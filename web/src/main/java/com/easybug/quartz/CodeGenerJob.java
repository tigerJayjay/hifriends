package com.easybug.quartz;

import com.easybug.common.PropertyUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class CodeGenerJob implements Job{
    @Autowired
    private CodeGeneratorService codeService;
    private static Integer underLimit;
    private static Integer incre;
    private static Integer start;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        underLimit =  Integer.valueOf(PropertyUtil.getProperty("underLimit"));
        incre = Integer.valueOf(PropertyUtil.getProperty("incre"));
        start = Integer.valueOf(PropertyUtil.getProperty("start"));
        Integer leftCount = codeService.getLeftCount();
        if(leftCount<underLimit){
            Integer maxCode =  codeService.getMaxCode();
            if(maxCode==null){
                maxCode=start;
            }
            codeService.generatorCode(maxCode+1,maxCode+incre);
        }
    }
}
