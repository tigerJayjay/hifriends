package com.easybug.listener;

import com.easybug.cache.BlackListCacheManager;
import com.easybug.common.SysException;
import com.easybug.model.TokenBlackList;
import com.easybug.netty.server.ServerInit;
import com.easybug.quartz.CodeGenerJob;
import com.easybug.quartz.JobBean;
import com.easybug.quartz.JobHandler;
import com.easybug.quartz.TriggerBean;
import com.easybug.service.token.ITokenService;
import org.apache.shiro.cache.Cache;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 应用启动开启任务
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private JobHandler jobHandler;
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private BlackListCacheManager cacheManager;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent){
        ApplicationContext ac = applicationReadyEvent.getApplicationContext();
        if(applicationReadyEvent.getApplicationContext().getParent()==null){
            startJob();
            //startBlackList();
            nettyServerStart();
        }
    }

    private void startJob(){
        JobBean jobBean  = new JobBean("codeJob","sysJobGroup","0 53 12 * * ?","com.easybug.quartz.CodeGenerJob");
        TriggerBean triggerBean = new TriggerBean("codeGeneratorTrigger","sysTriggerGroup");
        jobBean.setTrigger(triggerBean);
        jobHandler.startJob(jobBean);
    }

    private void nettyServerStart(){
        new Thread(new ServerInit("0.0.0.0",8082)).start();
    }


    /**
     * 开启黑名单缓存
     */
    /*private void startBlackList(){
        logger.info("token黑名单缓存开启");
        List<TokenBlackList> tokens = tokenService.getList();
         if(tokens.size()>0){
            cacheManager.setPrefix("tokenList:");
            Cache cache = cacheManager.getCache("blackList");
            for(TokenBlackList t:tokens){
                cacheManager.getRedisManager().setExpire(t.getExpiredTime().getTime()-);
                cache.put(String.valueOf(t.getId()),t.getToken());
            }
        }
        logger.info("token黑名单缓存结束");
    }*/

}