package com.smilehappiness.framework.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 分页拦截器
 * <p/>
 *
 * @author smilehappiness
 * @Date 2021/5/20 21:11
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor mybatisPlusInterceptor() {
        return new PaginationInterceptor();
    }

}