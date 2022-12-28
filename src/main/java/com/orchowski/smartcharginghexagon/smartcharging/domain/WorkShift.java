package com.orchowski.smartcharginghexagon.smartcharging.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
public class WorkShift {
    private final Instant shiftStartDate;
    private final Instant shiftEndDate;
    private final BigDecimal powerLimit;
}
