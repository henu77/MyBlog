package cn.malong.blog.utils;

import lombok.*;

import java.util.Calendar;
import java.util.Date;

/**
 * @author malong
 * @Date 2021-11-08 23:14:59
 */
@Data
public class CalendarUtil {
    private static Calendar calendar = Calendar.getInstance();

    public static int getDay(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    public static int getMonth(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH )+ 1;
    }

    public static int getYear(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
}
