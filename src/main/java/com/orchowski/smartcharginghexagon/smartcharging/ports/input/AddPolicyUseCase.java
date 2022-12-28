package com.orchowski.smartcharginghexagon.smartcharging.ports.input;

import com.orchowski.smartcharginghexagon.smartcharging.domain.Policy;

public interface AddPolicyUseCase {
    Policy addPolicy(AddPolicyRequest addPolicyRequest);
}
