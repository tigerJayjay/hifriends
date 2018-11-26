package com.easybug;

import com.easybug.config.DataSourceConfiguration;
import com.easybug.config.SessionFactoryConfiguration;
import com.easybug.config.WebSocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DataSourceConfiguration.class, SessionFactoryConfiguration.class, WebSocketConfig.class})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
