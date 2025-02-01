// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {SubscriptionsService} from "../service/subscriptions/subscriptions.service";
import {CheckoutSessionResponse} from "../service/subscriptions/subscriptions.model";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const PRICE_ID = 'price_1QmYo3IdZlPlV5wEDgbXc1Js'
const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred'
const HIDE_ERROR_DELAY = 2000

@Component({
    selector: 'sign-up',
    templateUrl: './manage-subscription.component.html',
    styleUrls: ['./manage-subscription.component.css'],
    standalone: false
})
export class ManageSubscriptionComponent implements OnInit {
    success: boolean = false
    error: string | undefined

    constructor(
        private subscriptionsService: SubscriptionsService) {
    }

    /**
     * Init page.
     */
    ngOnInit() {
        this.error = undefined
        this.success = false
    }

    checkoutSession(): void {
        this.subscriptionsService.fetchCustomerPortalUrl().subscribe({
            next: (response) => this.handleSuccess(response),
            error: (error) => this.handleError(error, DEFAULT_ERROR_MESSAGE),
        })
    }

    /**
     * Success response handler.
     * @private
     */
    private handleSuccess(response: CheckoutSessionResponse) {
        console.log(response)
        location.href = response.redirectUrl
    }

    /**
     * Error response handler
     * @param error Eror from the server
     * @param defaultErrorMessage Default error message
     * @private
     */
    private handleError(error: any, defaultErrorMessage: string) {
        console.error(error)

        this.error = error?.error?.message || defaultErrorMessage
        setTimeout(() => {
            this.error = undefined
        }, HIDE_ERROR_DELAY)
    }
}
