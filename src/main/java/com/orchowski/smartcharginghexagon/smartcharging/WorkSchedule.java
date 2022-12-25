package com.orchowski.smartcharginghexagon.smartcharging;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
class WorkSchedule {
    private Instant startTimeOfPlan;
    private Instant endTimeOfPlan;
    private final List<WorkShift> workShifts;

}
