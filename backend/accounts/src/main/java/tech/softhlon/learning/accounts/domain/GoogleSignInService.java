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
 * Google sing in service interface.
 */
@InboundPort
@FunctionalInterface
public interface GoogleSignInService {

    /**
     * Sign in user with Google credential.
     * @param credential User's Google credentioal
     * @return Result
     */
    Result execute(
          String credential);

    /**
     * Sign in user with Google credential result.
     */
    sealed interface Result {
        record Succeeded(String token) implements Result {}
        record AccountIsDeletedFailed(String message) implements Result {}
        record InvalidCredentialsFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

}
