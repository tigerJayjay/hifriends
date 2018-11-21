package com.easybug.dao;

import com.easybug.config.DataSourceConfiguration;
import com.easybug.config.SessionFactoryConfiguration;
import com.easybug.config.WebSocketConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@Import({WebSocketConfig.class, DataSourceConfiguration.class, SessionFactoryConfiguration.class})
public class TestDaoTest {
    @Autowired
    TestDao testDao;
    @Test
    public void queryTest() {
        testDao.queryTest();
    }

    @Test
    public void insertTest() {
    }
}