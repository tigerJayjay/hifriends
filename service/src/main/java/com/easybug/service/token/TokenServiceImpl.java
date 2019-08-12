package com.easybug.service.token;

import com.easybug.dao.TokenBlackListDao;
import com.easybug.model.TokenBlackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenServiceImpl implements ITokenService{
    @Autowired
    private TokenBlackListDao tokenBlackListDao;
    /**
     * 将失效Token加入黑名单
     */
    @Override
    public Integer insertTokenList(TokenBlackList tokenBlackList){
        if(tokenBlackListDao.insert(tokenBlackList)>0){
            return tokenBlackList.getId();
        }
        return null;
    }
    @Override
    public List<TokenBlackList> getList(){
        return tokenBlackListDao.getList();
    }
}
