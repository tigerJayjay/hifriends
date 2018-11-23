package com.easybug.web;

import com.easybug.dao.config.DataSourceConfiguration;
import com.easybug.dao.config.SessionFactoryConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DataSourceConfiguration.class, SessionFactoryConfiguration.class})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
