package com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters;

import com.orchowski.smartcharginghexagon.smartcharging.domain.Device;
import com.orchowski.smartcharginghexagon.smartcharging.ports.output.DevicePersistenceOutputPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class DevicePersistenceOutputPortImpl implements DevicePersistenceOutputPort {
    private final DeviceEntityRepository deviceEntityRepository;
    private final DeviceMapper deviceMapper;
    @Override
    public Device save(Device device) {
        DeviceEntity deviceEntity = deviceMapper.toEntity(device);
        DeviceEntity save = deviceEntityRepository.save(deviceEntity);
        return deviceMapper.fromEntity(save);
    }

    @Override
    public Optional<Device> getDeviceById(String id) {
        return deviceEntityRepository.findById(id)
                .map(deviceMapper::fromEntity);
    }
}
