package com.easybug.service.paramset;

import com.easybug.dao.StandardCodeDao;
import com.easybug.model.StandardCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StandardCodeServiceImpl implements IStandardCodeService{
    @Autowired
    private StandardCodeDao standardCodeDao;


    @Override
    public boolean insertStandardCode(StandardCode standardCode) {
        if(standardCodeDao.insertSelective(standardCode)>0){
            return true;
        }
        return false;
    }
}
