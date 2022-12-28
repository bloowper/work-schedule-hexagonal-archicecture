package com.orchowski.smartcharginghexagon.smartcharging.ports.output;

import com.orchowski.smartcharginghexagon.smartcharging.domain.Device;

import java.util.Optional;

public interface DevicePersistenceOutputPort {
    Device save(Device device);

    Optional<Device> getDeviceById(String id);
}
