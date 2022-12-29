package com.orchowski.smartcharginghexagon.smartcharging.domain;

import com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters.DeviceMapperImpl;
import com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters.SmartChargingBeanConfiguration;
import com.orchowski.smartcharginghexagon.smartcharging.ports.input.AddPolicyRequest;
import com.orchowski.smartcharginghexagon.smartcharging.ports.input.CreateDeviceRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static com.orchowski.smartcharginghexagon.smartcharging.domain.Priority.MEDIUM_PRIORITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SmartChargingBehavioralTest {
    private Instant i1 = LocalDateTime.of(2000, 1, 1, 0, 0).toInstant(ZoneOffset.UTC);
    private Instant i2 = LocalDateTime.of(2000, 1, 2, 0, 0).toInstant(ZoneOffset.UTC);
    private Instant i3 = LocalDateTime.of(2000, 1, 3, 0, 0).toInstant(ZoneOffset.UTC);
    private Instant i4 = LocalDateTime.of(2000, 1, 4, 0, 0).toInstant(ZoneOffset.UTC);
    private Instant i5 = LocalDateTime.of(2000, 1, 5, 0, 0).toInstant(ZoneOffset.UTC);
    private Instant i6 = LocalDateTime.of(2000, 1, 6, 0, 0).toInstant(ZoneOffset.UTC);

    @Test
    void shouldThrowExceptionOnAddingPolicyWhenDeviceNotExist() {
        // given
        ScheduleService scheduleService = getScheduleService();
        String deviceId = "deviceId";

        // when
        //TODO [question]This is more when or then in BDD approach???
        assertThrows(RuntimeException.class, () -> scheduleService.generateWorkSchedule(deviceId));

        // then

    }

    @Test
    void shouldCreateValidWorkScheduleFromSinglePolicy() {
        // given
        /*
         * ...<----------->........... policy1 | prio MED   | i2-i2
         * ---1-----------2----------- (instants desc)
         *
         *  */
        ScheduleService scheduleService = getScheduleService();
        String deviceId = "deviceId";
        List<AddPolicyRequest> addPolicyRequests = List.of(
                new AddPolicyRequest(deviceId, i1,i2,MEDIUM_PRIORITY, new BigDecimal("250."))
        );

        WorkSchedule validWorkSchedule = new WorkSchedule(
                i1,
                i2,
                List.of(new WorkShift(i1, i2, new BigDecimal("250.")))
        );

        // when
        scheduleService.createDevice(new CreateDeviceRequest(deviceId));
        addPolicyRequests.forEach(scheduleService::addPolicy);
        WorkSchedule workSchedule = scheduleService.generateWorkSchedule(deviceId);

        // then
        assertEquals(validWorkSchedule, workSchedule);
    }

    @Test
    void shouldCreateValidWorkScheduleFromMultipleNotTouchingPolicies() {


    }

    @Test
    void shouldCreateValidWorkScheduleWith3ShiftsFrom2OverlappingPolicies() {


    }

    @Test
    void shouldCreateValidWorkScheduleWithSingleShiftFrom2OverlappingPolicies() {

    }

    private  ScheduleService getScheduleService() {
        SmartChargingBeanConfiguration smartChargingBeanConfiguration = new SmartChargingBeanConfiguration(new DeviceEntityInMemoryRepository(), new DeviceMapperImpl());
        ScheduleService scheduleService = smartChargingBeanConfiguration.scheduleService();
        return scheduleService;
    }

}
