package com.orchowski.smartcharginghexagon.smartcharging.domain;

import com.orchowski.smartcharginghexagon.shared.InMemoryGenericRepository;
import com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters.DeviceEntity;
import com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters.DeviceEntityRepository;

import java.util.UUID;

class DeviceEntityInMemoryRepository extends InMemoryGenericRepository<DeviceEntity, String> implements DeviceEntityRepository {
    public DeviceEntityInMemoryRepository() {
        super(DeviceEntity::getId, () -> UUID.randomUUID().toString(), DeviceEntity::setId);
    }
}
