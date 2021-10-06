package com.smilehappiness.framework.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.smilehappiness.framework.generator.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 操作日志记录
 * </p>
 *
 * @author smilehappiness
 * @since 2021-10-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ApiLogger对象", description = "操作日志记录")
@TableName("api_logger")
public class ApiLogger extends BaseVo<ApiLogger> {

    private static final long serialVersionUID = 3094613624783765659L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "请求url")
    private String requestUrl;

    @ApiModelProperty(value = "请求方法名")
    private String methodName;

    @ApiModelProperty(value = "请求类名")
    private String className;

    @ApiModelProperty(value = "请求方式")
    private String requestType;

    @ApiModelProperty(value = "业务id，可为空，用户系统中，存储申请单id")
    private String bizId;

    @ApiModelProperty(value = "请求的业务模块名称")
    private String businessModuleName;

    @ApiModelProperty(value = "操作描述")
    private String operationDescribe;

    @ApiModelProperty(value = "请求参数")
    private String requestParams;

    @ApiModelProperty(value = "访问ip")
    private String requestIp;

    @ApiModelProperty(value = "响应结果")
    private String responseStr;

    @ApiModelProperty(value = "接口调用耗时时间：单位毫秒")
    private Integer operationTakeTime;

    @ApiModelProperty(value = "执行错误信息")
    private String errorMessage;

    @TableField("udf_1")
    @ApiModelProperty(value = "扩展字段")
    private String udf1;

    @TableField("udf_2")
    @ApiModelProperty(value = "扩展字段")
    private String udf2;

    @TableField("udf_3")
    @ApiModelProperty(value = "扩展字段")
    private String udf3;


}
