package com.easybug.quartz;

public class TriggerBean {
    private String triggerName;
    private String triggerGroup;

    public TriggerBean(String triggerName, String triggerGroup) {
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
    }

    public TriggerBean(){}
    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }
}
