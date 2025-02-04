// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Fetch profile service interface.
 */
@InboundPort
@FunctionalInterface
public interface FetchProfileService {

    /**
     * Fetch user's profile.
     * @param accountId Account Id
     * @return Result
     */
    Result execute(
          UUID accountId);

    /**
     * Fetch user's profile result.
     */
    sealed interface Result {
        record Succeeded(ProfileView profileView) implements Result {}
        record ProfileNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record ProfileView(
          String email,
          String name) {}

}

