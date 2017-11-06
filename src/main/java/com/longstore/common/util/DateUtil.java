package com.longstore.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间工具
 */
public class DateUtil {
    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    
    private final static long day_ms = 24 * 60 * 60 * 1000;
    /**yyyy-MM-dd HH:mm:ss*/
    public final static DateTimeFormatter DF_SIMPLE_24 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**yyyy-MM-dd hh:mm*/
    public final static DateTimeFormatter DF_YMDHM_12 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    /**yyyy-MM-dd HH:mm*/
    public final static DateTimeFormatter DF_YMDHM_24 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    /**yyyy.MM.dd HH:mm*/
    public final static DateTimeFormatter DF_YMDHM_24_2 = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
    /**yyyy-MM*/
    public final static DateTimeFormatter DF_YM = DateTimeFormatter.ofPattern("yyyy-MM");
    /**yyyy-MM-dd*/
    public final static DateTimeFormatter DF_YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**yy-MM-dd*/
    public final static DateTimeFormatter DF_YYMD = DateTimeFormatter.ofPattern("yy-MM-dd");
    /**yyyy/MM/dd*/
    public final static DateTimeFormatter DF_YMD_3 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    /**yyyy.MM.dd*/
    public final static DateTimeFormatter DF_YMD_4 = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    /**yyyyMMdd*/
    public final static DateTimeFormatter DF_YMD_2 = DateTimeFormatter.ofPattern("yyyyMMdd");
    /**hh:mm:ss*/
    public final static DateTimeFormatter DF_HMS_12 = DateTimeFormatter.ofPattern("hh:mm:ss");
    /**HH:mm:ss*/
    public final static DateTimeFormatter DF_HMS_24 = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**yyyy年MM月dd日*/
    public final static DateTimeFormatter DF_SIMPLE = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    /**yyyy年MM月d日*/
    public final static DateTimeFormatter DF_SIMPLE_DAY = DateTimeFormatter.ofPattern("yyyy年MM月d日");
    /**yyyy年MM月dd日H时mm分ss秒*/
    public final static DateTimeFormatter DF_SIMPLE_ZH = DateTimeFormatter.ofPattern("yyyy年MM月dd日H时mm分ss秒");
    /**yyyy年MM月dd日 HH:mm*/
    public final static DateTimeFormatter DF_SIMPLE_ZH_2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
    /**MM月dd日*/
    public final static DateTimeFormatter DF_YM_ZH = DateTimeFormatter.ofPattern("MM月dd日");
    /**yyyyMMddHHmmssSSS*/
    public final static DateTimeFormatter DF_FULL = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    /**yyyyMMddHHmmss*/
    public final static DateTimeFormatter DF_FULL2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    /**H时mm分*/
    public final static DateTimeFormatter DF_HHMM_ZH = DateTimeFormatter.ofPattern("H时mm分");
    /**MM月dd日H时mm分*/
    public final static DateTimeFormatter DF_MMDDHHMM_ZH = DateTimeFormatter.ofPattern("M月d日H时mm分");
    
