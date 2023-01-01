package com.orchowski.smartcharginghexagon.workschedule.adapters;

import com.orchowski.smartcharginghexagon.workschedule.domain.Policy;
import com.orchowski.smartcharginghexagon.workschedule.domain.WorkSchedule;
import com.orchowski.smartcharginghexagon.workschedule.domain.WorkShift;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.AddPolicyRequest;
import org.mapstruct.Mapper;

@Mapper
interface DomainMapper {//TODO Write tests
    AddPolicyRequest toRequest( PolicyToCreateDto policyToCreateDto, String deviceId);

    PolicyDto toDto(Policy policy);

    WorkScheduleDto toDto(WorkSchedule workSchedule);

    WorkShiftDto toDto(WorkShift workShift);

    default Integer policyToCreatePriorityToValue(PolicyToCreatePriority policyToCreatePriority){
        return policyToCreatePriority.getPriority();
    }
}
