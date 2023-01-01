package com.orchowski.smartcharginghexagon.workschedule.ports.input;

public interface CheckPolicyVisibilityStatusUseCase {
    boolean isPolicyVisible(String deviceId, String policyId);
}
