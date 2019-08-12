package com.easybug.service.paramset;

import com.easybug.dao.StandardCodeTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StandardCodeType implements IStandardCodeType {
    @Autowired
    private StandardCodeTypeDao standardCodeTypeDao;
    @Override
    public boolean insertStandardCodeType(com.easybug.model.StandardCodeType standardCodeType) {
        if(standardCodeTypeDao.insertSelective(standardCodeType)>0){
            return true;
        }
        return false;
    }
}
