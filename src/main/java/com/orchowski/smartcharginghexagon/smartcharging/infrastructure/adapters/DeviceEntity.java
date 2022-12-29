package com.orchowski.smartcharginghexagon.smartcharging.infrastructure.adapters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document("Device")
public class DeviceEntity {
    @Id
    private String id;
    private List<PolicyEntity> policies;
}
