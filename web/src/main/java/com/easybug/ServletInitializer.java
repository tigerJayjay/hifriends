package com.easybug;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import se.jiderhamn.classloader.leak.prevention.ClassLoaderLeakPreventorListener;

@ServletComponentScan
public class ServletInitializer extends SpringBootServletInitializer {

    @Bean
    public ServletListenerRegistrationBean servletRegistrationBean() {
        return new ServletListenerRegistrationBean(new ClassLoaderLeakPreventorListener());
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }

}
