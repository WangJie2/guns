/*
 * Copyright 2009-2012 Evun Technology. 
 * 
 * This software is the confidential and proprietary information of
 * Evun Technology. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with evun.cn.
 */
package com.stylefeng.guns.core.util.office;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导出EXCEL 表格通用处理
 *
 * @author yangw
 * @version v2.1.3
 */
public class ExportExcelTable<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcelTable.class);

    private int defaultColWidth = 15;

    private String datePattern = "yyy-MM-dd";

    private int pictureHeightInPoint = 60;

    private int pictureWidthInPoint = 60;

    private OutputStream output = null;

    private HttpServletResponse httpresponse = null;

    private HttpServletRequest httprequest = null;

    private HSSFWorkbook workbook = new HSSFWorkbook();

    public ExportExcelTable() {
        super();
    }

    public ExportExcelTable(OutputStream out) {
        super();
        output = out;
    }

    public ExportExcelTable(HttpServletRequest request, HttpServletResponse response) {
        super();
        httprequest = request;
        httpresponse = response;
        try {
            output = response.getOutputStream();
        } catch (IOException ioe) {
            LOGGER.error("error when export excel table at get output stream", ioe);
        }
    }

    /**
     * <p>创建sheet完毕后，对整个excel文件进行输出</P>
     */
    public void exportExcel(String fileName) {
        if (httpresponse != null) {
            httpresponse.setCharacterEncoding("UTF-8");
            httpresponse.setContentType("application/vnd.ms-excel;charset=UTF-8");
            httpresponse.addHeader("Content-Disposition", "attachment;" + this.getDownLoadFileName(
                    fileName + ".xls", httprequest));
        }
        try {
            workbook.write(output);
            output.close();
        } catch (Exception e) {
            LOGGER.error("error when export excel table at writing workbook", e);
        }
    }

    public void exportExcel() {
        exportExcel(String.valueOf(System.currentTimeMillis()));
    }

    /**
     * <p>为即将导出的excel文档增加一个sheet</P>
     *
     * @param dataset 需要显示的数据集合,每一条数据为一个Object[]。第一列内容为标题。
     *                支持的数据类型有基本数据类型及String,Date,byte[](图片数据)
     */
    public void addSheet(Collection<Object[]> dataset) {
        addSheet(null, dataset);
    }

    /**
     * <p>为即将导出的excel文档增加一个sheet</P>
     *
     * @param title   sheet的标题名
     * @param dataset 需要显示的数据集合,每一条数据为一个Object[]。第一列内容为标题。
     *                此方法支持的数据类型有基本数据类型及String,Date,byte[](图片数据)
     */
    public void addSheet(String title, Collection<Object[]> dataset) {
        HSSFSheet sheet = null;
        if (StringUtils.isEmpty(title)) {
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.createSheet(title);
        }
        sheet.setDefaultColumnWidth(defaultColWidth);//设置表格默认列宽度为15个字节

        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFFont cellFont = workbook.createFont();
        cellFont.setColor(HSSFColor.BLACK.index);

        //遍历集合数据，产生数据行
        if (CollectionUtils.isNotEmpty(dataset)) {
            Iterator<Object[]> it = dataset.iterator();
            HSSFRow row = null;
            int index = 0;//行序号
            while (it.hasNext()) {
                row = sheet.createRow(index);
                Object[] fields = (Object[]) it.next();
                for (int i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    if (index == 0) {//标题行
                        cell.setCellStyle(getHeaderStyle(workbook));
                    }
                    try {
                        Object value = fields[i];
                        if (value == null) {
                            continue;
                        }
                        //处理单元格内的数据内容
                        处理单元格内的数据内容(sheet, patriarch, cellFont, row, index, i, cell, value);
                    } catch (Exception e) {
                        LOGGER.error("error when export excel table at creating workbook sheet", e);
                        throw e;
                    }
                }
                index++;
            }
        }
    }

    private void 处理单元格内的数据内容(HSSFSheet sheet, HSSFPatriarch patriarch, HSSFFont cellFont, HSSFRow row, int index, int i, HSSFCell cell, Object value) {
        if (value instanceof byte[]) {//图片数据的处理
            row.setHeightInPoints(pictureHeightInPoint);//设置行高为60px;
            sheet.setColumnWidth(i, (short) (35.7 * pictureWidthInPoint));//设置图片所在列宽度为80px
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
            anchor.setAnchorType(2);
            patriarch.createPicture(anchor, workbook.addPicture((byte[]) value, HSSFWorkbook.PICTURE_TYPE_JPEG));
        } else {
            String textValue = getStringValue4Cell(value);//其他数据类型
            if (textValue != null) {
                Pattern p = Pattern.compile("^//d+(//.//d+)?{1}");
                Matcher matcher = p.matcher(textValue);//利用正则表达式判断textValue是否全部由数字组成
                if (matcher.matches()) {
                    cell.setCellValue(Double.parseDouble(textValue));//是数字当作double处理
                } else {
                    HSSFRichTextString richString = new HSSFRichTextString(textValue);
                    richString.applyFont(cellFont);
                    cell.setCellValue(richString);
                }
            }
        }
    }

    /**
     * <p>为即将导出的excel文档增加一个sheet</P>
     *
     * @param headers 标题
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。支持的
     *                数据类型有基本数据类型及String,Date,byte[](图片数据)
     */
    public void addSheet(String[] headers, String[] fieldNames, Collection<T> dataset) {
        addSheet(null, headers, fieldNames, dataset);
    }

    /**
     * 下载模板
     * 没有数据
     *
     * @param title
     * @param headers
     */
    public void addSheet(String title, String[] headers) {
        addSheet(title, headers, null, null);
    }

    /**
     * <p>为即将导出的excel文档增加一个sheet</P>
     *
     * @param title   sheet的标题名
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     */
    public void addSheet(String title, String[] headers, String[] fieldNames, Collection<T> dataset) {
        HSSFSheet sheet = null;
        if (StringUtils.isEmpty(title)) {
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.createSheet(title);
        }
        sheet.setDefaultColumnWidth(defaultColWidth);//设置表格默认列宽度为15个字节

        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        int index = 0;//行序号

        //表格标题行
        HSSFRow row = null;
        HSSFCellStyle headerStyle = getHeaderStyle(workbook);
        if (headers != null && headers.length > 0) {
            row = sheet.createRow(index);
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(new HSSFRichTextString(headers[i]));
            }
            index++;
        }
