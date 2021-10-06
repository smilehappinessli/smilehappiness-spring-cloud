package com.smilehappiness.framework.generator;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 基类
 * <p/>
 *
 * @author smilehappiness
 * @Date 2021/9/27 10:10
 */
@Data
public class BaseVo<T> implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 创建人 格式 账号名/登录用户名
     */
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updatedTime;

    /**
     * 修改人 格式 账号名/登录用户名
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updatedBy;

    private Date deleteTime;

    /**
     * 删除人  格式 账号名/登录用户名
     */
    private String deleteBy;

    @TableLogic
    private boolean isDelete;

    /**
     * 乐观锁版本号
     */
    private String version;

    /**
     * 备注
     */
    private String remark;
}
