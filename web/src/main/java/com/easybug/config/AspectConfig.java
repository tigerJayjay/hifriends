package com.easybug.config;

import com.easybug.cache.BlackListAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 开启aspectJ自动代理
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
    @Bean
    public BlackListAop blackListAop(){
        return new BlackListAop();
    }
}
