package com.orchowski.smartcharginghexagon.workschedule.adapters;

import com.orchowski.smartcharginghexagon.shared.InMemoryGenericRepository;
import com.orchowski.smartcharginghexagon.workschedule.adapters.DeviceEntity;
import com.orchowski.smartcharginghexagon.workschedule.adapters.DeviceEntityRepository;

import java.util.UUID;

public class DeviceEntityInMemoryRepository extends InMemoryGenericRepository<DeviceEntity, String> implements DeviceEntityRepository {
    public DeviceEntityInMemoryRepository() {
        super(DeviceEntity::getId, () -> UUID.randomUUID().toString(), DeviceEntity::setId);
    }
}
