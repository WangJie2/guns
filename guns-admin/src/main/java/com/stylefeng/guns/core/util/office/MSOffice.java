package com.stylefeng.guns.core.util.office;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;


public interface MSOffice<T> {

    public abstract T read(InputStream is, boolean isXlsx);

    public <A> List<A> read(InputStream is, boolean isXlsx, Class<A> clazz, Map<String, String> fieldMap);

    public <A> List<A> read(File excelFile, Class<A> clazz, Map<String, String> fieldMap);

    public abstract boolean write(T t, OutputStream outputStream);

    public abstract boolean write(List<T> t, OutputStream outputStream);

    public abstract boolean write(List<?> list, Class<?> clazz, OutputStream outputStream, Map<String, String> fieldMap);

    public abstract boolean close();

}
