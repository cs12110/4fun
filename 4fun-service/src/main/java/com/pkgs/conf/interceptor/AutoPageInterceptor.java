package com.pkgs.conf.interceptor;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 分页插件
 * <p/>
 *
 * @author cs12110 created at: 2019/1/7 9:21
 * <p>
 * since: 1.0.0
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.pkgs.mapper")
public class AutoPageInterceptor {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
