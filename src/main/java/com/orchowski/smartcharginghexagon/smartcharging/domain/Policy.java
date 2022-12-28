package com.orchowski.smartcharginghexagon.smartcharging.domain;

import com.orchowski.smartcharginghexagon.commons.NullCheck;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
public class Policy {
    private String uuid;
    private Instant startDate;
    private Instant endDate;
    private Integer priority;
    private BigDecimal maximumPower;

    Policy(Instant startDate, Instant endDate, Priority priority, BigDecimal maximumPower) {
        validatePolicy(startDate, endDate, maximumPower);
        this.uuid = UUID.randomUUID().toString();
        this.startDate = startDate;
        this.endDate = endDate;
        this.priority = priority.getPriority();
        this.maximumPower = maximumPower;
    }


    boolean isHidedCompletelyBy(Policy otherPolicy) {//TODO do i need this?
        if (this == otherPolicy) {
            return false;
        }
        boolean isOtherStarsBefore = otherPolicy.getStartDate().isBefore(getStartDate()) || otherPolicy.getStartDate().equals(getStartDate());
        boolean isOtherEndsAfter = otherPolicy.getEndDate().isAfter(getEndDate()) || otherPolicy.getEndDate().equals(getEndDate());
        boolean isOtherHaveGreaterPriority = otherPolicy.getPriority().compareTo(getPriority()) >0;
        return isOtherStarsBefore && isOtherEndsAfter && isOtherHaveGreaterPriority;
    }

    boolean isHidedCompletelyBy(List<Policy> otherPolicies) {//TODO do i need this?
        for (Policy otherPolicy : otherPolicies) {
            if (isHidedCompletelyBy(otherPolicy)) {
                return true;
            }
        }
        return false;
    }

    private void validatePolicy(Instant startDate, Instant endDate, BigDecimal maximumPower) {
        NullCheck.throwExceptionWhenAnyNull(() -> new IllegalArgumentException("startDate, endDate, maximumPower: cannot be null"), startDate, endDate, maximumPower);
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("endDate have to be after startDate");
        }
        if (BigDecimal.ZERO.compareTo(maximumPower)>0) {
            throw new IllegalArgumentException("maximumPower have to be greater or equal to 0");
        }
    }
}
