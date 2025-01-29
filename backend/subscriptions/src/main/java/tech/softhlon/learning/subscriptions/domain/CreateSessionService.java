// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.domain;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@FunctionalInterface
interface CreateSessionService {

    CreateSessionResult execute(
          CreateSessionRequest request);

    record CreateSessionRequest(
          UUID accountId,
          String sessionID) {}

    sealed interface CreateSessionResult {
        record SessionCreated() implements CreateSessionResult {}
        record SessionCrea() implements CreateSessionResult {}
    }

}
