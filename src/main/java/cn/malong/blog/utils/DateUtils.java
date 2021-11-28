package cn.malong.blog.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Csy
 * @Classname DateUtils
 * @date 2021-11-01 22:00
 * @Description TODO
 */
public class DateUtils {

    public static java.sql.Timestamp d2t(java.util.Date date) {
        if (null == date) {
            return null;
        }
        return new java.sql.Timestamp(date.getTime());
    }

    public static java.util.Date t2d(java.sql.Timestamp timestamp) {
        if (null == timestamp) {
            return null;
        }
        return new java.util.Date(timestamp.getTime());
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(d2t(date).toString());
        Timestamp timestamp = d2t(date);
        System.out.println(t2d(timestamp));
    }

    public static String getYMD(Date date) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
        return dateFormat.format(date);
    }

    public static String dateToString(Date date) {
        if (null == date) {
            return null;
        }
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
        DateFormat timeInstance = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
        String format = dateFormat.format(date);
        String time = timeInstance.format(date);
        return format + " " + time;
    }
}
