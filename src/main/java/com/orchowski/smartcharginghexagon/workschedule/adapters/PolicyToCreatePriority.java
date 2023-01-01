package com.orchowski.smartcharginghexagon.workschedule.adapters;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
enum PolicyToCreatePriority {
    VERY_LOW(1000),
    LOW(2000),
    MEDIUM(3000),
    HIGH(4000),
    VERY_HIGH(5000);

    private final Integer priority;
}
