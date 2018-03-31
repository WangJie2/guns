package com.stylefeng.guns.core.util.office.convert;

import com.stylefeng.guns.core.util.DateUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class DateConvert implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateConvert.class);

    @Override
    public Object convert(Class type, Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof String && StringUtils.isBlank((String) value)) {
            return null;
        }

        if (value instanceof Date) {
            return value;
        }

        if (value instanceof Long) {
            Long longValue = (Long) value;
            return new Date(longValue.longValue());
        }

        try {
            return DateUtils.parseDate(value.toString());
        } catch (Exception e) {
            LOGGER.error("error.date.convert", e);
            try {
                return DateUtils.parseTime(value.toString());
            } catch (Exception e1) {
                LOGGER.error("error.date.convert", e1);
                if (value instanceof String) {
                    String str = (String) value;
                    return new Date(NumberUtils.toLong(str));
                }
            }
        }
        return null;
    }

}
