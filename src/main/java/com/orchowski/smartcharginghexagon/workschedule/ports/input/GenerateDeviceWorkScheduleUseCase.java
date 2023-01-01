package com.orchowski.smartcharginghexagon.workschedule.ports.input;

import com.orchowski.smartcharginghexagon.workschedule.domain.WorkSchedule;

public interface GenerateDeviceWorkScheduleUseCase {
    WorkSchedule generateWorkSchedule(String deviceId);
}
