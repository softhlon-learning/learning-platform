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
 * Activate account service interface.
 */
@InboundPort
@FunctionalInterface
public interface ActivateAccountService {

    /**
     * Activate account.
     * @param token Account activation token
     * @return Result
     */
    Result execute(
          String token);

    /**
     * Activate account result.
     */
    sealed interface Result {
        record Succeeded(String authToken) implements Result {}
        record InvalidTokenFailed(String message) implements Result {}
        record ExpiredTokenFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
