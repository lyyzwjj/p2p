package cn.wolfcode.p2p.base.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by WangZhe on 2018/7/22.
 */
public class DateUtil {
    /**
     * 添加天数
     *
     * @param date
     * @param i
     * @return
     */
    public static Date addDays(Date date,int i){
        if (date == null){
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,i);
        return c.getTime();
    }

    /**
     * 添加月数
     *
     * @param date
     * @param i
     * @return
     */
    public static Date addMonths(Date date,int i){
        if (date == null){
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH,i);
        return c.getTime();
    }
    public static long getTimeBetween(Date d1, Date d2){
        return Math.abs((d1.getTime()-d2.getTime())/1000);
    }
    public static Date getEndDate(Date date){
        if (date == null){
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        return c.getTime();
    }
}
