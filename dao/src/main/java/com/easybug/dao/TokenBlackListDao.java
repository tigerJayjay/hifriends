package com.easybug.dao;

import com.easybug.model.TokenBlackList;

import java.util.List;

public interface TokenBlackListDao {
    int insert(TokenBlackList record);
    List<TokenBlackList> getList();
}