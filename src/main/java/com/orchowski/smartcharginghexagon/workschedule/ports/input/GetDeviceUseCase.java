package com.orchowski.smartcharginghexagon.workschedule.ports.input;

import com.orchowski.smartcharginghexagon.workschedule.domain.Device;

import java.util.List;

public interface GetDeviceUseCase { //This going to lead to method explosion :sad-pepe:
    List<Device> getDevices();
}
