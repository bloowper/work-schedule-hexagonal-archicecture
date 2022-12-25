package com.orchowski.smartcharginghexagon.smartcharging;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
enum Priority {
    VERY_LOW_PRIORITY(1000),
    LOW_PRIORITY(2000),
    MEDIUM_PRIORITY(3000),
    HIGH_PRIORITY(4000),
    VERY_HIGH_PRIORITY(5000);
    private final int priority;
}
