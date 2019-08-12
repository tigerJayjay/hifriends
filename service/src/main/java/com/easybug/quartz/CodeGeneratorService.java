package com.easybug.quartz;

import com.easybug.dao.CodeGeneratorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CodeGeneratorService {
    @Autowired
    private CodeGeneratorDao codeDao;

    /**
     * 生成账号
     * @param startCode  起始范围
     * @param endCode 结束范围
     */
    public void generatorCode(Integer startCode,Integer endCode){
        HashMap condition = new HashMap(2);
        condition.put("startCode",startCode);
        condition.put("endCode",endCode);
        codeDao.generatorCode(condition);
    }

    /**
     * 获取当前剩余账号数量
     * @return
     */
    public Integer getLeftCount(){
        return codeDao.getLeftCount();
    }

    /**
     * 获取新账号
     * @return
     */
    public Integer getNewCode(){
        return codeDao.getNewCode();
    }

    /**
     * 获取最大号码
     * @return
     */
    public Integer getMaxCode(){
        return codeDao.getMaxCode();
    }
}
