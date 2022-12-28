package com.orchowski.smartcharginghexagon.smartcharging.ports.input;

public interface CheckPolicyVisibilityStatusUseCase {
    boolean isPolicyVisible(String deviceId, String policyId);
}
