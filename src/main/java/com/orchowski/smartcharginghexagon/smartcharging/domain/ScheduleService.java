package com.orchowski.smartcharginghexagon.smartcharging.domain;

import com.orchowski.smartcharginghexagon.smartcharging.ports.input.AddPolicyRequest;
import com.orchowski.smartcharginghexagon.smartcharging.ports.input.AddPolicyUseCase;
import com.orchowski.smartcharginghexagon.smartcharging.ports.input.CreateDeviceRequest;
import com.orchowski.smartcharginghexagon.smartcharging.ports.input.CreateDeviceUseCase;
import com.orchowski.smartcharginghexagon.smartcharging.ports.output.DevicePersistenceOutputPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class ScheduleService implements AddPolicyUseCase, CreateDeviceUseCase {
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
}
