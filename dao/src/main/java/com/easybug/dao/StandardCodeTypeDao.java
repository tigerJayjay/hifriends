package com.easybug.dao;

import com.easybug.model.StandardCodeType;

public interface StandardCodeTypeDao {
    int insert(StandardCodeType record);

    int insertSelective(StandardCodeType record);
}