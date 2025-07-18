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
 * Sign out service interface.
 */
@InboundPort
@FunctionalInterface
public interface SignOutService {

    /**
     * Sign out user.
     * @param token Authentication token
     * @return Result
     */
    Result execute(
          String token);

    /**
     * Sing out user result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record NotAuthorized(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
