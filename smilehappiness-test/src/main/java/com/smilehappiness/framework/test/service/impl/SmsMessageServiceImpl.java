package com.smilehappiness.framework.test.service.impl;

import com.smilehappiness.framework.test.model.SmsMessage;
import com.smilehappiness.framework.test.service.SmsMessageService;
import com.smilehappiness.limit.annotation.ApiLimit;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息服务接口实现类
 * <p/>
 *
 * @author smilehappiness
 * @Date 2020/7/5 22:01
 */
@Service
public class SmsMessageServiceImpl implements SmsMessageService {

    /**
     * <p>
     * 根据消息模板以及内容，发送短信
     * 默认一分钟，限流500次，可以根据实际情况进行限流
     * <p/>
     *
     * @param smsMessage
     * @return void
     * @Date 2020/7/6 21:35
     */
    @ApiLimit(limitCounts = 10, timeSecond = 120, limitApiName = "sendSmsMessage")
    @Override
    public void sendSmsMessage(SmsMessage smsMessage) {
        // 注意：一般三方可能会限制，400/s，即每秒最多发送400条，超过这个限制的短信发送请求会被拒绝，所以需要限流，在高流量下，需要在业务端限制，每秒访问不要超过400次
        // 这里只是模拟这种限流的场景，具体的限流大小，根据实际场景去设置

        // TODO 调用第三方发送短信
        System.out.println("【" + Thread.currentThread().getName() + "】 调用三方短信服务，发送短信成功！");
    }
}