    /**yyyy-MM-dd hh:mm:ss*/
    public final static DateTimeFormatter DF_SIMPLE_12_GMT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    /**yyyy-MM-dd HH:mm:ss*/
    public final static DateTimeFormatter DF_SIMPLE_24_GMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**yyyy-MM-dd hh:mm*/
    public final static DateTimeFormatter DF_YMDHM_12_GMT = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    /**yyyy-MM-dd HH:mm*/
    public final static DateTimeFormatter DF_YMDHM_24_GMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    /**yyyy-MM-dd*/
    public final static DateTimeFormatter DF_YMD_GMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /** yyyyMM*/
    public final static DateTimeFormatter DF_YM_2_GMT = DateTimeFormatter.ofPattern("yyyyMM");
    /**yyyyMMdd*/
    public final static DateTimeFormatter DF_YMD_2_GMT = DateTimeFormatter.ofPattern("yyyyMMdd");
    /**hh:mm:ss*/
    public final static DateTimeFormatter DF_HMS_12_GMT = DateTimeFormatter.ofPattern("hh:mm:ss");
    /**HH:mm:ss*/
    public final static DateTimeFormatter DF_HMS_24_GMT = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**yyyy年MM月dd日H时mm分ss秒*/
    public final static DateTimeFormatter DF_SIMPLE_ZH_GMT = DateTimeFormatter.ofPattern("yyyy年MM月dd日H时mm分ss秒");
    /**yyyy年MM月dd日 HH:mm*/
    public final static DateTimeFormatter DF_SIMPLE_ZH_2_GMT = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
    /**yyyyMMddHHmmssSSS*/
    public final static DateTimeFormatter DF_FULL_GMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    /**-dd-MM-yyyy*/
    public final static DateTimeFormatter DF_DMY = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    
    /**
     * 时间字符串转化成 Date
     * @param dateStr 时间字符串(带时分秒)
     * @param df      时间字符串格式，如：yyyy-MM-dd HH:mm:ss
     * @return Date   返回日期
     */
    public static Date toDateTime(String dateStr, DateTimeFormatter df) {
        if (StringUtils.isBlank(dateStr) || df == null) {
             return null;
        }
        try {
            return Date.from(LocalDateTime.parse(dateStr, df).atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            logger.error("dateStr=" + dateStr + ", df=" + df.toString() + "\n" + e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 将日期字符串转换成date (不能转换只包含年与月的日期)
     * @param dateStr 日期字符串(不包含时分秒)
     * @param df       格式化,如: yyyy-MM-dd
     */
    public static Date toDate(String dateStr, DateTimeFormatter df) {
        if (StringUtils.isBlank(dateStr) || df == null) {
            return null;
       }
       try {
           return Date.from(LocalDate.parse(dateStr, df).atStartOfDay(ZoneId.systemDefault()).toInstant());
       } catch (Exception e) {
           logger.error("dateStr=" + dateStr + ", df=" + df.toString() + "\n" + e.getMessage(), e);
           return null;
       }
    }
    
    /**
     * 时间转化成字符串
     * @param date    时间，null为当前时间
     * @param df      时间字符串格式，如：yyyy-MM-dd HH:mm:ss
     * @return String 返回日期字符串
     */
    public static String format(Date date, DateTimeFormatter df) {
        Instant instant = date != null ? date.toInstant() : Instant.now();
        return df.format(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
    }
    
    /**
     * 时间计算
     * @param date 时间，null为当前时间
     * @param field 对哪个属性进行计算；1：年；2：月；3：日；4：小时；5：分钟；6：秒
     * @param amount 要增加的值
     * @return Date  返回日期
     */
    public static Date addTime(Date date, int field, int amount) {
        try {
            Instant instant = date == null ? Instant.now() : date.toInstant();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            if(field == 1){
                localDateTime = localDateTime.plusYears(amount);
            }else if(field == 2){
                localDateTime = localDateTime.plusMonths(amount);
            }else if(field == 3){
                localDateTime = localDateTime.plusDays(amount);
            }else if(field == 4){
                localDateTime = localDateTime.plusHours(amount);
            }else if(field == 5){
                localDateTime = localDateTime.plusMinutes(amount);
            }else if(field == 6){
                localDateTime = localDateTime.plusSeconds(amount);
            }
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            logger.error("add time failed: {}", e.getMessage());
        }
        return null;
    }
    
    /**
     * 两个时间之间的天数
     * @param startDate  起始时间
     * @param endDate    结束时间
     * @return long   两个时间间的天数(如果为负取绝对值)
     */
    public static long daysBetweenTwoDate(Date startDate, Date endDate) {
        if(startDate == null){
            return 0;
        }
        Instant endInstant = endDate == null ? Instant.now() : endDate.toInstant();
        return Math.abs((endInstant.toEpochMilli() - startDate.toInstant().toEpochMilli()) / day_ms);
    }
    
}
