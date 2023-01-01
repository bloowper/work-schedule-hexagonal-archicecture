package com.orchowski.smartcharginghexagon.workschedule.adapters;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
class PolicyToCreateDto {
    private Instant startDate;
    private Instant endDate;
    private PolicyToCreatePriority priority;
    private BigDecimal maximumPower;
}
