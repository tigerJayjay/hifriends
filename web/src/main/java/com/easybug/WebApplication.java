package com.easybug;

import com.easybug.activemq.Consumer;
import com.easybug.activemq.Producer;
import com.easybug.common.SpringContextUtils;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@EnableTransactionManagement
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
