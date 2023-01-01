package com.orchowski.smartcharginghexagon.workschedule.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class PolicyDto {
    private String uuid;
    private Instant startDate;
    private Instant endDate;
    private Integer priority;
    private BigDecimal maximumPower;
}
