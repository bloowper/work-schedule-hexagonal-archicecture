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

import static com.orchowski.smartcharginghexagon.smartcharging.domain.Priority.HIGH_PRIORITY;
import static com.orchowski.smartcharginghexagon.smartcharging.domain.Priority.LOW_PRIORITY;
import static com.orchowski.smartcharginghexagon.smartcharging.domain.Priority.MEDIUM_PRIORITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SmartChargingBehavioralTest {
    private static final Instant I_1 = LocalDateTime.of(2000, 1, 1, 0, 0).toInstant(ZoneOffset.UTC);
    private static final Instant I_2 = LocalDateTime.of(2000, 1, 2, 0, 0).toInstant(ZoneOffset.UTC);
    private static final Instant I_3 = LocalDateTime.of(2000, 1, 3, 0, 0).toInstant(ZoneOffset.UTC);
    private static final Instant I_4 = LocalDateTime.of(2000, 1, 4, 0, 0).toInstant(ZoneOffset.UTC);
    private static final Instant I_5 = LocalDateTime.of(2000, 1, 5, 0, 0).toInstant(ZoneOffset.UTC);
    private static final Instant I_6 = LocalDateTime.of(2000, 1, 6, 0, 0).toInstant(ZoneOffset.UTC);
    private static final String DEVICE_ID = "deviceId";
    private static final BigDecimal VALUE250 = new BigDecimal("250.");
    private static final BigDecimal VALUE350 = new BigDecimal("350.");
    private static final BigDecimal VALUE450 = new BigDecimal("550.");

    @Test
    void shouldCreateValidWorkScheduleFromSinglePolicy() {
        // given
        /*
         * ...<----------->........... policy1 | prio MED   | i2-i2
         * ---1-----------2----------- (instants desc)
         *
         *  */
        WorkScheduleService workScheduleService = getScheduleService();
        AddPolicyRequest addPolicyRequest = new AddPolicyRequest(DEVICE_ID, I_1, I_2, MEDIUM_PRIORITY, VALUE250);

        WorkSchedule validWorkSchedule = new WorkSchedule(
                I_1,
                I_2,
                List.of(new WorkShift(I_1, I_2, VALUE250))
        );

        // when
        workScheduleService.createDevice(new CreateDeviceRequest(DEVICE_ID));
        workScheduleService.addPolicy(addPolicyRequest);
        WorkSchedule workSchedule = workScheduleService.generateWorkSchedule(DEVICE_ID);

        // then
        assertEquals(validWorkSchedule, workSchedule);
    }

    @Test
    void shouldCreateValidWorkScheduleFromTwoNotOverlyingPolicy() {
        // given
        /*
         * ..<---->................... policy1 | prio MED   | i2-i2
         * ............<----->........ policy2 | prio MED   | i3-i4
         * --1----2----3-----4-------- (instants desc)
         *
         *  */
        WorkScheduleService workScheduleService = getScheduleService();
        AddPolicyRequest policy1 = new AddPolicyRequest(DEVICE_ID, I_1, I_2, MEDIUM_PRIORITY, VALUE250);
        AddPolicyRequest policy2 = new AddPolicyRequest(DEVICE_ID, I_3, I_4, MEDIUM_PRIORITY, VALUE450);


        // when
        workScheduleService.createDevice(new CreateDeviceRequest(DEVICE_ID));
        workScheduleService.addPolicy(policy1);
        workScheduleService.addPolicy(policy2);
        WorkSchedule workSchedule = workScheduleService.generateWorkSchedule(DEVICE_ID);

        // then
        WorkSchedule validWorkSchedule = new WorkSchedule(
                I_1,
                I_4,
                List.of(
                        new WorkShift(I_1, I_2, VALUE250),
                        new WorkShift(I_3, I_4, VALUE450)
                )
        );
        assertEquals(validWorkSchedule, workSchedule);
    }

    @Test
    void shouldCreateValidWorkScheduleFromTouchingPolicies() {
        // given
        /*
         * .<----------->............. policy1 | prio MED   | i2-i2
         * .............<--------->... policy2 | prio MED   | i3-i4
         * -1-----------2---------3--- (instants desc)
         *
         * -|<----p1--->|<--p2--->|---
         *
         *  */

        WorkScheduleService workScheduleService = getScheduleService();
        AddPolicyRequest policy1 = new AddPolicyRequest(DEVICE_ID, I_1, I_2, MEDIUM_PRIORITY, VALUE250);
        AddPolicyRequest policy2 = new AddPolicyRequest(DEVICE_ID, I_2, I_3, MEDIUM_PRIORITY, VALUE350);

        // when
        workScheduleService.createDevice(new CreateDeviceRequest(DEVICE_ID));
        workScheduleService.addPolicy(policy1);
        workScheduleService.addPolicy(policy2);
        WorkSchedule workSchedule = workScheduleService.generateWorkSchedule(DEVICE_ID);

        // then
        WorkSchedule validWorkSchedule = new WorkSchedule(
                I_1,
                I_3,
                List.of(
                        new WorkShift(I_1, I_2, VALUE250),
                        new WorkShift(I_2, I_3, VALUE350)
                )
        );
        assertEquals(validWorkSchedule, workSchedule);
    }

    @Test
    void shouldCreateValidWorkScheduleWith3siftsFromTwoOverlyingPolicies() {
        // given
        /*
         * ..<------------------->.... policy1 | prio MED    | i2-i2
         * .......<--------->......... policy2 | prio HIGH   | i3-i4
         * --1----2---------3----4---- (instants desc)
         *
         * --|<p1>|<---p2-->|<p1>|---   output sum of policies(time)
         *
         *  */

        WorkScheduleService workScheduleService = getScheduleService();
        AddPolicyRequest policy1 = new AddPolicyRequest(DEVICE_ID, I_1, I_4, MEDIUM_PRIORITY, VALUE250);
        AddPolicyRequest policy2 = new AddPolicyRequest(DEVICE_ID, I_2, I_3, HIGH_PRIORITY, VALUE450);

        // when
        workScheduleService.createDevice(new CreateDeviceRequest(DEVICE_ID));
        workScheduleService.addPolicy(policy1);
        workScheduleService.addPolicy(policy2);
        WorkSchedule workSchedule = workScheduleService.generateWorkSchedule(DEVICE_ID);

        // then
        WorkSchedule validWorkSchedule = new WorkSchedule(
                I_1,
                I_4,
                List.of(
                        new WorkShift(I_1, I_2, VALUE250),
                        new WorkShift(I_2, I_3, VALUE450),
                        new WorkShift(I_3, I_4, VALUE250)
                )
        );
        assertEquals(validWorkSchedule, workSchedule);
    }

    @Test
    void lowerPriorityPolicyShouldBeInvisibleInWorkSchedule() {
        // given
        /*
         * ..<------------------->.... policy1 | prio MED    | i2-i2
         * .......<--------->......... policy2 | prio HIGH   | i3-i4
         * --1----2---------3----4---- (instants desc)
         *
         * --|<---------p1------>|---   output sum of policies(time)
         *
         *  */

        WorkScheduleService workScheduleService = getScheduleService();
        AddPolicyRequest policy1 = new AddPolicyRequest(DEVICE_ID, I_1, I_4, HIGH_PRIORITY, VALUE250);
        AddPolicyRequest policy2 = new AddPolicyRequest(DEVICE_ID, I_2, I_3, LOW_PRIORITY, VALUE450);


        // when
        workScheduleService.createDevice(new CreateDeviceRequest(DEVICE_ID));
        workScheduleService.addPolicy(policy1);
        workScheduleService.addPolicy(policy2);
        WorkSchedule workSchedule = workScheduleService.generateWorkSchedule(DEVICE_ID);

        // then
        WorkSchedule validWorkSchedule = new WorkSchedule(
                I_1,
                I_4,
                List.of(
                        new WorkShift(I_1, I_4, VALUE250)
                )
        );
        assertEquals(validWorkSchedule, workSchedule);
    }


    @Test
    void shouldThrowExceptionOnAddingPolicyWhenDeviceNotExist() {
        // given
        WorkScheduleService workScheduleService = getScheduleService();

        // when

        // then
        //TODO [question]This is more when or then in BDD approach???
        assertThrows(RuntimeException.class, () -> workScheduleService.generateWorkSchedule(DEVICE_ID));//TODO provide domain exception
    }

    @Test
    void shouldThrowExceptionOnSamePriorityPolicyConflict() {
        // given
        WorkScheduleService workScheduleService = getScheduleService();
        AddPolicyRequest firstPolicy = new AddPolicyRequest(DEVICE_ID, I_1, I_2, MEDIUM_PRIORITY, VALUE250);
        AddPolicyRequest secondPolicy = new AddPolicyRequest(DEVICE_ID, I_1, I_3, MEDIUM_PRIORITY, VALUE350);

        workScheduleService.createDevice(new CreateDeviceRequest(DEVICE_ID));
        workScheduleService.addPolicy(firstPolicy);

        // when
        // then
        assertThrows(RuntimeException.class, () -> workScheduleService.addPolicy(secondPolicy));

    }
    private WorkScheduleService getScheduleService() {
        SmartChargingBeanConfiguration smartChargingBeanConfiguration = new SmartChargingBeanConfiguration(new DeviceEntityInMemoryRepository(), new DeviceMapperImpl());
        WorkScheduleService workScheduleService = smartChargingBeanConfiguration.scheduleService();
        return workScheduleService;
    }

}
