// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Email service implementation.
 */
@Slf4j
@Service
class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final boolean sendEmailsEnabled;


    public EmailServiceImpl(
          JavaMailSender emailSender,
          @Value("${send-emails.enabled}") boolean sendEmailsEnabled) {

        this.emailSender = emailSender;
        this.sendEmailsEnabled = sendEmailsEnabled;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(
          String to,
          String subject,
          String text) {

        if (sendEmailsEnabled == false) {
            log.info("Sending emails not enabled");
            return;
        }

        var message = new SimpleMailMessage();
        message.setFrom("Java FullStack Academy <support@java-fullstack.tech>");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

}
