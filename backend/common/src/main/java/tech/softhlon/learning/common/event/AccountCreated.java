// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.common.event;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Account created event.
 */
@Event
@Getter
@Builder
public class AccountCreated extends ApplicationEvent {

    private UUID acountId;

    public AccountCreated(Object source) {
        super(source);
    }

}
