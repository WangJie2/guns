package com.stylefeng.guns.core.util.office.convert;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongConvert implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongConvert.class);

    @Override
    public Object convert(Class type, Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String && StringUtils.isBlank((String) value)) {
            return null;
        }

        if (value instanceof Long) {
            return value;
        }

        if (value instanceof Integer) {
            Integer intValue = (Integer) value;
            return intValue.longValue();
        }

        try {
            return Double.valueOf(value.toString()).longValue();
        } catch (Exception e) {
            LOGGER.error("error.long.convert", e);
        }
        return null;
    }

}
