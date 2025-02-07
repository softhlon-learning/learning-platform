// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Email service interface.
 */
@FunctionalInterface
interface EmailService {

    /**
     * Send email message.
     * @param to      Email recipient
     * @param subject Email subject
     * @param text    Email texgt
     */
    void sendMessage(
          String to,
          String subject,
          String text);

}
