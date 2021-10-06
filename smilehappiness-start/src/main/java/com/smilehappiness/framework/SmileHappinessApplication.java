package com.smilehappiness.framework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>
 * 服务启动类
 * 如果需要排除自动装配，可以使用 @SpringBootApplication(exclude = {RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class, DataSourceAutoConfiguration.class})
 * <p/>
 *
 * @author smilehappiness
 * @Date 2021/5/25 13:23
 */
@SpringBootApplication(scanBasePackages = {"com.smilehappiness"})
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan(basePackages = "com.smilehappiness.**.mapper")
public class SmileHappinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmileHappinessApplication.class, args);
    }

}
