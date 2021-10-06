package com.smilehappiness.framework.action;


import com.alibaba.fastjson.JSON;
import com.smilehappiness.aspect.operate.OperateLog;
import com.smilehappiness.exception.exceptions.BusinessException;
import com.smilehappiness.exception.exceptions.SystemInternalException;
import com.smilehappiness.framework.entity.ApiLogger;
import com.smilehappiness.framework.enums.FrameworkBusinessExceptionEnum;
import com.smilehappiness.framework.service.ApiLoggerService;
import com.smilehappiness.result.CommonResult;
import com.smilehappiness.utils.DateUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author smilehappiness
 * @since 2021-10-02
 */
@Slf4j
@Api(value = "ApiLoggerController", tags = "ApiLoggerController服务")
@RestController
@RequestMapping("/apiLogger")
public class ApiLoggerController {

    private ApiLoggerService apiLoggerService;

    public ApiLoggerController(ApiLoggerService apiLoggerService) {
        this.apiLoggerService = apiLoggerService;
    }

    /**
     * <p>
     * 通过请求url和方法名称，获取日志信息列表
     * <p/>
     *
     * @param requestUrl
     * @param methodName
     * @return CommonResult<List < ApiLogger>>
     * @Date 2021/10/4 11:46
     */
    @ApiOperation(notes = "通过请求url和方法名称，获取日志信息列表", value = "getApiLoggerInfoByRequestUrlAndMethodName")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestUrl", value = "请求url", type = "String"),
            @ApiImplicitParam(name = "methodName", value = "方法名称", type = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "通过请求url和方法名称，获取日志信息列表成功"),
            @ApiResponse(code = 400, message = "请求参数有误"),
            @ApiResponse(code = 500, message = "服务器内部异常，请稍后再试")
    })
    @OperateLog("通过请求url和方法名称，获取日志信息列表")
    @GetMapping("/getApiLoggerInfoByRequestUrlAndMethodName")
    public CommonResult<List<ApiLogger>> getApiLoggerInfoByRequestUrlAndMethodName(@RequestParam("requestUrl") String requestUrl, @RequestParam("methodName") String methodName) {
        LocalDateTime bizTimeStart = LocalDateTime.now();
        CommonResult<List<ApiLogger>> commonResult = new CommonResult<>();
        try {
            List<ApiLogger> apiLoggerList = apiLoggerService.getApiLoggerInfoByRequestUrlAndMethodName(requestUrl, methodName);
            if (CollectionUtils.isEmpty(apiLoggerList)) {
                throw new BusinessException(FrameworkBusinessExceptionEnum.API_LOGGER_INFO_NULL);
            }

            log.info("通过请求url和方法名称，获取日志信息接口返回结果:{}", JSON.toJSONString(commonResult));
            log.info("通过请求url和方法名称，获取日志信息方法执行耗时（毫秒）：{}", DateUtil.getTakeTime(bizTimeStart, LocalDateTime.now(), TimeUnit.MILLISECONDS));

            return commonResult;
        } catch (BusinessException e) {
            log.error("【业务异常】通过请求url和方法名称，获取日志信息异常，异常原因：{}", e.getMessage());
            log.info("通过请求url和方法名称，获取日志信息方法执行耗时（毫秒）：{}", DateUtil.getTakeTime(bizTimeStart, LocalDateTime.now(), TimeUnit.MILLISECONDS));

            throw new BusinessException(e.getCode(), e.getBizCode(), "通过请求url和方法名称，获取日志信息异常，异常原因：" + e.getMessage());
        } catch (Exception e) {
            log.error("【系统异常】通过请求url和方法名称，获取日志信息异常，异常原因：{}", e.getMessage());
            log.info("通过请求url和方法名称，获取日志信息方法执行耗时（毫秒）：{}", DateUtil.getTakeTime(bizTimeStart, LocalDateTime.now(), TimeUnit.MILLISECONDS));

            throw new SystemInternalException("通过请求url和方法名称，获取日志信息异常，异常原因：" + e.getMessage());
        }
    }
}
