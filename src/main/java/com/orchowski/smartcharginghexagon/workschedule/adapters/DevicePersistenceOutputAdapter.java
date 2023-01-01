package com.orchowski.smartcharginghexagon.workschedule.adapters;

import com.orchowski.smartcharginghexagon.workschedule.domain.Device;
import com.orchowski.smartcharginghexagon.workschedule.ports.output.DevicePersistenceOutputPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
class DevicePersistenceOutputAdapter implements DevicePersistenceOutputPort {
    private final DeviceEntityRepository deviceEntityRepository;
    private final EntityMapper entityMapper;
    @Override
    public Device save(Device device) {
        DeviceEntity deviceEntity = entityMapper.toEntity(device);
        DeviceEntity save = deviceEntityRepository.save(deviceEntity);
        return entityMapper.fromEntity(save);
    }

    @Override
    public Optional<Device> getDeviceById(String id) {
        return deviceEntityRepository.findById(id)
                .map(entityMapper::fromEntity);
    }
}
