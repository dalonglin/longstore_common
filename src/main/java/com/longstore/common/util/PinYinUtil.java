package com.longstore.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * 中文拼音
 */
public class PinYinUtil {

    public static String getPinYinHeaderChar(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        char firstChar = str.charAt(0);
        String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(firstChar);
        String counvert = "";
        if (pinyinArr != null) {
            counvert += pinyinArr[0].charAt(0);
        } else {
            counvert += firstChar;
        }
        return counvert.trim().replace(" ", "").toUpperCase();
    }
    
    public static String pinYin(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        StringBuffer pinyinName = new StringBuffer();
        char[] nameChar = str.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        int ncLength = nameChar.length;
        int ncLength2 = ncLength - 1;
        for (int i = 0; i < ncLength; i++) {
            if (nameChar[i] > 128) {
                if (i < ncLength2) {
                    String py = duoyinMap.get(nameChar[i] + "-" + nameChar[i + 1]);
                    if (py != null) {
                        pinyinName.append(py);
                        i++;
                        continue;
                    }
                }
                try {
                    String[] strs = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if (strs != null && strs.length > 0) {
                        pinyinName.append(strs[0]);
                    }else{
                        pinyinName.append(nameChar[i]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName.append(nameChar[i]);
            }
        }
        return pinyinName.toString();
    }
    
    private final static Map<String, String> duoyinMap = new HashMap<String, String>(100);
    static{
        duoyinMap.put('长' + "-" + '春', "CHANGCHUN");
        duoyinMap.put('长' + "-" + '沙', "CHANGSHA");
        duoyinMap.put('长' + "-" + '治', "CHANGZHI");
        duoyinMap.put('成' + "-" + '都', "CHENGDU");
        duoyinMap.put('重' + "-" + '庆', "CHONGQING");
        duoyinMap.put('厦' + "-" + '门', "XIAMEN");
    }
    
}
