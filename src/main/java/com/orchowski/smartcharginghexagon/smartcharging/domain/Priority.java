package com.orchowski.smartcharginghexagon.smartcharging.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Priority {
    VERY_LOW_PRIORITY(1000),
    LOW_PRIORITY(2000),
    MEDIUM_PRIORITY(3000),
    HIGH_PRIORITY(4000),
    VERY_HIGH_PRIORITY(5000);
    private final int priority;
}
