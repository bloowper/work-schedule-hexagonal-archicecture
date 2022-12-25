package com.orchowski.smartcharginghexagon.smartcharging;

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

        Point shiftStartPoint = null;
        Point shiftEndPoint = null;
        WorkShift.WorkShiftBuilder workShiftBuilder = WorkShift.builder();
        for (Point point : points) {
            if (shiftStartPoint == null) {
                shiftStartPoint = point;
                workShiftBuilder.shiftStartDate(point.getInstant());
                workShiftBuilder.powerLimit(point.getOriginPolicy().getMaximumPower());
                continue;
            }
            if (point.getType().equals(Type.END) && (point.getOriginPolicy() == shiftStartPoint.getOriginPolicy())) {
                workShiftBuilder.shiftEndDate(point.getInstant());
                workShifts.add(workShiftBuilder.build());
                shiftStartPoint = null;
                shiftEndPoint = null;
                continue;
            }

            // Current Policy is interluded by other policy with higher
            if (point.getType().equals(Type.START) && shiftEndPoint != null && point.getPriority() > shiftEndPoint.getPriority()) {
                workShiftBuilder.shiftEndDate(point.getInstant());
                workShifts.add(workShiftBuilder.build());
                workShiftBuilder.shiftStartDate(point.getInstant());
                workShiftBuilder.powerLimit(point.originPolicy.getMaximumPower());
            }
            if (point.getType().equals(Type.END) && point.getPriority() > shiftEndPoint.getPriority()) {
                workShiftBuilder.shiftEndDate(point.getInstant());
                workShifts.add(workShiftBuilder.build());
                workShiftBuilder.shiftStartDate(point.getInstant());
                workShiftBuilder.powerLimit(point.originPolicy.getMaximumPower());
            }

            shiftEndPoint = shiftStartPoint;
            shiftStartPoint = null;
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
