// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) IO.SOFTHLON - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package io.softhlon.learning.accounts.gateway;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

class RestResources {
    static final String API_PREFIX = "/api/v1";
    static final String ACCOUNT = API_PREFIX + "/account";
    static final String SIGN_IN = ACCOUNT + "/auth/in";
    static final String SIGN_OUT = ACCOUNT + "/auth/out";
    static final String SIGN_UP = ACCOUNT + "/sign-up";
}
