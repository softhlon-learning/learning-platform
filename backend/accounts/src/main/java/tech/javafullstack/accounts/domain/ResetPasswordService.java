// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Reset password service interface.
 */
@InboundPort
@FunctionalInterface
public interface ResetPasswordService {

    /**
     * Reset password for given email.
     * @param email User's email
     * @return Result
     */
    Result execute(
          String email);

    /**
     * Reset password for given email result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record EmailPolicyFailed(String message) implements Result {}
        record EmailNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
