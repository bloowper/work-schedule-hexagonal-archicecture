package com.orchowski.smartcharginghexagon.smartcharging.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
public class WorkSchedule {
    private Instant startTimeOfPlan;
    private Instant endTimeOfPlan;
    private final List<WorkShift> workShifts;

}
