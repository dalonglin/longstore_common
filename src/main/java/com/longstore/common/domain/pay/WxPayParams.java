package com.longstore.common.domain.pay;

public class WxPayParams extends PayParams {
    private static final long serialVersionUID = -5106891706110424198L;

    private String            appId;
    private String            partnerId;
    private String            prepayId;
    private String            strPackage;
    private String            nonceStr;
    private String            timeStamp;
    private String            sign;
    private String            codeUrl;                                 //微信二维码地址
    private String picBase64;//微信二维码图片base64位字符串

    public String getPicBase64() {
        return picBase64;
    }

    public void setPicBase64(String picBase64) {
        this.picBase64 = picBase64;
    }
    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getStrPackage() {
        return strPackage;
    }

    public void setStrPackage(String strPackage) {
        this.strPackage = strPackage;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
