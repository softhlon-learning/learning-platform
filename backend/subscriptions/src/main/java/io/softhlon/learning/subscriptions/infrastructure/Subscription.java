// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.subscriptions.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Getter
@Builder
@Entity(name = "subscriptions")
public class Subscription {
    @Id
    private UUID id;
    private UUID accountId;
    private String status;
    private OffsetDateTime startedTime;
    private OffsetDateTime cancelledTime;
    private OffsetDateTime createdTime;
    private OffsetDateTime updatedTime;
}
