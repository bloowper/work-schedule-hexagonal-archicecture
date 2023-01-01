package com.orchowski.smartcharginghexagon.workschedule.domain;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Slf4j
class SchedulerCreator {
    private final List<Policy> policies;

    public SchedulerCreator(List<Policy> policies) {
        if (policies == null || policies.size() == 0) {
            throw new IllegalArgumentException("Policy list cannot be empty or null");
        }
        this.policies = policies.stream()
                .sorted(Comparator.comparing(Policy::getStartDate))
                .toList();
    }

    WorkSchedule createWorkSchedule() {
        /* TODO
        * NEED to refactor this class
        * it would be great to rewrite this to state machine(valid design pattern, not only conceptually as now)
        *  */
        List<TimelinePoint> points = policies.stream()
                .flatMap(policy ->
                        List.of(
                                new TimelinePoint(policy.getStartDate(), TimelinePointType.START, policy.getPriority(), policy),
                                new TimelinePoint(policy.getEndDate(), TimelinePointType.END, policy.getPriority(), policy)
                        ).stream()
                )
                .sorted(Comparator.comparing(TimelinePoint::getInstant))
                .toList();
        log.debug("Points from which schedule will be built [{}]", points);
        List<WorkShift> workShifts = new ArrayList<>(points.size() - 1);
        TimelinePoint previousPoint = null;
        WorkShift.WorkShiftBuilder workShiftBuilder = WorkShift.builder();
        for (TimelinePoint point : points) {
            if (previousPoint == null || point.getType().equals(TimelinePointType.START) && previousPoint.getType().equals(TimelinePointType.END)) {
                workShiftBuilder.startDate(point.getInstant());
                workShiftBuilder.powerLimit(point.getOriginPolicy().getMaximumPower());
                previousPoint = point;
            }
            else if (point.getType().equals(TimelinePointType.START)  && point.getPriority() > previousPoint.getPriority()) {
                workShiftBuilder.endDate(point.getInstant());
                workShifts.add(workShiftBuilder.build());
                workShiftBuilder.startDate(point.getInstant());
                workShiftBuilder.powerLimit(point.getOriginPolicy().getMaximumPower());
                previousPoint = point;
            } else if (point.getType().equals(TimelinePointType.END) && point.getOriginPolicy() == previousPoint.getOriginPolicy()) {
                workShiftBuilder.endDate(point.getInstant());
                workShifts.add(workShiftBuilder.build());
                previousPoint = point;
            }
            else if (point.getType().equals(TimelinePointType.END) && previousPoint.getType().equals(TimelinePointType.END)) {
                workShiftBuilder.endDate(point.getInstant());
                workShiftBuilder.powerLimit(point.getOriginPolicy().getMaximumPower());
                workShiftBuilder.startDate(previousPoint.getInstant());
                workShifts.add(workShiftBuilder.build());
                previousPoint = point;
            }
        }
        return new WorkSchedule(
                workShifts.get(0).getStartDate(),
                workShifts.get(workShifts.size() - 1).getEndDate(),
                workShifts
        );
    }

}
