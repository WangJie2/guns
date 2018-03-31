package com.stylefeng.guns.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by WangJie on 2018/3/27.
 */
public class GunDateUtil {

    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDateString(Date date) {
        if (date == null) {
            return null;
        }
        return SDF.format(date);
    }
}
