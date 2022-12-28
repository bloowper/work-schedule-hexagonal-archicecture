package com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
class DeviceEntity {
    @Id
    private String id;
    private List<PolicyEntity> policies;
}
