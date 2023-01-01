package com.orchowski.smartcharginghexagon.workschedule.ports.output;

import com.orchowski.smartcharginghexagon.workschedule.domain.Device;

import java.util.Optional;

public interface DevicePersistenceOutputPort {
    Device save(Device device);

    Optional<Device> getDeviceById(String id);
}
