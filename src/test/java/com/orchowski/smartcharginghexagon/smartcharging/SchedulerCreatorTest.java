package com.orchowski.smartcharginghexagon.smartcharging;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static com.orchowski.smartcharginghexagon.smartcharging.Priority.HIGH_PRIORITY;
import static com.orchowski.smartcharginghexagon.smartcharging.Priority.MEDIUM_PRIORITY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SchedulerCreatorTest {
    private Instant i1 = LocalDateTime.of(2000, 1, 1, 0, 0).toInstant(ZoneOffset.UTC);
    private Instant i2 = LocalDateTime.of(2000, 1, 2, 0, 0).toInstant(ZoneOffset.UTC);
    private Instant i3 = LocalDateTime.of(2000, 1, 3, 0, 0).toInstant(ZoneOffset.UTC);
    private Instant i4 = LocalDateTime.of(2000, 1, 4, 0, 0).toInstant(ZoneOffset.UTC);
    private Instant i5 = LocalDateTime.of(2000, 1, 5, 0, 0).toInstant(ZoneOffset.UTC);
    private Instant i6 = LocalDateTime.of(2000, 1, 6, 0, 0).toInstant(ZoneOffset.UTC);

    @Test
    void shouldCrateValidWorkScheduleFromSinglePolicy() {
        // given
        /*
         * ...<----------->........... policy1 | prio MED   | i2-i2
         * ---1-----------2----------- (instants desc)
         *
         *  */
        List<Policy> policies = List.of(
                new Policy(
                        i1,
                        i2,
                        MEDIUM_PRIORITY,
                        new BigDecimal("250.")
                )
        );
        WorkSchedule validWorkSchedule = new WorkSchedule(
                i1,
                i2,
                List.of(new WorkShift(i1, i2, new BigDecimal("250.")))
        );

        // when
        SchedulerCreator schedulerCreator = new SchedulerCreator(policies);
        WorkSchedule workSchedule = schedulerCreator.createWorkSchedule();

        // then
        System.out.println(workSchedule);
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
        List<Policy> policies = List.of(
                new Policy(
                        i1,
                        i2,
                        MEDIUM_PRIORITY,
                        new BigDecimal("250.")
                ),
                new Policy(
                        i3,
                        i4,
                        MEDIUM_PRIORITY,
                        new BigDecimal("350.")
                )
        );
        WorkSchedule validWorkSchedule = new WorkSchedule(
                i1,
                i4,
                List.of(
                        new WorkShift(i1, i2, new BigDecimal("250.")),
                        new WorkShift(i3, i4, new BigDecimal("350."))
                )
        );

        // when
        SchedulerCreator schedulerCreator = new SchedulerCreator(policies);
        WorkSchedule workSchedule = schedulerCreator.createWorkSchedule();

        // then
        assertEquals(validWorkSchedule.getStartTimeOfPlan(), workSchedule.getStartTimeOfPlan());
        assertEquals(validWorkSchedule.getEndTimeOfPlan(), workSchedule.getEndTimeOfPlan());
        assertEquals(validWorkSchedule.getWorkShifts(), workSchedule.getWorkShifts());
    }

    @Test
    void shouldCreateValidWorkScheduleWith3siftsFromTwoOverlyingPolicies() {
        // given
        /*
         * ..<------------------->.... policy1 | prio MED    | i2-i2
         * .......<--------->......... policy2 | prio HIGH   | i3-i4
         * --1----2---------3----4---- (instants desc)
         *
         *  */
        List<Policy> policies = List.of(
                new Policy(
                        i1,
                        i4,
                        MEDIUM_PRIORITY,
                        new BigDecimal("250.")
                ),
                new Policy(
                        i2,
                        i3,
                        HIGH_PRIORITY,
                        new BigDecimal("350.")
                )
        );
        WorkSchedule validWorkSchedule = new WorkSchedule(
                i1,
                i4,
                List.of(
                        new WorkShift(i1, i2, new BigDecimal("250.")),
                        new WorkShift(i2, i3, new BigDecimal("350.")),
                        new WorkShift(i3, i4, new BigDecimal("250."))
                )
        );

        System.out.println("Valid work shifts");
        validWorkSchedule.getWorkShifts().stream().forEach(System.out::println);
        System.out.println("----\n\n");

        // when
        SchedulerCreator schedulerCreator = new SchedulerCreator(policies);
        WorkSchedule workSchedule = schedulerCreator.createWorkSchedule();

        System.out.println("Work shifts");
        workSchedule.getWorkShifts().stream().forEach(System.out::println);
        System.out.println("----\n\n");

        // then
        assertEquals(validWorkSchedule.getStartTimeOfPlan(), workSchedule.getStartTimeOfPlan());
        assertEquals(validWorkSchedule.getEndTimeOfPlan(), workSchedule.getEndTimeOfPlan());
        assertEquals(validWorkSchedule.getWorkShifts(), workSchedule.getWorkShifts());
    }

    // @Test
    // void shouldCreateValidWorkSchedule() {
    //     // given
    //     Instant i1 = Instant.ofEpochSecond(1000);
    //     Instant i2 = Instant.ofEpochSecond(2000);
    //     Instant i3 = Instant.ofEpochSecond(3000);
    //     Instant i4 = Instant.ofEpochSecond(4000);
    //     Instant i5 = Instant.ofEpochSecond(5000);
    //     Instant i6 = Instant.ofEpochSecond(6000);
    //
    //     BigDecimal p1 = new BigDecimal("200");
    //     BigDecimal p2 = new BigDecimal("300");
    //     BigDecimal p3 = new BigDecimal("400");
    //
    //     List<Policy> policies = List.of(
    //             new Policy(i1, i3, MEDIUM_PRIORITY, p1),
    //             new Policy(i2, i5, HIGH_PRIORITY, p2),
    //             new Policy(i4, i6, MEDIUM_PRIORITY, p3)
    //     );
    //
    //     WorkSchedule walidWorkSchedule = new WorkSchedule(
    //             i1,
    //             i6,
    //             List.of(
    //                     new WorkShift(i1, i2, p1),
    //                     new WorkShift(i2, i5, p2),
    //                     new WorkShift(i5, i6, p3)
    //             )
    //     );
    //
    //     System.out.println("VALID WORK SHIFT");
    //     walidWorkSchedule.getWorkShifts().stream().forEach(System.out::println);
    //     System.out.println("\n\n");
    //
    //     // when
    //     SchedulerCreator schedulerCreator = new SchedulerCreator(policies);
    //     WorkSchedule workSchedule = schedulerCreator.createWorkSchedule();
    //
    //     // then
    //     System.out.println(workSchedule);
    //     assertEquals(walidWorkSchedule, workSchedule);
    // }
}
