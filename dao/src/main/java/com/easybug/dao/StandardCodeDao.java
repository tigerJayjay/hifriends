package com.easybug.dao;

import com.easybug.model.StandardCode;

public interface StandardCodeDao {
    int insert(StandardCode record);

    int insertSelective(StandardCode record);
}