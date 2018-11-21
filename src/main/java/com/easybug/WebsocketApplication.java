package com.easybug;

import com.easybug.config.DataSourceConfiguration;
import com.easybug.config.SessionFactoryConfiguration;
import com.easybug.config.WebSocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"com.easybug.controller"})
@Import({WebSocketConfig.class, DataSourceConfiguration.class, SessionFactoryConfiguration.class})
public class WebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);
    }
}
