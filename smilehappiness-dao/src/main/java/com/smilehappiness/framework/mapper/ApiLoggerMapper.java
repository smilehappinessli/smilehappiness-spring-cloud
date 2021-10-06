package com.smilehappiness.framework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smilehappiness.framework.entity.ApiLogger;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author smilehappiness
 * @since 2021-10-02
 */
@Repository
public interface ApiLoggerMapper extends BaseMapper<ApiLogger> {

}
