package com.orchowski.smartcharginghexagon.smartcharging.ports.input;

import com.orchowski.smartcharginghexagon.smartcharging.domain.WorkSchedule;

public interface GenerateDeviceWorkScheduleUseCase {
    WorkSchedule generateWorkSchedule(String deviceId);
}
