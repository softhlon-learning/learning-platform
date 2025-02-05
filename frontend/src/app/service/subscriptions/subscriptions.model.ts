// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
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

export class FetchFreeTrialResponse {
    timeLeft: string
    active: boolean

    constructor(timeLeft: string, active: boolean) {
        this.timeLeft = timeLeft;
        this.active = active;
    }
}
