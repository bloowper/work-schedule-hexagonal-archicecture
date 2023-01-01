package com.orchowski.smartcharginghexagon.workschedule.ports.input;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@Getter
public class AddPolicyRequest {
    private final String deviceId;
    private final Instant startDate;
    private final Instant endDate;
    private final Integer priority;
    private final BigDecimal maximumPower;
}
