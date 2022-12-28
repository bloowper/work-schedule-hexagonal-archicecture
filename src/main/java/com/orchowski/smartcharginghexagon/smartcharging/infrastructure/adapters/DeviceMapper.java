package com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters;

import com.orchowski.smartcharginghexagon.smartcharging.domain.Device;
import com.orchowski.smartcharginghexagon.smartcharging.domain.Policy;
import org.mapstruct.Mapper;

@Mapper
interface DeviceMapper {

    DeviceEntity toEntity(Device device);

    Device fromEntity(DeviceEntity deviceEntity);

    Policy toEntity(PolicyEntity policyEntity);

    PolicyEntity fromEntity(Policy policy);
}
