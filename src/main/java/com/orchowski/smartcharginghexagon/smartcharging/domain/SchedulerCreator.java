package com.orchowski.smartcharginghexagon.smartcharging.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


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

        System.out.println("POINTS");
        points.stream().forEach(System.out::println);

        List<WorkShift> workShifts = new ArrayList<>(points.size() - 1);

        Point previousPoint = null;
        WorkShift.WorkShiftBuilder workShiftBuilder = WorkShift.builder();
        for (Point point : points) {
            if (previousPoint == null || point.getType().equals(Type.START) && previousPoint.getType().equals(Type.END)) {
                // Start new Policy without interrupting other policy
                workShiftBuilder.shiftStartDate(point.getInstant());
                workShiftBuilder.powerLimit(point.getOriginPolicy().getMaximumPower());
                previousPoint = point;
                continue;
            }
            if (point.getType().equals(Type.START)  && point.getPriority() > previousPoint.getPriority()) {
                // Previous Policy is interrupted by other policy with higher priority
                workShiftBuilder.shiftEndDate(point.getInstant());
                workShifts.add(workShiftBuilder.build());
                workShiftBuilder.shiftStartDate(point.getInstant());
                workShiftBuilder.powerLimit(point.originPolicy.getMaximumPower());
                previousPoint = point;
                continue;
            }
            if (point.getType().equals(Type.END) && point.getPriority() > previousPoint.getPriority()) {
                workShiftBuilder.shiftEndDate(point.getInstant());
                workShifts.add(workShiftBuilder.build());
                workShiftBuilder.shiftStartDate(point.getInstant());
                workShiftBuilder.powerLimit(point.originPolicy.getMaximumPower());
                previousPoint = point;
                continue;
            }
            if (point.getType().equals(Type.END) && (point.getOriginPolicy() == previousPoint.getOriginPolicy())) {
                // normal end of shift, without interrupt
                workShiftBuilder.shiftEndDate(point.getInstant());
                workShifts.add(workShiftBuilder.build());
                previousPoint = point;
                continue;
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
