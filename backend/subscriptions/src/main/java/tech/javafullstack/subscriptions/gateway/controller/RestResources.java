// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

package tech.javafullstack.subscriptions.gateway.controller;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * REST endpoints constants.
 */
class RestResources {

    static final String API_PREFIX = "/api/v1";
    static final String SUBSCRIPTION = API_PREFIX + "/subscription";
    static final String CUSTOMER_PORTAL = SUBSCRIPTION + "/customer-portal";
    static final String CHECKOUT_SESSION = SUBSCRIPTION + "/checkout-session";
    static final String SUBMIT_CHECKOUT_COMPLETED = SUBSCRIPTION + "/checkout-completed-event";
    static final String SUBMIT_CUSTOMER_CREATED = SUBSCRIPTION + "/customer-created-event";
    static final String SUBMIT_SUBSCRIPTION_CREATED = SUBSCRIPTION + "/created-event";
    static final String SUBMIT_SUBSCRIPTION_DELETED = SUBSCRIPTION + "/deleted-event";
    static final String SUBMIT_SUBSCRIPTION_UPDATED = SUBSCRIPTION + "/updated-event";
    static final String SUBMIT_SUBSCRIPTION_GENERIC = SUBSCRIPTION + "/generic-event";
    static final String SUBMIT_INVOICE_PAID = SUBSCRIPTION + "/invoice-paid-event";
    static final String FETCH_FREE_TRIAL = SUBSCRIPTION + "/free-trial";

}
