package com.orchowski.smartcharginghexagon.smartcharging.domain;

import com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters.DeviceMapperImpl;
import com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters.SmartChargingBeanConfiguration;
import org.junit.jupiter.api.Test;


class SmartChargingBehavioralTest {

    @Test
    void shouldCreateValidWorkScheduleFromSinglePolicy() {
        // given
        ScheduleService scheduleService = getScheduleService();
        String deviceId = "deviceId";

        // when
        WorkSchedule workSchedule = scheduleService.generateWorkSchedule(deviceId);

        // then

    }

    @Test
    void shouldCreateValidWorkScheduleFromMultipleNotTouchingPolicies() {
        // given
        ScheduleService scheduleService = getScheduleService();
        String deviceId = "deviceId";

        // when
        WorkSchedule workSchedule = scheduleService.generateWorkSchedule(deviceId);

        // then

    }

    @Test
    void shouldCreateValidWorkScheduleWith3ShiftsFrom2OverlappingPolicies() {
        // given
        ScheduleService scheduleService = getScheduleService();
        String deviceId = "deviceId";

        // when
        WorkSchedule workSchedule = scheduleService.generateWorkSchedule(deviceId);

        // then

    }

    @Test
    void shouldCreateValidWorkScheduleWithSingleShiftFrom2OverlappingPolicies() {
        // given
        ScheduleService scheduleService = getScheduleService();
        String deviceId = "deviceId";

        // when
        WorkSchedule workSchedule = scheduleService.generateWorkSchedule(deviceId);

        // then

    }

    private  ScheduleService getScheduleService() {
        SmartChargingBeanConfiguration smartChargingBeanConfiguration = new SmartChargingBeanConfiguration(null, new DeviceMapperImpl());
        ScheduleService scheduleService = smartChargingBeanConfiguration.scheduleService();
        return scheduleService;
    }

}
