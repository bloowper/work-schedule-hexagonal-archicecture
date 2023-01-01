package com.orchowski.smartcharginghexagon.workschedule.ports.input;

import com.orchowski.smartcharginghexagon.workschedule.domain.Policy;

public interface AddPolicyUseCase {
    Policy addPolicy(AddPolicyRequest addPolicyRequest);
}
