package com.orchowski.smartcharginghexagon.workschedule.ports.input;

import com.orchowski.smartcharginghexagon.workschedule.domain.Policy;

import java.util.List;

public interface GetDevicePoliciesUseCase {
    List<Policy> getPoliciesForDevice(String deviceId);
}
