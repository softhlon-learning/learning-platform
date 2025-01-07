// -------------------------------------------------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// -------------------------------------------------------------------------------------------------------------------------------------------------------------
package io.softhlon.learning.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.softhlon.learning.common.controller.ResponseBodyHelper.ResponseField.*;
import static org.springframework.http.ResponseEntity.status;

// -------------------------------------------------------------------------------------------------------------------------------------------------------------
// Implementation
// -------------------------------------------------------------------------------------------------------------------------------------------------------------

@Slf4j
public class ResponseBodyHelper {
    public static ResponseEntity successOkBody() {
        return status(HttpStatus.OK).build();
    }

    public static ResponseEntity successCreatedBody() {
        return status(HttpStatus.CREATED).build();
    }

    public static ResponseEntity successAcceptedBody() {
        return status(HttpStatus.ACCEPTED).build();
    }

    public static ResponseEntity<?> internalServerBody(HttpServletRequest request, Throwable cause) {
        log.error("Unexpected error:", cause);
        return ResponseEntity.internalServerError()
              .body(
                    ResponseBodyBuilder.builder()
                          .status(HttpStatus.INTERNAL_SERVER_ERROR)
                          .errorMessage(ErrorMessage.INTERNAL_SERVER_ERROR)
                          .message(cause.getMessage())
                          .path(request.getRequestURI())
                          .build()
                          .body());
    }

    public static ResponseEntity<?> badRequestBody(HttpServletRequest request, String message) {
        return ResponseEntity.badRequest()
              .body(
                    ResponseBodyBuilder.builder()
                          .status(HttpStatus.BAD_REQUEST)
                          .errorMessage(ErrorMessage.VALIDATION_ERROR)
                          .message(message)
                          .path(request.getRequestURI())
                          .build()
                          .body());
    }

    @Builder
    static class ResponseBodyBuilder {
        private HttpStatus status;
        private ErrorMessage errorMessage;
        private Object message;
        private String path;

        Map<String, Object> body() {
            Map<String, Object> responseBody = new LinkedHashMap<>();
            responseBody.put(TIMESTAMP.text, OffsetDateTime.now());
            responseBody.put(STATUS.text, status.value());
            responseBody.put(ERROR.text, errorMessage.text);
            responseBody.put(MESSAGE.text, message);
            responseBody.put(PATH.text, path);

            return responseBody;
        }
    }

    enum ResponseField {
        TIMESTAMP("timestamp"),
        STATUS("status"),
        ERROR("error"),
        MESSAGE("message"),
        PATH("path");

        private final String text;

        ResponseField(String text) {
            this.text = text;
        }
    }

    enum ErrorMessage {
        VALIDATION_ERROR("Validation Error"),
        INTERNAL_SERVER_ERROR("Internal Server Error");

        private final String text;

        ErrorMessage(String text) {
            this.text = text;
        }
    }
}
