package com.easybug.dao;

import com.easybug.entity.Test;

import java.util.List;

public interface TestDao {
    List<Test> queryTest();
    int insertTest(Test test);
}
