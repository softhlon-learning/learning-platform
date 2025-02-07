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
 * Sign in with email and password service interface.
 */
@InboundPort
@FunctionalInterface
public interface SignInService {

    /**
     * Sign in with email and password.
     * @param email    User's email
     * @param password USer's apssword
     * @return Result
     */
    Result execute(
          String email,
          String password);

    /**
     * Sign in with email and password result.
     */
    sealed interface Result {
        record Succeeded(String token) implements Result {}
        record EmailPolicyFailed(String message) implements Result {}
        record InvalidCredentialsFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
