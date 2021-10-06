package com.smilehappiness.framework.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 配置乐观锁version
 * <p/>
 *
 * @author smilehappiness
 * @Date 2021/5/20 10:11
 */
@Configuration
public class OptimisticLockerConfiguration {

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
