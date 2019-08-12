package com.easybug.config;

import com.easybug.cache.BlackListAop;
import com.easybug.component.JwtInterceptor;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import se.jiderhamn.classloader.leak.prevention.ClassLoaderLeakPreventorListener;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/hi");
    }

    @Bean
    public ServletListenerRegistrationBean servletRegistrationBean() {
        ServletListenerRegistrationBean listenerRegistrationBean = new ServletListenerRegistrationBean();
        listenerRegistrationBean.setListener(new ClassLoaderLeakPreventorListener());
        return listenerRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean serviceServlet(){
        ServletRegistrationBean serviceServlet = new ServletRegistrationBean(new CXFServlet(),"/service/*");
        return serviceServlet;
    }
}
