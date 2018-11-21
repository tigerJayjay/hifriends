package com.example.websocket.dao;

import com.example.websocket.config.DataSourceConfiguration;
import com.example.websocket.config.SessionFactoryConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@Import({DataSourceConfiguration.class, SessionFactoryConfiguration.class})
@SpringBootTest
public class TestDaoTest {
    @Autowired
    private  TestDao testDao;
    @Test
    public void queryTest() {
        List<com.example.websocket.entity.Test> tests = testDao.queryTest();
        for(com.example.websocket.entity.Test t:tests){
            System.out.println(t.getId());
        }
    }

    @Test
    public void insertTest() {
        com.example.websocket.entity.Test test = new com.example.websocket.entity.Test();
       test.setTid(6);
        int id = testDao.insertTest(test);
        System.out.println(test.getId());
    }
}