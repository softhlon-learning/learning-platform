// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.accounts.gateway.controller;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * REST endpoints constants.
 */
class RestResources {

    static final String API_PREFIX = "/api/v1";
    static final String ACCOUNT = API_PREFIX + "/account";
    static final String SIGN_IN = ACCOUNT + "/auth/sign-in";
    static final String GOOGLE_SIGN_IN = ACCOUNT + "/auth/google-sign-in";
    static final String ACTIVATE_ACCOUNT = ACCOUNT + "/activate";
    static final String SIGN_OUT = ACCOUNT + "/auth/sign-out";
    static final String SIGN_UP = ACCOUNT + "/sign-up";
    static final String PROFILE = ACCOUNT + "/profile";
    static final String RESET_PASSWORD = ACCOUNT + "/reset-password";
    static final String UPDATE_PASSWORD = ACCOUNT + "/update-password";

}
