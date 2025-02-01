// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Create checkout session request body.
 */
export class CheckoutSessionRequest {
    priceId: string

    constructor(priceId: string) {
        this.priceId = priceId
    }
}

export class CheckoutSessionResponse {
    redirectUrl: string

    constructor(redirectUrl: string) {
        this.redirectUrl = redirectUrl;
    }
}


export class FetchCustomerPortalUrlResponse {
    redirectUrl: string

    constructor(redirectUrl: string) {
        this.redirectUrl = redirectUrl;
    }
}
