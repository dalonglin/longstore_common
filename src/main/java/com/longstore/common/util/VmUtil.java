package com.longstore.common.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * 页面工具
 */
public class VmUtil {
    private final static char c = '/';
    private static String imgUrl = null;
    private static String getImgUrl(){
    	if (imgUrl == null) {
			imgUrl = WebConfigUtil.image();
		}
    	return imgUrl;
    }

    /**
     * 转换获取img的url的大小，width 和 height 同时为0，不改变大小。
     * @param url
     * @param width
     * @param height
     */
    public final static String imgUrl(String url, int width, int height) {
        if (StringUtils.isBlank(url)) {
            return "";
        }
        if (url.startsWith("http://") || url.startsWith("https://")) {
			
		}else if (c == url.charAt(0)) {
        	url = getImgUrl() + url;
        }else{
            url = getImgUrl() + "/" + url;
        }
        if (width > 0) {
			if (height > 0) {
		        return url + "@w_" + width + ",h_" + height;
			}else{
		        return url + "@w_" + width;
			}
		}else{
			if (height > 0) {
		        return url + "@h_" + height;
			}
		}
        return url;
    }

    /**
     * 数字格式化，不要小数位
     * @param val
     */
    public final static String priceF(Double val){
    	return Arith.format0p(val);
    }
    
    /**
     * 数字格式化，保留两个小数位
     * @param val
     */
    public final static String priceF2(Double val){
        return Arith.format2p(val);
    }
    
    /**
     * 将数字类型（Double）转换为字符串类型
     * 不保留小数位
     */
    public static String toString(Double v) {
        return v.toString();
    }
    
    /**
     * 屏蔽手机
     */
    public final static String hideTel(Object obj) {
        if (obj != null) {
            return LocalUtil.hideTel(obj.toString());
        }
        return "";
    }

    /**
     * json转换
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return "{}";
        }
        return JsonUtil.toJson(obj);
    }

    /**
     * js转换，对英文的单引号和双引号进行中文符号转换
     */
    public static String js(String str) {
        if (str == null) {
            return "";
        }
        return str.replaceAll("\"", "“").replaceAll("'", "’");
    }

    /**
     * 截取字符
     */
    public static String subStr(String str, int length) {
    	if (StringUtils.isBlank(str) || str.length() < length) {
			return str;
		}
        return str.substring(0, length);
    }
    
    /**
     * 把html标签字符串 转义为 html标签  如：&lt;p&gt;这是内容&lt;/p&gt; 转义为 <p>这是内容</p> 
     * @param content
     * @return String
     */
    public static String htmlUnescape(String content){
        if(StringUtils.isBlank(content)){
            return "";
        }
        return HtmlUtils.htmlUnescape(content);
    }
    
    /**
     * 把 html标签  转义为 html标签字符串  如：<p>这是内容</p> 转义为 &lt;p&gt;这是内容&lt;/p&gt; 
     * @param content
     * @return String
     */
    public static String htmlEscape(String content){
        if(StringUtils.isBlank(content)){
            return "";
        }
        return HtmlUtils.htmlEscape(content);
    }
    
}
