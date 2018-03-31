package com.stylefeng.guns.core.util.office.convert;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class BigDecimalConvert implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BigDecimalConvert.class);

    @Override
    public Object convert(Class type, Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String && StringUtils.isBlank((String) value)) {
            return null;
        }

        if (value instanceof Long) {
            Long longValue = (Long) value;
            return BigDecimal.valueOf(longValue);
        }

        if (value instanceof Double) {
            Double douValue = (Double) value;
            return BigDecimal.valueOf(douValue);
        }

        if (value instanceof Float) {
            Float floValue = (Float) value;
            return BigDecimal.valueOf(floValue);
        }

        if (value instanceof Integer) {
            Integer intValue = (Integer) value;
            return BigDecimal.valueOf(intValue);
        }

        try {
            return new BigDecimal(value.toString());
        } catch (Exception e) {
            LOGGER.error("error.bigDecimal.convert", e);
        }
        return null;
    }

}
