package com.easybug.service.token;

import com.easybug.model.TokenBlackList;

import java.util.List;

public interface ITokenService {
    /**
     * 插入黑名单
     * @return
     */
    Integer insertTokenList(TokenBlackList tokenBlackList);

    /**
     * 获取所有黑名单
     * @return
     */
    List<TokenBlackList> getList();
}
