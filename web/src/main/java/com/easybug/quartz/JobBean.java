package com.easybug.quartz;

import org.quartz.Trigger;

import java.util.ArrayList;
import java.util.List;

public class JobBean {
    private String jobName;
    private String jobGroup;
    private String cron;
    private List<TriggerBean> triggers = new ArrayList<>();
    private String jobClass;

    public JobBean(String jobName, String jobGroup, String cron, String jobClass) {
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.cron = cron;
        this.jobClass = jobClass;
    }

    public JobBean(){

    }
    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public List<TriggerBean> getTriggers() {
        return triggers;
    }

    public void setTrigger(TriggerBean trigger) {
        this.triggers.add(trigger);
    }
}
