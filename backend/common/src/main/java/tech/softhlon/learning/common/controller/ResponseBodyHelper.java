// -------------------------------------------------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// -------------------------------------------------------------------------------------------------------------------------------------------------------------
package tech.softhlon.learning.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.status;
import static tech.softhlon.learning.common.controller.ResponseBodyHelper.ResponseField.*;

// -------------------------------------------------------------------------------------------------------------------------------------------------------------
// Implementation
// -------------------------------------------------------------------------------------------------------------------------------------------------------------

/**
 * Http response body helper utility.
 */
@Slf4j
public class ResponseBodyHelper {
    private static final String INTERNAL_ERROR = "Server internal error";

    /**
     * HTTP 200 response body.
     * @return ResponseEntity
     */
    public static ResponseEntity successOkBody() {
        return status(HttpStatus.OK)
              .build();
    }

    /**
     * HTTP 201 response body.
     * @return ResponseEntity
     */
    public static ResponseEntity successCreatedBody() {
        return status(HttpStatus.CREATED)
              .build();
    }

    /**
     * HTTP 202 response body.
     * @return ResponseEntity
     */
    public static ResponseEntity successAcceptedBody() {
        return status(HttpStatus.ACCEPTED)
              .build();
    }

    /**
     * HTTP 303 response body.
     * @return ResponseEntity
     */
    public static ResponseEntity redirectBody() {
        return status(HttpStatus.SEE_OTHER)
              .build();
    }

    /**
     * HTTP 505 response body.
     * @param request HttpServletRequest
     * @param cause   Throwable
     * @return ResponseEntity
     */
    public static ResponseEntity<?> internalServerBody(
          HttpServletRequest request,
          Throwable cause) {

        log.error("Unexpected error:", cause);
        return ResponseEntity
              .internalServerError()
              .body(
                    ResponseBodyBuilder.builder()
                          .status(HttpStatus.INTERNAL_SERVER_ERROR)
                          .errorMessage(ErrorMessage.INTERNAL_SERVER_ERROR)
                          .message(INTERNAL_ERROR)
                          .path(request.getRequestURI())
                          .build()
                          .body()
              );

    }

    /**
     * HTTP 400 response body.
     * @param request HttpServletRequest
     * @param message Error message
     * @return ResponseEntity
     */
    public static ResponseEntity<?> badRequestBody(
          HttpServletRequest request,
          String message) {

        return ResponseEntity
              .badRequest()
              .body(
                    ResponseBodyBuilder.builder()
                          .status(HttpStatus.BAD_REQUEST)
                          .errorMessage(ErrorMessage.VALIDATION_ERROR)
                          .message(message)
                          .path(request.getRequestURI())
                          .build()
                          .body()
              );

    }

    /**
     * HTTP 201 response body.
     * @param request HttpServletRequest
     * @param message Error message
     * @return ResponseEntity
     */
    public static ResponseEntity<?> unAuthorizedBody(
          HttpServletRequest request,
          String message) {

        return ResponseEntity
              .status(HttpStatus.UNAUTHORIZED)
              .body(
                    ResponseBodyBuilder.builder()
                          .status(HttpStatus.UNAUTHORIZED)
                          .errorMessage(ErrorMessage.AUTHENTICATION_ERROR)
                          .message(message)
                          .path(request.getRequestURI())
                          .build()
                          .body()
              );

    }

    /**
     * HTTP response fields.
     */
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

    /**
     * Errot message enum.
     */
    private enum ErrorMessage {

        AUTHENTICATION_ERROR("Authentication Error"),
        VALIDATION_ERROR("Validation Error"),
        INTERNAL_SERVER_ERROR("Internal Server Error");

        private final String text;

        ErrorMessage(String text) {
            this.text = text;
        }

    }

    /**
     * HTTP response body builder.
     */
    @Builder
    private static class ResponseBodyBuilder {

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

}
