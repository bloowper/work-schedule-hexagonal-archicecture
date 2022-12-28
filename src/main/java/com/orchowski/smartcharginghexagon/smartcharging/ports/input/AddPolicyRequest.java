package com.orchowski.smartcharginghexagon.smartcharging.ports.input;

import com.orchowski.smartcharginghexagon.smartcharging.domain.Priority;
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
    private final Priority priority;
    private final BigDecimal maximumPower;
}
