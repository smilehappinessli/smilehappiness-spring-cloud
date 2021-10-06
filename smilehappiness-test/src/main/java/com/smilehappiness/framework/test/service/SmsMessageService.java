package com.smilehappiness.framework.test.service;

import com.smilehappiness.framework.test.model.SmsMessage;

/**
 * <p>
 * 消息服务接口类
 * <p/>
 *
 * @author smilehappiness
 * @Date 2020/7/5 21:55
 */
public interface SmsMessageService {

    /**
     * <p>
     * 根据消息模板以及内容，发送短信
     * <p/>
     *
     * @param smsMessage
     * @return void
     * @Date 2020/7/6 21:35
     */
    void sendSmsMessage(SmsMessage smsMessage);

}
