// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

import tech.softhlon.learning.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@InboundPort
@FunctionalInterface
public interface GetProfileService {

    Result execute(
          UUID accountId);

    sealed interface Result {
        record Succeeded(ProfileView profileView) implements Result {}
        record ProfileNotFoundFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record ProfileView(
          String email,
          String name) {}

}

