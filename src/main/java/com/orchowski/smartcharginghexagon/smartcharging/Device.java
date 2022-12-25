package com.orchowski.smartcharginghexagon.smartcharging;

import java.time.Instant;
import java.util.List;

class Device {
    private String uuid;
    List<Policy> policies;

    void addNewPolicy(Policy policy) {
        policies.add(policy);
    }

    WorkSchedule generateWorkSchedule(Instant startOfWorkSchedule) {
        SchedulerCreator schedulerCreator = new SchedulerCreator(policies);
        return schedulerCreator.createWorkSchedule();
    }
}
