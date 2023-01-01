package com.orchowski.smartcharginghexagon.workschedule.adapters;

import com.orchowski.smartcharginghexagon.workschedule.domain.WorkScheduleService;

public class ContextCreator {
    // This class exist only because want to keep classes from adapter as package private
    // [question] is this ok?

    public static WorkScheduleService getWorkScheduleService() {
        SmartChargingBeanConfiguration smartChargingBeanConfiguration = new SmartChargingBeanConfiguration(new DeviceEntityInMemoryRepository(), new EntityMapperImpl());
        WorkScheduleService workScheduleService = smartChargingBeanConfiguration.scheduleService();
        return workScheduleService;
    }
}
