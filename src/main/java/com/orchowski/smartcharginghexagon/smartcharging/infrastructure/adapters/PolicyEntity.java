package com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
class PolicyEntity {
    private String uuid;
    private Instant startDate;
    private Instant endDate;
    private Integer priority;
    private BigDecimal maximumPower;
}
