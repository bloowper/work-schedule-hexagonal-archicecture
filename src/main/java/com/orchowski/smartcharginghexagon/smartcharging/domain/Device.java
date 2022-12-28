package com.orchowski.smartcharginghexagon.smartcharging.domain;

import java.time.Instant;
import java.util.List;

public class Device {
    private String id;
    List<Policy> policies;

    public Device(String id) {
        this.id = id;
    }

    void addNewPolicy(Policy policy) {
        policies.add(policy);
    }

    WorkSchedule generateWorkSchedule(Instant startOfWorkSchedule) {
        SchedulerCreator schedulerCreator = new SchedulerCreator(policies);
        return schedulerCreator.createWorkSchedule();
    }
}
