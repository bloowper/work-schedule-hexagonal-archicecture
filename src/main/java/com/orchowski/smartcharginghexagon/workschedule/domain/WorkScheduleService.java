package com.orchowski.smartcharginghexagon.workschedule.domain;

import com.orchowski.smartcharginghexagon.workschedule.ports.input.AddPolicyRequest;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.AddPolicyUseCase;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.CheckPolicyVisibilityStatusUseCase;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.CreateDeviceRequest;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.CreateDeviceUseCase;
import com.orchowski.smartcharginghexagon.workschedule.ports.input.GenerateDeviceWorkScheduleUseCase;
import com.orchowski.smartcharginghexagon.workschedule.ports.output.DevicePersistenceOutputPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class WorkScheduleService implements AddPolicyUseCase, GenerateDeviceWorkScheduleUseCase, CreateDeviceUseCase, CheckPolicyVisibilityStatusUseCase {
    //TODO provide domain exceptions
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
                .orElseThrow(() -> new NoSuchElementException("There is no device with id %s".formatted(addPolicyRequest.getDeviceId())));
        device.addNewPolicy(policy);
        devicePersistenceOutputPort.save(device);
        return policy;
    }

    @Override
    public WorkSchedule generateWorkSchedule(String deviceId) {
        return devicePersistenceOutputPort.getDeviceById(deviceId)
                .orElseThrow(() -> new NoSuchElementException("There is no device with id %s".formatted(deviceId)))
                .generateWorkSchedule();
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
