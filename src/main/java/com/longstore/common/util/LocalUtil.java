package com.longstore.common.util;

public class LocalUtil {
    
    /**
     * 屏蔽手机
     */
    public static String hideTel(String tel){
        if(tel != null){
            int le = tel == null ? -1 : tel.length();
            if (le > 8) {
                return tel.substring(0, le - 8) + "****" + tel.substring(le - 4);
            }else if (le > 6) {
                return tel.substring(0, le - 6) + "***" + tel.substring(le - 3);
            }else if (le > 4) {
                return tel.substring(0, le - 4) + "**" + tel.substring(le - 2);
            }else{
                return tel;
            }
        }
        return "";
    }
    
    /**
     * 将数字转为前端需要的字符格式
     * @param num
     * @return
     */
    public static String numToString(Integer num) {
        if (num == null || num < 0) {
            return "0";
        }
        /* 如果数量上万了，则以“万”为单位表述数量，最多精确到小数点后1位。 */
        if (num > 10000) {
            String str = String.valueOf(num);
            String b = str.substring(str.length() - 4, str.length() - 3);
            if (b.equals("0")) {
                str = str.substring(0, str.length() - 4);
                return str + "万";
            } else {
                str = str.substring(0, str.length() - 4);
                return str + "." + b + "万";
            }
        }
        return String.valueOf(num);
    }
    
}
