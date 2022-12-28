package com.orchowski.smartcharginghexagon.smartcharging.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder(access = AccessLevel.PACKAGE)
public class WorkShift {
    private final Instant shiftStartDate;
    private final Instant shiftEndDate;
    private final BigDecimal powerLimit;
}
