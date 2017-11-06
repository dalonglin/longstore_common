package com.longstore.common.domain.pay;

public class AliPayParams extends PayParams {
    private static final long serialVersionUID = 5976227205860270719L;
    
    private String url;
    
    public AliPayParams(){
        
    }
    
    public AliPayParams(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
