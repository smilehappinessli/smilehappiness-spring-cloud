package com.smilehappiness.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smilehappiness.aspect.operate.IOperateLogStore;
import com.smilehappiness.aspect.operate.OperateLogBaseInfo;
import com.smilehappiness.exception.exceptions.SystemInternalException;
import com.smilehappiness.framework.entity.ApiLogger;
import com.smilehappiness.framework.mapper.ApiLoggerMapper;
import com.smilehappiness.framework.service.ApiLoggerService;
import com.smilehappiness.utils.DozerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author smilehappiness
 * @since 2021-10-02
 */
@Service
public class ApiLoggerServiceImpl extends ServiceImpl<ApiLoggerMapper, ApiLogger> implements ApiLoggerService, IOperateLogStore {

    /**
     * <p>
     * 存储操作日志信息
     * <p/>
     *
     * @param logList
     * @return void
     * @Date 2021/10/2 18:25
     */
    @Override
    public void store(List<OperateLogBaseInfo> logList) {
        //保存日志信息
        List<ApiLogger> apiLoggerList = DozerUtil.transForList(logList, ApiLogger.class);
        //注：这里直接操作性能不是很高，建议在mapper.xml进行批量保存
        saveBatch(apiLoggerList);
    }

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
    @Override
    public List<ApiLogger> getApiLoggerInfoByRequestUrlAndMethodName(String requestUrl, String methodName) {
        //两个条件至少有一个，否则不允许查询，因为数据量会很大
        if (StringUtils.isBlank(requestUrl) && StringUtils.isBlank(methodName)) {
            throw new SystemInternalException("通过请求url和方法名称，获取日志信息列表时，参数不允许为空");
        }

        QueryWrapper<ApiLogger> ew = new QueryWrapper<>();
        ew.eq(StringUtils.isNotBlank(requestUrl), "request_url", requestUrl);
        ew.eq(StringUtils.isNotBlank(methodName), "method_name", methodName);
        ew.orderByDesc("created_time");
        return baseMapper.selectList(ew);
    }
}
