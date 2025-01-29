// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {SubscriptionsService} from "../service/subscriptions/subscriptions.service";
import {Router} from "@angular/router";
import {CheckoutSessionResponse} from "../service/subscriptions/subscriptions.model";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const PRICE_ID = 'price_1QmYo3IdZlPlV5wEDgbXc1Js'
const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred'
const HIDE_ERROR_DELAY = 2000

@Component({
    selector: 'sign-up',
    templateUrl: './subscribe.component.html',
    styleUrls: ['./subscribe.component.css'],
    standalone: false
})
export class SubscribeComponent implements OnInit {
    success: boolean = false
    error: string | undefined

    constructor(
        private subscriptionsService: SubscriptionsService,
        private router: Router) {
    }

    /**
     * Init page.
     */
    ngOnInit() {
        this.error = undefined
        this.success = false
    }

    checkoutSession(): void {
        this.subscriptionsService.createCheckoutSession(PRICE_ID).subscribe({
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
        location.href=response.redirectUrl
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
