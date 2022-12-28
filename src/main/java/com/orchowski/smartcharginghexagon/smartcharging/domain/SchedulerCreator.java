package com.orchowski.smartcharginghexagon.smartcharging.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
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
        List<Point> points = policies.stream()
                .flatMap(policy ->
                        List.of(
                                new Point(policy.getStartDate(), Type.START, policy.getPriority(), policy),
                                new Point(policy.getEndDate(), Type.END, policy.getPriority(), policy)
                        ).stream()
                )
                .sorted(Comparator.comparing(Point::getInstant))
                .toList();
        log.debug("Points from which schedule will be built [{}]", points);
        List<WorkShift> workShifts = new ArrayList<>(points.size() - 1);
        Point previousPoint = null;
        WorkShift.WorkShiftBuilder workShiftBuilder = WorkShift.builder();
        for (Point point : points) {
            if (previousPoint == null || point.getType().equals(Type.START) && previousPoint.getType().equals(Type.END)) {
                workShiftBuilder.shiftStartDate(point.getInstant());
                workShiftBuilder.powerLimit(point.getOriginPolicy().getMaximumPower());
                previousPoint = point;
            }
            else if (point.getType().equals(Type.START)  && point.getPriority() > previousPoint.getPriority()) {
                workShiftBuilder.shiftEndDate(point.getInstant());
                workShifts.add(workShiftBuilder.build());
                workShiftBuilder.shiftStartDate(point.getInstant());
                workShiftBuilder.powerLimit(point.originPolicy.getMaximumPower());
                previousPoint = point;
            } else if (point.getType().equals(Type.END) && point.getOriginPolicy() == previousPoint.getOriginPolicy()) {
                workShiftBuilder.shiftEndDate(point.getInstant());
                workShifts.add(workShiftBuilder.build());
                previousPoint = point;
            }
            else if (point.getType().equals(Type.END) && previousPoint.getType().equals(Type.END)) {
                workShiftBuilder.shiftEndDate(point.getInstant());
                workShiftBuilder.powerLimit(point.originPolicy.getMaximumPower());
                workShiftBuilder.shiftStartDate(previousPoint.getInstant());
                workShifts.add(workShiftBuilder.build());
                previousPoint = point;
            }
        }
        return new WorkSchedule(
                workShifts.get(0).getShiftStartDate(),
                workShifts.get(workShifts.size() - 1).getShiftEndDate(),
                workShifts
        );
    }

    @RequiredArgsConstructor
    @Getter
    private static final class Point {
        private final Instant instant;
        private final Type type;
        private final Integer priority;
        @ToString.Exclude
        private final Policy originPolicy;

        @Override
        public String toString() {
            return "Point{" +
                    "instant=" + instant +
                    ", type=" + type +
                    ", priority=" + priority +
                    ", maxPower=" + originPolicy.getMaximumPower() +
                    '}';
        }
    }

    private enum Type {
        START,
        END
    }
}