//        HSSFFont cellFont = workbook.createFont();
//        cellFont.setColor(HSSFColor.BLUE.index);
        //遍历集合数据，产生数据行
        HSSFCellStyle bodyStyle = getBodyStyle(workbook);
        if (CollectionUtils.isNotEmpty(dataset)) {
            Iterator<T> it = dataset.iterator();
            while (it.hasNext()) {
                row = sheet.createRow(index);
                T bean = (T) it.next();
                if (fieldNames == null || fieldNames.length == 0) {
                    List<String> names = new ArrayList<String>();
                    Field[] fields = bean.getClass().getDeclaredFields();//得到bean属性集合
                    for (Field field : fields) {
                        names.add(field.getName());
                    }
                    fieldNames = names.toArray(new String[0]);
                }
                for (int i = 0; i < fieldNames.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(bodyStyle);
                    try {
                        Object value = ReflectionUtils.invokeGetter(bean, fieldNames[i]);
                        //处理单元格内的数据内容
                        if (value instanceof byte[]) {//图片数据的处理
                            row.setHeightInPoints(pictureHeightInPoint);//设置行高为60px;
                            sheet.setColumnWidth(i, (short) (35.7 * pictureWidthInPoint));//设置图片所在列宽度为80px
                            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
                            anchor.setAnchorType(2);
                            patriarch.createPicture(anchor, workbook.addPicture((byte[]) value, HSSFWorkbook.PICTURE_TYPE_JPEG));
                        } else {
                            String textValue = getStringValue4Cell(value);//其他数据类型
                            if (textValue != null) {
                                Pattern p = Pattern.compile("^//d+(//.//d+)?{1}");
                                Matcher matcher = p.matcher(textValue);//利用正则表达式判断textValue是否全部由数字组成
                                if (matcher.matches()) {
                                    cell.setCellValue(Double.parseDouble(textValue));//是数字当作double处理
                                } else {
                                    HSSFRichTextString richString = new HSSFRichTextString(textValue);
//                                    richString.applyFont(cellFont);
                                    cell.setCellValue(richString);
                                }
                            }
                        }
                    } catch (IllegalArgumentException ex) {
                        LOGGER.error("error when export excel table at creating workbook sheet", ex);
                        continue;
                    } catch (Exception e) {
                        LOGGER.error("error when export excel table at creating workbook sheet", e);
                        throw e;
                    }
                }
                index++;
            }
        }
    }

    private String getStringValue4Cell(Object value) {
        if (value == null) {
            return null;
        }
        String textValue = null;
        if (value instanceof Boolean) {
            textValue = "是";
            if (!((Boolean) value)) {
                textValue = "否";
            }
        } else if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
            textValue = sdf.format((Date) value);
        } else {
            textValue = value.toString();//其它数据类型都当作字符串简单处理
        }
        return textValue;
    }

    private HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
        return style;
    }

    private HSSFCellStyle getBodyStyle(HSSFWorkbook workbook) {
        HSSFCellStyle style2 = workbook.createCellStyle();
//        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style2.setFont(font2);
        return style2;
    }

    public int getDefaultColWidth() {
        return defaultColWidth;
    }

    public void setDefaultColWidth(int defaultColWidth) {
        this.defaultColWidth = defaultColWidth;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public int getPictureHeightInPoint() {
        return pictureHeightInPoint;
    }

    public void setPictureHeightInPoint(int pictureHeightInPoint) {
        this.pictureHeightInPoint = pictureHeightInPoint;
    }

    public int getPictureWidthInPoint() {
        return pictureWidthInPoint;
    }

    public void setPictureWidthInPoint(int pictureWidthInPoint) {
        this.pictureWidthInPoint = pictureWidthInPoint;
    }

    public String getDownLoadFileName(String fileName, HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String name = null;
        try {
            name = URLEncoder.encode(fileName, "UTF-8").replace("%28", "(").replace("%29", ")");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("error.getDownLoadFileName", e);
            name = fileName;
        }
        if (StringUtils.isNotEmpty(userAgent)) {
            userAgent = userAgent.toLowerCase();
            if (userAgent.indexOf("opera") != -1) {
                name = "filename*=UTF-8''" + name;
            } else if (userAgent.indexOf("msie") != -1) {
                name = "filename=\"" + name + "\"";
            } else if (userAgent.indexOf("mozilla") != -1 && userAgent.indexOf("firefox") != -1) {
                try {
                    name = "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"";
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("error.getDownLoadFileName", e);
                    name = "filename=\"" + name + "\"";
                }
                //由于IE的userAgent调整，如下为IE11的userAgent，调整判断
                //mozilla/5.0 (windows nt 6.1; wow64; trident/7.0; ) like geckorv:11.0
            } else if (userAgent.indexOf("mozilla") != -1 && userAgent.indexOf("safari") != -1) {
                try {
                    name = "filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("error.getDownLoadFileName", e);
                    name = "filename=" + name;
                }
            } else if (userAgent.indexOf("mozilla") != -1) {
                name = "filename=\"" + name + "\"";
            } else {
                name = "\"filename=" + name + "\"";
            }
        } else {
            name = "\"filename=" + name + "\"";
        }
        return name;
    }
}