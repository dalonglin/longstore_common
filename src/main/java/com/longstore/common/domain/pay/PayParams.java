package com.longstore.common.domain.pay;

import java.io.Serializable;

public class PayParams implements Serializable {
    private static final long serialVersionUID = 848576838197274192L;

    public PayParams() {
        
    }
    public String orderNo;//商家订单编号
    public Double amount;//当前支付的金额
    public String payTypeName;//支付方式名称
    
    public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getPayTypeName() {
        return payTypeName;
    }
    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }
}
