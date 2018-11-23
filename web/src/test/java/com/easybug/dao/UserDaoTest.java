package com.easybug.dao;

import com.easybug.dao.config.DataSourceConfiguration;
import com.easybug.dao.config.SessionFactoryConfiguration;
import com.easybug.model.User;
import com.easybug.web.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest(classes = {WebApplication.class})
@RunWith(SpringRunner.class)
@Import({DataSourceConfiguration.class, SessionFactoryConfiguration.class})
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    @Test
    public void queryUser() {
        List<User> users = userDao.queryUser();
        for(User user:users){
            System.out.println(user.getUserName());
        }
    }

    @Test
    public void insertUser() {
    }
}