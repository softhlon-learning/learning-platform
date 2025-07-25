// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

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
        record Succeeded(String authToken) implements Result {}
        record PasswordPolicyFailed(String message) implements Result {}
        record InvalidTokenFailed(String message) implements Result {}
        record ExpiredTokenFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
