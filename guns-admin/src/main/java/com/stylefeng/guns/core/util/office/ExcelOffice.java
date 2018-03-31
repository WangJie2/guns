package com.stylefeng.guns.core.util.office;


import com.stylefeng.guns.core.util.DateUtils;
import com.stylefeng.guns.core.util.office.convert.BigDecimalConvert;
import com.stylefeng.guns.core.util.office.convert.DateConvert;
import com.stylefeng.guns.core.util.office.convert.IntegerConvert;
import com.stylefeng.guns.core.util.office.convert.LongConvert;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

public class ExcelOffice implements MSOffice<SheetDO> {
    static {
        ConvertUtils.register(new DateConvert(), Date.class);
        ConvertUtils.register(new IntegerConvert(), Integer.class);
        ConvertUtils.register(new LongConvert(), Long.class);
        ConvertUtils.register(new BigDecimalConvert(), BigDecimal.class);
    }

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ExcelOffice.class);

    // DO对应导出字段
    public final static Map<String, LinkedHashMap<String, String>> MAP_DO_FIELDS = new HashMap<String, LinkedHashMap<String, String>>();

    // 默认第一个工作薄
    public final static int DEFAULT_SHEET_INDEX = 0;
    // 第一行表头
    // public final static int DEFAULT_HEAD_INDEX = 0;
    // 第二行开始表体
    // public final static int DEFAULT_BODY_INDEX = 1;
    // 导入最大500条数
    private int IMPORT_MAX_ROWS = 1000;

    public final static ExcelOffice excelOffice = new ExcelOffice();

    private Workbook wb;

    private ExcelOffice() {
    }

    public static ExcelOffice getInstance() {
        return excelOffice;
    }

    public void setMaxRows(int maxRows) {
        IMPORT_MAX_ROWS = maxRows;
    }

    private void newWorkbook(InputStream is, boolean isXlsx) throws IOException {
        if (is != null) {
            try {
                if (isXlsx) {
                    // .xlsx后缀文件
                    wb = new XSSFWorkbook(is);
                } else {
                    // .xls 后缀文件
                    wb = new HSSFWorkbook(is);
                }
            } catch (Exception e) {
                LOGGER.error("err.import.file.io.exception", e);
            } finally {
                if (is != null) {
                    is.close();
                }
            }

        } else {
            wb = new HSSFWorkbook();
        }
    }

    @Override
    public <A> List<A> read(File excelFile, Class<A> clazz, Map<String, String> fieldMap) {
        try {
            if (excelFile.isDirectory() || !excelFile.exists()) {
                return null;
            }
            return read(new FileInputStream(excelFile), true, clazz, fieldMap);
        } catch (Exception e) {
            LOGGER.error("err.read", e);
        }
        return null;
    }

    @Override
    public <A> List<A> read(InputStream is, boolean isXlsx, Class<A> clazz, Map<String, String> fieldMap) {
        List<A> data = new ArrayList<A>();
        SheetDO sheet = read(is, isXlsx);
        if (CollectionUtils.isEmpty(sheet.getHeadlist())
                || CollectionUtils.isEmpty(sheet.getBodyList())) {
            return null;
        }
        if (fieldMap == null || fieldMap.isEmpty()) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        Map<Integer, String> importFields = new HashMap<Integer, String>();
        for (Iterator<Entry<String, String>> iter = fieldMap.entrySet()
                .iterator(); iter.hasNext(); ) {
            Entry<String, String> entry = iter.next();
            map.put(entry.getValue(), entry.getKey());
        }
        for (int i = 0; i < sheet.getHeadlist().size(); i++) {
            String code = map.get(sheet.getHeadlist().get(i));
            if (code == null) {
                continue;
            }
            importFields.put(i, code);
        }

        List<List<Object>> bodyList = sheet.getBodyList();
        for (List<Object> row : bodyList) {
            A a = _createObject(row, importFields, clazz);
            data.add(a);
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    private <A> A _createObject(List<Object> row, Map<Integer, String> map,
                                Class<A> clazz) {
        Map<String, String> vals = new HashMap<String, String>();
        for (Iterator<Entry<Integer, String>> iter = map.entrySet().iterator(); iter
                .hasNext(); ) {
            Entry<Integer, String> entry = iter.next();
            try {
                String str = (String) row.get(entry.getKey());
                if (str != null) {
                    vals.put(entry.getValue(), str);
                }
            } catch (Exception e) {
                LOGGER.error("_createObject失败", e);
            }
        }

        Object obj = org.springframework.beans.BeanUtils
                .instantiateClass(clazz);
        try {
            BeanUtils.copyProperties(obj, vals);
        } catch (IllegalAccessException e) {
            LOGGER.error("_createObject失败", e);
        } catch (InvocationTargetException e) {
            LOGGER.error("_createObject失败", e);
        }
        return (A) obj;
    }

    @Override
    public SheetDO read(InputStream is, boolean isXlsx) {
        SheetDO sheetDO = new SheetDO();
        try {
            newWorkbook(is, isXlsx);
            Sheet sheet = this.wb.getSheetAt(DEFAULT_SHEET_INDEX);
            _getHead(sheet, sheetDO);
            _getBody(sheet, sheetDO);
        } catch (Exception e) {
            LOGGER.error("读取EXCEL失败", e);
        } finally {
            close();
        }
        return sheetDO;
    }

    private void _getHead(Sheet sheet, SheetDO sheetDO) {
        Row row = sheet.getRow(sheetDO.getHeadIndex());
        sheetDO.setHeadlist(_getCellOfHead(row, null));
    }

    private void _getBody(Sheet sheet, SheetDO sheetDO) {
        int idx = sheetDO.getBodyIndex();
        Row row = null;
        do {
            row = sheet.getRow(idx);
            if (row == null) {
                return;
            }
            sheetDO.addRow(_getCellOfRow(row, sheetDO.getColumns()));
            idx++;
        } while (idx <= IMPORT_MAX_ROWS);

    }

    private List<String> _getCellOfHead(Row row, Integer heads) {
        List<String> cols = new ArrayList<String>();
        if (heads == null) {
            for (Iterator<Cell> iter = row.iterator(); iter.hasNext(); ) {
                Cell cell = iter.next();
                cols.add(cell.toString());
            }
        } else {// 表体
            boolean isRowValid = false;
            for (int i = 0; i < heads; i++) {
                String temp = null;
                Cell cell = row.getCell(i);
                if (cell == null) {
                    temp = "";
                } else {
                    temp = _getCellOfVal(cell);
                }
                isRowValid |= StringUtils.isNotEmpty(temp);
                cols.add(temp);
            }
            if (!isRowValid) {
                return null;
            }
        }
        return cols;
    }

    private List<Object> _getCellOfRow(Row row, Integer heads) {
        List<Object> cols = new ArrayList<Object>();
        if (heads == null) {
            for (Iterator<Cell> iter = row.iterator(); iter.hasNext(); ) {
                Cell cell = iter.next();
                cols.add(cell.toString());
            }
        } else {// 表体
            boolean isRowValid = false;
            for (int i = 0; i < heads; i++) {
                String temp = null;
                Cell cell = row.getCell(i);
                if (cell == null) {
                    temp = "";
                } else {
                    temp = _getCellOfVal(cell);
                }
                isRowValid |= StringUtils.isNotEmpty(temp);
                cols.add(temp);
            }
            if (!isRowValid) {
                return null;
            }
        }
        return cols;
    }

    @SuppressWarnings("static-access")
    private String _getCellOfVal(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC
                && DateUtil.isCellDateFormatted(cell)) {
            // HSSFDateUtil.isCellDateFormatted(cell)
            return cell.getDateCellValue().getTime() + "";
        } else {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return cell.toString();
        }
    }

    @Override
    public boolean write(List<?> list, Class<?> clazz, OutputStream outputStream, Map<String, String> fieldMap) {
        SheetDO sheetDO = _convertList2Sheet(list, clazz, fieldMap);
        return write(sheetDO, outputStream);
    }


    private SheetDO _convertList2Sheet(List<?> list, Class<?> clazz, Map<String, String> fieldMap) {
        SheetDO sheetDO = new SheetDO();
        if (clazz == null || fieldMap == null || fieldMap.isEmpty()) {
            return null;
        }
        sheetDO.addHead(fieldMap.values());
        if (CollectionUtils.isEmpty(list)) {
            return sheetDO;
        }
        List<List<Object>> bodyList = new ArrayList<List<Object>>();
        for (Object o : list) {
            List<Object> row = _getObjectVal(o,
                    new ArrayList<String>(fieldMap.keySet()));
            bodyList.add(row);
        }
        sheetDO.setBodyList(bodyList);
        return sheetDO;
    }

    private List<Object> _getObjectVal(Object obj, List<String> fields) {
        List<Object> val = new ArrayList<Object>();
        Class<?> clazz = obj.getClass();
        for (String field : fields) {
            try {
                PropertyDescriptor pd = new PropertyDescriptor(field, clazz);
                Method getMethod = pd.getReadMethod();
                Object o = getMethod.invoke(obj);
                val.add(_convert2Val(o));
            } catch (Exception e) {
                LOGGER.info("error in convert field to String val.", e);
            }
        }
        return val;
    }

    private Object _convert2Val(Object o) {
        if (o == null) {
            return "";
        }
        if (o instanceof Date) {
            return DateUtils.formatTime((Date) o);
        }
        return o;
    }

    @Override
    public boolean write(SheetDO sheetDO, OutputStream outputStream) {
        if (sheetDO == null || outputStream == null) {
            return false;
        }
        try {
            newWorkbook(null, true);
            String sheetName = sheetDO.getSheetName();
            if (sheetName == null || sheetName.trim().length() < 1) {
                sheetName = DateUtils.formatDate(new Date());
            }
            Sheet sheet = this.wb.createSheet(sheetName);

            for (int i = 0; i < sheetDO.getHeadlist().size(); i++) {
                sheet.autoSizeColumn(i);
            }
            _setFirst(sheet, sheetDO);
            _setHead(sheet, sheetDO);
            _setBody(sheet, sheetDO);
            _setLast(sheet, sheetDO);
            this.wb.write(outputStream);
        } catch (Exception e) {
            LOGGER.error("写入EXCEL失败", e);
        } finally {
            close();
            try {
                outputStream.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return false;
    }

    private void _setFirst(Sheet sheet, SheetDO sheetDO) {
        if (sheetDO.getFirstList() == null || sheetDO.getFirstList().isEmpty()) {
            return;
        }
        for (int i = 0; i < sheetDO.getFirstList().size(); i++) {
            Row row = sheet.createRow(i);
            _setCell(row, sheetDO.getFirstList().get(i));
        }
    }

    private void _setHead(Sheet sheet, SheetDO sheetDO) {
        if (sheetDO.getHeadlist() == null || sheetDO.getHeadlist().isEmpty()) {
            return;
        }
        Row row = sheet.createRow(sheetDO.getHeadIndex());
        row.setHeight((short) 400);
        _setCellHead(row, sheetDO.getHeadlist());
    }

    private void _setBody(Sheet sheet, SheetDO sheetDO) {
        if (sheetDO.getBodyList() == null || sheetDO.getBodyList().isEmpty()) {
            return;
        }
        for (int i = 0; i < sheetDO.getBodyList().size(); i++) {
            Row row = sheet.createRow(sheetDO.getBodyIndex() + i);
            _setCell(row, sheetDO.getBodyList().get(i));
        }
    }

    private void _setLast(Sheet sheet, SheetDO sheetDO) {
        if (sheetDO.getLastList() == null || sheetDO.getLastList().isEmpty()) {
            return;
        }
        int startIndex = sheetDO.getBodyIndex() + sheetDO.getBodyList().size();
        for (int i = 0; i < sheetDO.getLastList().size(); i++) {
            Row row = sheet.createRow(startIndex + i);
            _setCell(row, sheetDO.getLastList().get(i));
        }
    }

    private void _setCellHead(Row row, List<String> headlist) {
        for (int i = 0; i < headlist.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headlist.get(i));
        }
    }

    private void _setCell(Row row, List<Object> bodylist) {
        for (int i = 0; i < bodylist.size(); i++) {
            Cell cell = row.createCell(i);
            if (bodylist.get(i) instanceof Integer) {
                int num = (Integer) bodylist.get(i);
                Double donum = (double) num;
                cell.setCellValue(donum);
            } else if (bodylist.get(i) instanceof Double) {
                cell.setCellValue((Double) bodylist.get(i));
            } else {
                cell.setCellValue((String) bodylist.get(i));
            }

        }
    }

    @Override
    public boolean close() {
        if (wb != null) {
            wb = null;
        }
        return false;
    }

    @Override
    public boolean write(List<SheetDO> sheetDOs, OutputStream outputStream) {

        if (sheetDOs == null || outputStream == null) {
            return false;
        }
        try {
            newWorkbook(null, true);
            for (SheetDO sheetDO : sheetDOs) {
                String sheetName = sheetDO.getSheetName();
                if (sheetName == null || sheetName.trim().length() < 1) {
                    sheetName = DateUtils.formatDate(new Date());
                }
                Sheet sheet = this.wb.createSheet(sheetName);

                for (int i = 0; i < sheetDO.getHeadlist().size(); i++) {
                    sheet.autoSizeColumn(i);
                }
                _setFirst(sheet, sheetDO);
                _setHead(sheet, sheetDO);
                _setBody(sheet, sheetDO);
                _setLast(sheet, sheetDO);
            }
            this.wb.write(outputStream);
        } catch (Exception e) {
            LOGGER.error("写入EXCEL失败", e);
        } finally {
            close();
        }
        return false;
    }

}
