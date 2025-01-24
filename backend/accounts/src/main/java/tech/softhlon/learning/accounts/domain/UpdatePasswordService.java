// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.accounts.domain;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

interface UpdatePasswordService {
    Result execute(Request request);

    record Request(String password, String token) {}

    sealed interface Result {
        record Succeeded() implements Result {}
        record InvalidTokenFailed(String message) implements Result {}
        record ExpiredTokenFailed(String message) implements Result {}
        record Failed() implements Result {}
    }
}
