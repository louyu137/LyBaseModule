package cn.louyu.lylibrary.core.utils.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * yyyy:年
 * MM: 月
 * dd:日
 * hh:1~12小时制（1-12）
 * HH:24小时制（0-23）
 * mm:分
 * ss:秒
 * S:毫秒
 * E:星期几
 * D:一年中的第几天
 * F:一个月中的第几个星期（会吧这个月总共过的天数除以七）
 * w:一年中的第几个星期
 * W:一个月中的第几个星期（根据实际情况来计算）
 * a:上下午标识
 * k:和HH差不多，表示一天24小时制（1-24）
 * K:和hh差不多，表示天天12小时制（0-11）
 * z:表示时区
 * */
public class DateUtils {

    public final static String yyyy_MM_dd = "yyyy-MM-dd";
    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    /**
     * 格式化当前的时间
     * */
    public static String dateFormat(String pattern){
        return dateFormat(new Date(),pattern);
    }

    /**
     * 格式化指定的时间
     * */
    public static String dateFormat(Date time,String pattern){
        SimpleDateFormat format=new SimpleDateFormat(pattern);
        return format.format(time);
    }

    public static String getDateTimeFormat(){
        return "["+dateFormat(yyyy_MM_dd_HH_mm_ss)+"]";
    }
}
