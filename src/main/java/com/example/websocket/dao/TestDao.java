package com.example.websocket.dao;

import com.example.websocket.entity.Test;

import java.util.List;

public interface TestDao {
    List<Test> queryTest();
    int insertTest(Test test);
}
