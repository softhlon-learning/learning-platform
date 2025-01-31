// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.subscriptions.gateway;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

class RestResources {

    static final String API_PREFIX = "/api/v1";
    static final String SUBSCRIPTION = API_PREFIX + "/subscription";
    static final String CUSTOMER_PORTAL = SUBSCRIPTION + "/customer-portal";
    static final String CHECKOUT_SESSION = SUBSCRIPTION + "/checkout-session";
    static final String SUBMIT_CHECKOUT_COMPLETED = SUBSCRIPTION + "/checkout-completed-event";
    static final String SUBMIT_SUBSCRIPTION_CREATED = SUBSCRIPTION + "/created-event";
    static final String SUBMIT_SUBSCRIPTION_DELETED = SUBSCRIPTION + "/deleted-event";
    static final String SUBMIT_SUBSCRIPTION_GENERIC = SUBSCRIPTION + "/generic-event";

}
