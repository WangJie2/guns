package com.stylefeng.guns.core.util.office;

import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来处理excel的导入导出
 * Created by WangJie on 2017/4/24.
 */
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    private static final String XLSX = "xlsx";

    private static final String XLS = "xls";

    private FileUtils() {
    }

    /**
     * 导出只含有标题的excel
     * 一般当做导出模板用
     *
     * @param fileName
     * @param sheetTitle
     * @param request
     * @param response
     */
    public static void exportTableTemplate(String fileName, String sheetTitle, String[] headers, HttpServletRequest request, HttpServletResponse response) {
        ExportExcelTable table = new ExportExcelTable(request, response);
        table.addSheet(sheetTitle, headers);
        table.exportExcel(fileName);
    }

    /**
     * 导出excel带有数据和标题
     *
     * @param fileName
     * @param sheetTitle
     * @param request
     * @param response
     */
    public static <T> void exportTableWithData(String fileName, String sheetTitle, String[] headers, String[] fieldNames, Collection<T> data, HttpServletRequest request, HttpServletResponse response) {
        ExportExcelTable table = new ExportExcelTable(request, response);
        table.addSheet(sheetTitle, headers, fieldNames, data);
        table.exportExcel(fileName);
    }

    /**
     * 导入读取excel文件
     *
     * @param uploadFile
     * @param clazz
     * @param map
     * @return
     * @throws Exception
     */
    public static <A> List<A> uploadAndReadExcel(MultipartFile uploadFile, Class<A> clazz, Map<String, String> map) {
        if (uploadFile == null || uploadFile.isEmpty()) {
            throw new BussinessException(BizExceptionEnum.FILE_EMPTY);
        }
        String fileName = uploadFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (StringUtils.isEmpty(suffix) || !(XLSX.equalsIgnoreCase(suffix) || XLS.equalsIgnoreCase(suffix))) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        boolean bool = XLSX.equalsIgnoreCase(suffix);
        InputStream is = null;
        try {
            is = uploadFile.getInputStream();
            return ExcelOffice.getInstance().read(is, bool, clazz, map);
        } catch (Exception e) {
            LOGGER.error("err.import.file.io.exception", e);
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("err.import.file.io.exception", e);
                }
            }
        }
    }

    /**
     * 获取excel对应bean的属性
     *
     * @param fields  属性
     * @param headers 表头
     * @return
     */
    public static Map<String, String> getFieldOfClass(String[] fields, String[] headers) {
        if (fields == null || headers == null || fields.length != headers.length) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < fields.length; i++) {
            map.put(fields[i], headers[i]);
        }
        return map;
    }
}
