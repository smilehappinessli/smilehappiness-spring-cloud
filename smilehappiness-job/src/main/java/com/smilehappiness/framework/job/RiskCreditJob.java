package com.smilehappiness.framework.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author smilehappiness
 * @since 2021-8-02
 */
@Slf4j
@Component
public class RiskCreditJob {

    /**
     * <p>
     * 风控授信成功数量统计job
     * <p/>
     *
     * @param
     * @return void
     * @Date 2021/10/3 14:42
     */
    @XxlJob(value = "creditCountStatistic")
    public void creditCountStatistic() throws Exception {
        try {
            // 获取参数
            String param = XxlJobHelper.getJobParam();
            log.info("风控授信成功数量统计job执行时，参数：{}", param);
            XxlJobHelper.log("风控授信成功数量统计job执行时，参数：{}", param);

            //TODO 执行业务方法。。。

            log.info("风控授信成功数量统计job执行成功！");
            XxlJobHelper.log("风控授信成功数量统计job执行成功!");

            XxlJobHelper.handleSuccess("风控授信成功数量统计job执行成功！");
        } catch (Exception e) {
            XxlJobHelper.log("风控授信成功数量统计job执行失败，失败原因：{}", e.getMessage());
            XxlJobHelper.handleFail("风控授信成功数量统计job执行失败，失败原因：" + e.getMessage());
        }
    }

}
