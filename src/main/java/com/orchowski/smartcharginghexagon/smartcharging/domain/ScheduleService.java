package com.orchowski.smartcharginghexagon.smartcharging.domain;

import com.orchowski.smartcharginghexagon.smartcharging.ports.input.AddPolicyRequest;
import com.orchowski.smartcharginghexagon.smartcharging.ports.input.AddPolicyUseCase;
import com.orchowski.smartcharginghexagon.smartcharging.ports.input.CheckPolicyVisibilityStatusUseCase;
import com.orchowski.smartcharginghexagon.smartcharging.ports.input.CreateDeviceRequest;
import com.orchowski.smartcharginghexagon.smartcharging.ports.input.CreateDeviceUseCase;
import com.orchowski.smartcharginghexagon.smartcharging.ports.input.GenerateDeviceWorkScheduleUseCase;
import com.orchowski.smartcharginghexagon.smartcharging.ports.output.DevicePersistenceOutputPort;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
class ScheduleService implements AddPolicyUseCase, GenerateDeviceWorkScheduleUseCase, CreateDeviceUseCase, CheckPolicyVisibilityStatusUseCase {
    private final DevicePersistenceOutputPort devicePersistenceOutputPort;
    @Override
    public void createDevice(CreateDeviceRequest createDeviceRequest) {
        devicePersistenceOutputPort.save(new Device(createDeviceRequest.getDeviceId()));
    }


    @Override
    public Policy addPolicy(AddPolicyRequest addPolicyRequest) {
        Policy policy = new Policy(
                addPolicyRequest.getStartDate(),
                addPolicyRequest.getEndDate(),
                addPolicyRequest.getPriority(),
                addPolicyRequest.getMaximumPower()
        );
        Device device = devicePersistenceOutputPort.getDeviceById(addPolicyRequest.getDeviceId())
                .orElseThrow(() -> new RuntimeException("Device not exist"));
        device.addNewPolicy(policy);
        devicePersistenceOutputPort.save(device);
        return policy;
    }

    @Override
    public WorkSchedule generateWorkSchedule(String deviceId) {
        return devicePersistenceOutputPort.getDeviceById(deviceId)
                .orElseThrow(() -> new NoSuchElementException("There is no device with id %s".formatted(deviceId)))
                .generateWorkSchedule(Instant.MIN);
    }

    @Override
    public boolean isPolicyVisible(String deviceId, String policyId) {
        List<Policy> policies = devicePersistenceOutputPort.getDeviceById(deviceId)
                .orElseThrow(() -> new NoSuchElementException("There is no device with id %s".formatted(deviceId)))
                .getPolicies();
        return policies.stream()
                .filter(policy -> policy.getUuid().equals(policyId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Device doesnt contain policy with id %s".formatted(policyId)))
                .isHidedCompletelyBy(policies);
    }

}
