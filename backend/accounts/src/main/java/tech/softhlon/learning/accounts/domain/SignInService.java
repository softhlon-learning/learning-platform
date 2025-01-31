// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface SignInService {

    Result execute(
          String email,
          String password);

    sealed interface Result {
        record Succeeded(String token) implements Result {}
        record EmailPolicyFailed(String message) implements Result {}
        record InvalidCredentialsFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
