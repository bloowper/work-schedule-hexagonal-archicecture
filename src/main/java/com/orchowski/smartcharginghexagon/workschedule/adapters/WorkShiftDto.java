package com.orchowski.smartcharginghexagon.workschedule.adapters;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
class WorkShiftDto {
    private Instant startDate;
    private Instant endDate;
    private BigDecimal powerLimit;
}
