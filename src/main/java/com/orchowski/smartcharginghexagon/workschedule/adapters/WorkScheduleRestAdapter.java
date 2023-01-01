package com.orchowski.smartcharginghexagon.workschedule.adapters;

import com.orchowski.smartcharginghexagon.workschedule.domain.Policy;
import com.orchowski.smartcharginghexagon.workschedule.domain.WorkSchedule;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.AddPolicyRequest;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.AddPolicyUseCase;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.CreateDeviceRequest;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.CreateDeviceUseCase;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.GenerateDeviceWorkScheduleUseCase;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
class WorkScheduleRestAdapter {
    // TODO m8by adding AdapterService? [question]
    private final CreateDeviceUseCase createDeviceUseCase;
    private final AddPolicyUseCase addPolicyUseCase;
    private final GenerateDeviceWorkScheduleUseCase generateDeviceWorkScheduleUseCase;
    private final DomainMapper domainMapper;

    @PutMapping("/device/{id}")
    void createDevice(@NotNull @PathVariable String id) {
        createDeviceUseCase.createDevice(new CreateDeviceRequest(id));
    }

    @PostMapping("/device/{deviceId}/policy")
    PolicyDto createPolicy(@PathVariable String deviceId, @RequestBody PolicyToCreateDto policyToCreateDto) {
        AddPolicyRequest addPolicyRequest = domainMapper.toRequest(policyToCreateDto, deviceId);
        Policy policy = addPolicyUseCase.addPolicy(addPolicyRequest);
        return domainMapper.toDto(policy);
    }

    @GetMapping("/device/{deviceId}/work-schedule")
    WorkScheduleDto getWorkSchedule(@PathVariable String deviceId) {
        WorkSchedule workSchedule = generateDeviceWorkScheduleUseCase.generateWorkSchedule(deviceId);
        return domainMapper.toDto(workSchedule);
    }


}
