package com.easybug.listener;

import com.easybug.cache.BlackListCacheManager;
import com.easybug.common.SysException;
import com.easybug.model.TokenBlackList;
import com.easybug.quartz.CodeGenerJob;
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
    Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
    @Autowired
    private Scheduler scheduler;
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
        }
    }

    /**
     * 开启定时任务
     */
    private void startJob() {
        logger.info("定时任务开启");
        JobKey jobKey = new JobKey("codeJob_1", "codeGroup_1");
        try {
            List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
            if (triggersOfJob.size() == 0) {
                JobDetail jobDetail = JobBuilder.newJob(CodeGenerJob.class).withIdentity("codeJob_1", "codeGroup_1").build();
                CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule("0 53 12 * * ?").withMisfireHandlingInstructionDoNothing();
                CronTrigger trigger = TriggerBuilder.newTrigger().withSchedule(builder).withIdentity("codeTrigger_1", "codeGroup_1").build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                scheduler.resumeJob(jobKey);
            }
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new SysException("任务开启异常", 500);
        }
        logger.info("定时任务开启结束");
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