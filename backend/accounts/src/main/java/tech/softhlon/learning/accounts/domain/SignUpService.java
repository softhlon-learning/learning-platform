// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Sign up service interface.
 */
@InboundPort
@FunctionalInterface
public interface SignUpService {

    /**
     * Sign up a new user.
     * @param name     User's name
     * @param email    User's email
     * @param password User's password
     * @return Result
     */
    Result execute(
          String name,
          String email,
          String password);

    /**
     * Sign up a new user result.
     */
    sealed interface Result {
        record Succeeded(UUID id, String authToken) implements Result {}
        record AccountAlreadyExistsFailed(String message) implements Result {}
        record AccountIsDeletedFailed(String message) implements Result {}
        record NamePolicyFailed(String message) implements Result {}
        record EmailPolicyFailed(String message) implements Result {}
        record PasswordPolicyFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
