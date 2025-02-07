// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

class EmailTemplates {

    static final String ACTIVATE_ACCOUNT_TEMPLATE = """
          Dear User,
          
          Thank you for registering with Java FullStack Academy! We're excited to have you as part of our community. 
          To activate your account and start using our services, click on this activation link: 
          %s
             
          Once you click the link, your account will be activated, and you'll be all set to explore Java FullStack Academy.
          
          Note: This link is valid for 24 hours. 
          If you do not activate your account within this time, you may need to register again.
          
          If you encounter any issues or if you did not intend to sign up for Java FullStack Academy, 
          please reply to this email, and we will assist you promptly.
          
          Welcome aboard!
          
          Best regards,
          
          Support Team
          Java FullStack Academy
          support@java-fullstack.tech
          https://java-fullstack.tech
          """;

    static final String RESET_PASSWORD_TEMPLATE = """
          Hello,
          
          We've received a request to reset your password. Please click the link below to set a new one:
          %s
          
          If you didn't request this, please ignore this email for your account's security.
          
          Best regards,
                              
          Support Team
          Java FullStack Academy
          support@java-fullstack.tech
          https://java-fullstack.tech
          """;

}
