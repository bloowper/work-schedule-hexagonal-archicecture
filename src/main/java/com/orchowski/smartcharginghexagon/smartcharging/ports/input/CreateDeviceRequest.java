package com.orchowski.smartcharginghexagon.smartcharging.ports.input;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateDeviceRequest {
    private final String deviceId;
}
