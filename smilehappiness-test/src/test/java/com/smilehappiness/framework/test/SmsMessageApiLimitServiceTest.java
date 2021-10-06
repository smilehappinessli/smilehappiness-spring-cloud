package com.smilehappiness.framework.test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.smilehappiness.framework.test.model.SmsMessage;
import com.smilehappiness.framework.test.service.SmsMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * <p>
 * 消息发送服务，限流功能测试
 * <p/>
 *
 * @author smilehappiness
 * @Date 2020/7/5 22:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsMessageApiLimitServiceTest {

    @Resource
    private SmsMessageService smsMessageService;

    /**
     * <p>
     * 消息发送服务，限流功能测试-单条不会限流
     * <p/>
     *
     * @param
     * @return void
     * @Date 2020/7/5 22:25
     */
    @Test
    public void testSmsMessageSend() {
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setMsgKey("register-user");
        smsMessage.setContent("register an user notice!");
        smsMessageService.sendSmsMessage(smsMessage);
    }

    /**
     * <p>
     * 消息发送服务，限流功能多线程环境下测试（超过限定次数就会限流）
     * <p/>
     *
     * @param
     * @return void
     * @Date 2020/7/5 22:45
     */
    @Test
    public void testSmsMessageSendBatch() {
        //循环次数
        int count = 15;

        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("smsLimit-pool-%d").build();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, corePoolSize * 2 + 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), nameThreadFactory);
        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            threadPoolExecutor.execute(() -> {
                SmsMessage smsMessage = new SmsMessage();
                smsMessage.setMsgKey("register-user");
                smsMessage.setContent("register an user notice!");
                //业务方法，添加了@ApiLimit(limitCounts = 10, timeSecond = 120)注解，表示2分钟只允许10次调用，否则就会限流
                smsMessageService.sendSmsMessage(smsMessage);
                countDownLatch.countDown();
            });
        }

        try {
            boolean await = countDownLatch.await(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }

        System.out.println("处理完成啦...");
    }

}
