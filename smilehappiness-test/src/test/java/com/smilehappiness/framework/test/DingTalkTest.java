package com.smilehappiness.framework.test;

import com.smilehappiness.early.warning.DingTalkWarningNoticeServer;
import com.smilehappiness.framework.SmileHappinessApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 * 钉钉预警通知测试
 * <p/>
 *
 * @author smilehappiness
 * @Date 2021/10/3 14:18
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmileHappinessApplication.class)
public class DingTalkTest {

    @Autowired
    private DingTalkWarningNoticeServer dingTalkWarningNoticeServer;

    /**
     * 钉钉预警通知测试，注意，如果你设定的机器人类型是关键字，内容需要包含关键字，才可以发送通知成功
     */
    @Test
    public void testDingTalkNotice() {
        dingTalkWarningNoticeServer.sendWarningMessage("smile:这是一个警告的通知");
        //第一个参数title，可以理解为关键信息标识，后续跟踪日志可以使用该关键信息标识快速找到
        dingTalkWarningNoticeServer.sendWarningMessage("hello，", "smile:这是一个警告的通知");

        try {
            log.info("结果：{}", 1 / 0);
        } catch (Exception e) {
            dingTalkWarningNoticeServer.sendErrorMessage(StringUtils.join("smile:异常通知，原因：", e.getMessage()));
            dingTalkWarningNoticeServer.sendErrorMessage("world", StringUtils.join("smile:异常通知，原因：", e.getMessage()));
        }
    }
}
