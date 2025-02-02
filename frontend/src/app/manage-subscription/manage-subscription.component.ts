// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {SubscriptionsService} from "../service/subscriptions/subscriptions.service";
import {CheckoutSessionResponse} from "../service/subscriptions/subscriptions.model";
import { NgxSpinnerService } from 'ngx-spinner';

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred'
const HIDE_ERROR_DELAY = 2000
const SPINNER_DELAY = 10000;

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
        private subscriptionsService: SubscriptionsService,
        private spinner: NgxSpinnerService) {
    }

    /**
     * Init page.
     */
    ngOnInit() {
        this.spinner.show();
        setTimeout(() => {
            this.spinner.hide();
        }, SPINNER_DELAY);

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
