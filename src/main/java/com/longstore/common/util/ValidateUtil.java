package com.longstore.common.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ValidateUtil {

    private final static Pattern P_MOBILE = Pattern.compile("^[1][0-9]{10}$");
    public static boolean isMobileNo(String mobiles) {
        if (StringUtils.isBlank(mobiles)){
            return false;
        }
        return P_MOBILE.matcher(mobiles).matches();
    }

    private final static Pattern P_TIME_HM = Pattern.compile("^(([0-1]\\d)|(2[0-4])):[0-5]\\d$");
    /** 验证是否是 (时:分) 的格式 */
    public static boolean isHourminute(String time) {
        if (StringUtils.isBlank(time)){
            return false;
        }
        return P_TIME_HM.matcher(time).matches();
    }
    
    private final static Pattern P_EMAIL = Pattern.compile(
            "^[a-zA-Z0-9]+([.\\-_]?[a-zA-Z0-9]+)+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email)){
            return false;
        }
        return P_EMAIL.matcher(email).matches();
    }

    private final static Pattern P_PHONE = Pattern.compile("^(([1][0-9]{10})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)$");
    public static boolean isPhoneNo(String phoneNo) {
        if (StringUtils.isBlank(phoneNo)){
            return false;
        }
        return P_PHONE.matcher(phoneNo).matches();
    }
    
    /** 国际传真 */
    public static boolean isFaxNo(String faxNo) {
        if (StringUtils.isBlank(faxNo)){
            return false;
        }
        return isPhoneNo(faxNo);
    }

    private final static Pattern P_ZIP_CODE = Pattern.compile("[0-9]{6}");
    public static boolean isZipCode(String zipCode) {
        if (StringUtils.isBlank(zipCode)){
            return false;
        }
        return P_ZIP_CODE.matcher(zipCode).matches();
    }

    private final static Pattern P_NUMBER = Pattern.compile("[0-9]+(\\.[0-9]+)?");
    public static boolean isNumber(String number) {
        if (StringUtils.isBlank(number)){
            return false;
        }
        if (P_NUMBER.matcher(number).matches()) {
            String prefix = number.split("\\.")[0];
            return prefix.length() <= 16;
        }
        return false;
    }

    private final static Pattern P_INT = Pattern.compile("^[1-9]\\d*$");
    public static boolean isInt(String number) {
        if (StringUtils.isBlank(number)){
            return false;
        }
        if (P_INT.matcher(number).matches()) {
            double d = Double.valueOf(number);
            return d <= 2147483647d;
        }
        return false;
    }
    
    private final static Pattern P_INTEGER = Pattern.compile("^[-\\+]?[0-9]\\d*$");
    public static boolean isInteger(String number) {
        if (StringUtils.isBlank(number)){
            return false;
        }
        if (P_INTEGER.matcher(number).matches()) {
            double d = Double.valueOf(number);
            return d <= 2147483647d;
        }
        return false;
    }
    
    private final static Pattern P_INDIADATE = Pattern.compile("^(?:[0-9]|[0][1-9]|[1-2]\\d|3[0-1])/(?:[1-9]|0[1-9]|[1][0-2])/(?:\\d{4})$");
    public static boolean isIndiaDate(String number) {
        if (StringUtils.isBlank(number)){
            return false;
        }
        return P_INDIADATE.matcher(number).matches();
    }
    
    private final static Pattern P_DOUBLE2 = Pattern.compile("\\d{1,8}(\\.\\d{1,2})?");
    /** 验证数字小数点后是否超过2位  */
    public static boolean isTwoAfterPoint(String number) {
        if (StringUtils.isBlank(number)){
            return false;
        }
        return P_DOUBLE2.matcher(number).matches();
    }
    
    private final static Pattern P_DOUBLE7 = Pattern.compile("\\d{1,10}(\\.\\d{1,7})?");
    /** 验证数字小数点后是否超过7位 */
    public static boolean isSeewenAfterPoint(String number) {
        if (StringUtils.isBlank(number)){
            return false;
        }
        return P_DOUBLE7.matcher(number).matches();
    }
    
    private final static Pattern P_DATE_YM = Pattern.compile("([0-2])?[0-9]/(\\d{1,2})");
    /** 验证日期年月 mm/yy */
    public static boolean isYearMonth(String number) {
        if (StringUtils.isBlank(number)){
            return false;
        }
        return P_DATE_YM.matcher(number).matches();
    }
    
    private final static Pattern P_AZ = Pattern.compile("^[a-zA-Z_0-9]+$");
    /** 数字+字母+下划线 */
    public static boolean isNumberAndLetter(String number) {
        if (StringUtils.isBlank(number)){
            return false;
        }
        return P_AZ.matcher(number).matches();
    }
    
    public static void main(String[] args) {
        //[a-zA-Z_0-9]+
        System.out.println(isNumberAndLetter("545asdad*&"));
    }
}
