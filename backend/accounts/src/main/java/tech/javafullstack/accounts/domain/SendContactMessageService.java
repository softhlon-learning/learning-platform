// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import tech.javafullstack.common.hexagonal.InboundPort;

import java.util.UUID;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Add contact message service interface.
 */
@InboundPort
@FunctionalInterface
public interface SendContactMessageService {

    /**
     * Add contact message.
     * @param contacMessage Contact message
     * @return Result
     */
    Result execute(
          Request contacMessage);

    /**
     * Add contact message result.
     */
    sealed interface Result {
        record Succeeded() implements Result {}
        record MessagePolicyFailed(String message) implements Result {}
        record Failed(Throwable cause) implements Result {}
    }

    record Request(
          UUID accountId,
          String subject,
          String email,
          String message) {
    }

}
