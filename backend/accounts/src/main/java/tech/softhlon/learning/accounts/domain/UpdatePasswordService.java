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
public interface UpdatePasswordService {

    Result execute(
          String token,
          String password);

    sealed interface Result {
        record Succeeded() implements Result {}
        record PasswordPolicyFailed(String message) implements Result {}
        record InvalidTokenFailed(String message) implements Result {}
        record ExpiredTokenFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
