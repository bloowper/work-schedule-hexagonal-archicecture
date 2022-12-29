package com.orchowski.smartcharginghexagon.smartcharging.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter//[Question] HOW TO GET RID OF PUBLIC SETTERS IN THIS ARCH???????????????????
public class Device {
    //TODO PROVIDED domain exceptions
    private String id;
    List<Policy> policies;

    public Device(String id) {
        this.id = id;
    }

    void addNewPolicy(Policy policy) {
        validateNewPolicy(policy);
        policies.add(policy);
    }


    WorkSchedule generateWorkSchedule() {
        SchedulerCreator schedulerCreator = new SchedulerCreator(policies);
        return schedulerCreator.createWorkSchedule();
    }

    private void validateNewPolicy(Policy policy) {
        List<Policy> policiesInRange = getPoliciesInRange(policy.getStartDate(), policy.getEndDate());
        policiesInRange.stream()
                .filter(p -> p.getPriority().equals(policy.getPriority()))
                .findFirst()
                .ifPresent(p -> {throw new IllegalArgumentException("Two policies with the same priority must not overlap");});
    }

    /**
     * external points are excluded
     */
    private List<Policy> getPoliciesInRange(Instant startDate, Instant endDate) {
        return policies.stream()
                .filter(policy -> policy.getStartDate().compareTo(endDate) > 0 || policy.getEndDate().compareTo(startDate) > 0)
                .toList();
    }
}
