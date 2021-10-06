package com.smilehappiness.framework.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smilehappiness.framework.entity.ApiLogger;

import java.util.List;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author smilehappiness
 * @since 2021-10-02
 */
public interface ApiLoggerService extends IService<ApiLogger> {

    /**
     * <p>
     * 通过请求url和方法名称，获取日志信息列表
     * <p/>
     *
     * @param requestUrl
     * @param methodName
     * @return java.util.List<com.smilehappiness.framework.model.ApiLogger>
     * @Date 2021/10/4 11:51
     */
    List<ApiLogger> getApiLoggerInfoByRequestUrlAndMethodName(String requestUrl, String methodName);
}
