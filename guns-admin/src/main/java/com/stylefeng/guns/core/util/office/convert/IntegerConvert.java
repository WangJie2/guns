package com.stylefeng.guns.core.util.office.convert;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegerConvert implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegerConvert.class);

    @Override
    public Object convert(Class type, Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String && StringUtils.isBlank((String) value)) {
            return null;
        }

        if (value instanceof Integer) {
            return value;
        }

        if (value instanceof Long) {
            Long longValue = (Long) value;
            return longValue.intValue();
        }

        try {
            return Double.valueOf(value.toString()).intValue();
        } catch (Exception e) {
            LOGGER.error("error.integer.convert", e);
        }
        return null;
    }

}
