package com.orchowski.smartcharginghexagon.smartcharging;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static com.orchowski.smartcharginghexagon.smartcharging.Priority.HIGH_PRIORITY;
import static com.orchowski.smartcharginghexagon.smartcharging.Priority.LOW_PRIORITY;
import static org.junit.jupiter.api.Assertions.*;

class PolicyTest {
    private final Instant instant1 = Instant.ofEpochSecond(1000);
    private final Instant instant2 = Instant.ofEpochSecond(2000);
    private final Instant instant3 = Instant.ofEpochSecond(3000);
    private final Instant instant4 = Instant.ofEpochSecond(4000);
    private final Instant instant5 = Instant.ofEpochSecond(5000);
    private final Instant instant6 = Instant.ofEpochSecond(5000);

    @Test
    void policyShouldBeHidedCompletely() {
        // given
        Policy policy1 = new Policy(instant1, instant3, LOW_PRIORITY, BigDecimal.valueOf(200.));
        Policy policy2 = new Policy(instant1, instant3, HIGH_PRIORITY, BigDecimal.valueOf(450.1));

        // when
        boolean policy1isHidedCompletelyByPolicy2 = policy1.isHidedCompletelyBy(policy2);

        // then
        assertTrue(policy1isHidedCompletelyByPolicy2);

    }

    @Test
    void policyShouldNotBeHidedCompletely() {
        // given
        Policy policy1 = new Policy(instant1, instant3, HIGH_PRIORITY, BigDecimal.valueOf(100.));
        Policy policy2 = new Policy(instant1, instant4, LOW_PRIORITY, BigDecimal.valueOf(100.));

        // when
        boolean isHidedCompletely = policy1.isHidedCompletelyBy(policy2);

        // then
        assertFalse(isHidedCompletely);
    }

    @Test
    void policyShouldBeHidedCompletelyByOnePolicyFromList() {
        // given
        /*
        * ....<---------------->..... policy1 | prio HIGH | i1-i4
        * ........<------>........... policy2 | prio LOW  | i2-i3
        * ----1---2------3-----4----- (instant desc)
        *
        *  */
        Policy policy1 = new Policy(instant1, instant4, HIGH_PRIORITY, BigDecimal.valueOf(100.));
        Policy policy2 = new Policy(instant2, instant3, LOW_PRIORITY, BigDecimal.valueOf(100.));

        // when
        boolean hidedCompletelyBy = policy2.isHidedCompletelyBy(List.of(policy1));

        // then
        assertTrue(hidedCompletelyBy);
    }

    @Test
    void policyShouldNotBeHidedCompletelyByOnePolicyFromList() {
        // given
        /*
         * ....<---------------->..... policy1 | prio LOW | i1-i4
         * ........<------>........... policy2 | prio HIGH  | i2-i3
         * ----1---2------3-----4----- (instant desc)
         *
         *  */
        Policy policy1 = new Policy(instant1, instant4, LOW_PRIORITY, BigDecimal.valueOf(100.));
        Policy policy2 = new Policy(instant2, instant3, HIGH_PRIORITY, BigDecimal.valueOf(100.));

        // when
        boolean hidedCompletelyBy = policy2.isHidedCompletelyBy(List.of(policy1));

        // then
        assertFalse(hidedCompletelyBy);
    }

    @Test
    void policyShouldBeHidedCompletelyByMultiplePolitics() {
        // given
        /*
         * ...<----------->........... policy1 | prio HIGH   | i1-i4
         * ...........<---------->.... policy2 | prio HIGH   | i3-i6
         * .......<---------->........ policy3 | prio MEDIUM | i2-i5
         * ---1---2---3---4--5---6---- (instants desc)
         *
         * p3 is hided by sum of p2 and p1
         *  */
        Policy policy1 = new Policy(instant1, instant4, HIGH_PRIORITY, BigDecimal.valueOf(100.));
        Policy policy2 = new Policy(instant3, instant6, HIGH_PRIORITY, BigDecimal.valueOf(100.));
        Policy policy3 = new Policy(instant2, instant5, LOW_PRIORITY, BigDecimal.valueOf(100.));


        // when
        boolean hidedCompletelyBy = policy3.isHidedCompletelyBy(List.of(policy2, policy1));

        // then
        assertTrue(hidedCompletelyBy);
    }

}
