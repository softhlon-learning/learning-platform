// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Update password service interface.
 */
@InboundPort
@FunctionalInterface
public interface UpdatePasswordService {

    /**
     * Update user's password.
     * @param token    Reset password token
     * @param password New password
     * @return Result
     */
    Result execute(
          String token,
          String password);

    /**
     * Update user's password result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record PasswordPolicyFailed(String message) implements Result {}
        record InvalidTokenFailed(String message) implements Result {}
        record ExpiredTokenFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
