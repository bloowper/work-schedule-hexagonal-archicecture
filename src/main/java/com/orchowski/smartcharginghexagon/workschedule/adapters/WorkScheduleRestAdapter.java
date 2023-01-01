package com.orchowski.smartcharginghexagon.workschedule.adapters;

import com.orchowski.smartcharginghexagon.workschedule.domain.Policy;
import com.orchowski.smartcharginghexagon.workschedule.domain.WorkSchedule;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.AddPolicyRequest;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.AddPolicyUseCase;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.CreateDeviceRequest;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.CreateDeviceUseCase;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.GenerateDeviceWorkScheduleUseCase;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.GetDevicePoliciesUseCase;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.GetDeviceUseCase;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
class WorkScheduleRestAdapter {
    // TODO m8by adding AdapterService? [question]
    // TODO provide better response error codes( currently always 500 is returned)
    private final CreateDeviceUseCase createDeviceUseCase;
    private final AddPolicyUseCase addPolicyUseCase;
    private final GenerateDeviceWorkScheduleUseCase generateDeviceWorkScheduleUseCase;
    private final GetDeviceUseCase getDeviceUseCase;
    private final GetDevicePoliciesUseCase getDevicePoliciesUseCase;
    private final DomainMapper domainMapper;

    @PutMapping("/device/{id}")
    void createDevice(@NotBlank @PathVariable String id) {
        createDeviceUseCase.createDevice(new CreateDeviceRequest(id));
    }

    @GetMapping("/device")
    List<DeviceDto> getDevices() {
        //TODO provide pagination for sake to not returning entire db :)
        return getDeviceUseCase.getDevices().stream()
                .map(domainMapper::toDto)
                .toList();
    }

    @PostMapping("/device/{deviceId}/policy")
    PolicyDto createPolicy(@NotBlank @PathVariable String deviceId, @RequestBody PolicyToCreateDto policyToCreateDto) {
        AddPolicyRequest addPolicyRequest = domainMapper.toRequest(policyToCreateDto, deviceId);
        Policy policy = addPolicyUseCase.addPolicy(addPolicyRequest);
        return domainMapper.toDto(policy);
    }

    @GetMapping("/device/{deviceId}/policy")
    List<PolicyDto> getPolicies(@NotBlank @PathVariable String deviceId) {
        return getDevicePoliciesUseCase.getPoliciesForDevice(deviceId).stream()
                .map(domainMapper::toDto)
                .toList();
    }

    @GetMapping("/device/{deviceId}/work-schedule")
    WorkScheduleDto getWorkSchedule(@NotBlank @PathVariable String deviceId) {
        WorkSchedule workSchedule = generateDeviceWorkScheduleUseCase.generateWorkSchedule(deviceId);
        return domainMapper.toDto(workSchedule);
    }


}
