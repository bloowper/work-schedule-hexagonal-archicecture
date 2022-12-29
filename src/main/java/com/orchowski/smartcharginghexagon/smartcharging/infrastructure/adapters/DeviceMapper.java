package com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters;

import com.orchowski.smartcharginghexagon.smartcharging.domain.Device;
import com.orchowski.smartcharginghexagon.smartcharging.domain.Policy;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface DeviceMapper {

    DeviceEntity toEntity(Device device);

    Device fromEntity(DeviceEntity deviceEntity);

    Policy fromEntity(PolicyEntity policyEntity);

    PolicyEntity toEntity(Policy policy);

    default List<Policy> policyEntityListToPolicyList(List<PolicyEntity> policyEntities) {
        //TODO [question] why mapstruct cannot handle
        // @Mapping(target = "policies",source = "policies",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
        if (policyEntities == null) {
            return new ArrayList<>();
        }
        return policyEntities.stream().map(this::fromEntity).collect(Collectors.toList());
    }
}
