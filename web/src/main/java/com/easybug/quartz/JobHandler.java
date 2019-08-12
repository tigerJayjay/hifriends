package com.easybug.quartz;

import com.easybug.common.SysException;
import com.easybug.listener.ApplicationStartup;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobHandler {
    Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
    @Autowired
    private Scheduler scheduler;

    /**
     * 开启定时任务
     */
    public  void startJob(JobBean jobBean){
        logger.info("定时任务开启");
        JobKey jobKey = new JobKey(jobBean.getJobName(), jobBean.getJobGroup());
        try {
        Class jobClass = Class.forName(jobBean.getJobClass());
            List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
            if (triggersOfJob.size() == 0) {
                JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobBean.getJobName(), jobBean.getJobGroup()).build();
                CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(jobBean.getCron()).withMisfireHandlingInstructionDoNothing();
                for(TriggerBean triggerBean:jobBean.getTriggers()){
                    CronTrigger trigger = TriggerBuilder.newTrigger().withSchedule(builder).withIdentity(triggerBean.getTriggerName(),triggerBean.getTriggerGroup()).build();
                    scheduler.scheduleJob(jobDetail, trigger);
                }
            } else {
                scheduler.resumeJob(jobKey);
            }
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new SysException("任务开启异常", 500);
        }catch (ClassNotFoundException e){
            throw  new SysException(this.getClass().getName()+":找不到Job类！",500);
        }
        logger.info("定时任务开启结束");
    }

    /**
     * 暂停任务
     */
    public void pauseJob(JobBean jobBean) throws SchedulerException{
        JobKey jobKey = new JobKey(jobBean.getJobName(), jobBean.getJobGroup());
        scheduler.pauseJob(jobKey);
    }


    /**
     * 恢复任务
     */
    public void resumeJob(JobBean jobBean)throws SchedulerException{
        JobKey jobKey = new JobKey(jobBean.getJobName(), jobBean.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除任务
     */
    public void deleteJob(JobBean jobBean)throws SchedulerException{
        JobKey jobKey = new JobKey(jobBean.getJobName(), jobBean.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 更新任务
     */
    public void updateJob(JobBean jobBean)throws SchedulerException{
       for(TriggerBean triggerBean:jobBean.getTriggers()){
           TriggerKey triggerKey = TriggerKey.triggerKey(triggerBean.getTriggerName(),triggerBean.getTriggerGroup());
           CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);

           CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(jobBean.getCron()).withMisfireHandlingInstructionDoNothing();
           trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(builder).build();
           scheduler.rescheduleJob(triggerKey,trigger);
       }
    }
}
