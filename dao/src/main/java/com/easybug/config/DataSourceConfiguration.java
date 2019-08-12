package com.easybug.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
@MapperScan("com.easybug.dao")
public class DataSourceConfiguration {
    @Value("${jdbc.driver}")
    private String jdbcDriver;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;


    @Bean(name="dataSource")
    public DataSource createDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(jdbcDriver);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setInitialPoolSize(3);
        dataSource.setMinPoolSize(3);
        dataSource.setMaxPoolSize(15);
        //每次获取连接的个数
        dataSource.setAcquireIncrement(1);
        //获取连接超时时间 ms
        dataSource.setCheckoutTimeout(5000);
        //心跳检测时间 s
        dataSource.setIdleConnectionTestPeriod(90);
        //最大空闲时间 s
        dataSource.setMaxIdleTime(1800);
        //归还连接检测
        dataSource.setTestConnectionOnCheckin(false);
        //获取连接检测
        dataSource.setTestConnectionOnCheckout(false);
        //关闭连接后不自动commit，事务才会起作用
        dataSource.setAutoCommitOnClose(false);
        return dataSource;
    }
}
