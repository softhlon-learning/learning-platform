// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

class EmailTemplates {

    static final String RESET_PASSWORD_TEMPLATE = """
          Hello,
          
          We've received a request to reset your password. Please click the link below to set a new one:
          %s
          
          If you didn't request this, please ignore this email for your account's security.
          
          Best regards,
          Java Fullstack Academy Team
          """;

}
