package com.longstore.common.msg;

import java.io.Serializable;

/**
 * 发送消息
 */
public abstract class IMsgProducer {
	
	/** 消息队列--短信  */
	public static final String MSG_QUEUE_SMS = "quene_sms";
    /** 消息队列-- 操作日志 */
	public static final String MSG_QUEUE_OPERATION_LOG = "quene_log";
	
    /**
     * 发送消息
     * @param queue 队列名称
     * @param msg   消息内容
     */
	public abstract boolean publish(String queue, String msg);
	
    /**
     * 发送消息
     * @param queue 队列名称
     * @param msg   消息内容
     */
	public abstract boolean publis(String queue, Serializable object);
		
}
