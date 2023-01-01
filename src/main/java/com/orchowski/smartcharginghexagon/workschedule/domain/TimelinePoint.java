package com.orchowski.smartcharginghexagon.workschedule.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
final class TimelinePoint {
    private final Instant instant;
    private final TimelinePointType type;
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
