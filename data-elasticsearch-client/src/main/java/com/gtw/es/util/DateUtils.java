package com.gtw.es.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间处理函数
 **/
public class DateUtils {
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat(DEFAULT_PATTERN);

    /**
     * 将时间转换为默认的时间格式"yyyy-MM-dd HH:mm:ss"
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (null == date) {
            return null;
        }
        return DEFAULT_FORMAT.format(date);
    }

    /**
     * 字符串解析为date
     *
     * @param time
     * @return
     */
    public static Date parseDate(String time) {
        if (time != null && !"".equals(time.trim())) {
            return null;
        }
        try {
            return DEFAULT_FORMAT.parse(time);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 将毫秒值转换为date
     *
     * @param milseconds
     * @return
     */
    public static Date parseDate(Long milseconds) {
        if (null == milseconds) {
            return null;
        }
        return new Date(milseconds);
    }

}
