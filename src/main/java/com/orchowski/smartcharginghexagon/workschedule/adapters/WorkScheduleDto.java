package com.orchowski.smartcharginghexagon.workschedule.adapters;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
class WorkScheduleDto {
    private Instant startDate;
    private Instant endDate;

    private List<WorkShiftDto> workShifts;
}
