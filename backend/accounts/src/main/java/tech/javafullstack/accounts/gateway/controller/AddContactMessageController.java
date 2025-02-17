// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.gateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.javafullstack.accounts.domain.AddContactMessageService;
import tech.javafullstack.accounts.domain.AddContactMessageService.Request;
import tech.javafullstack.accounts.domain.AddContactMessageService.Result.Failed;
import tech.javafullstack.accounts.domain.AddContactMessageService.Result.MessagePolicyFailed;
import tech.javafullstack.accounts.domain.AddContactMessageService.Result.Succeeded;
import tech.javafullstack.common.hexagonal.RestApiAdapter;

import static tech.javafullstack.accounts.gateway.controller.RestResources.ADD_CONTACT_MESSAGE;
import static tech.javafullstack.common.controller.ResponseBodyHelper.*;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Activate account controller.
 */
@Slf4j
@RestApiAdapter
@RestController
@RequiredArgsConstructor
class AddContactMessageController {

    private final AddContactMessageService service;
    private final HttpServletRequest httpRequest;

    /**
     * POST /api/v1/account/contact-message endpoint.
     * @param request  ContactMEssage
     * @param response ActivateAccount
     * @return ResponseEntity<?>
     */
    @PostMapping(ADD_CONTACT_MESSAGE)
    ResponseEntity<?> addContactMessage(
          @Validated @RequestBody ContactMessageRequest request,
          HttpServletResponse response) {

        log.info("controller | request / Activate account, {}",
              request);

        var result = service.execute(new Request(request.subject(), request.email(), request.message()));
        return switch (result) {
            case Succeeded() -> successOkBody();
            case MessagePolicyFailed(String message) -> badRequestBody(httpRequest, message);
            case Failed(Throwable cause) -> internalServerBody(httpRequest, cause);
        };

    }

    record ContactMessageRequest(
          String subject,
          String email,
          String message) {

    }

}
