// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Injectable} from '@angular/core'
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs'
import {CheckoutSessionRequest, CheckoutSessionResponse, FetchCustomerPortalUrlResponse} from "./subscriptions.model";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const SUBSCRIBE_PATH = '/api/v1/subscription/checkout-session'
const CUSTOMER_PORTAL_PATH = '/api/v1/subscription/customer-portal'

@Injectable({
    providedIn: 'root',
})
export class SubscriptionsService {
    constructor(
        private http: HttpClient) {
    }

    createCheckoutSession(priceId?: string): Observable<CheckoutSessionResponse> {
        return this.http
            .post<CheckoutSessionResponse>(
                SUBSCRIBE_PATH,
                new CheckoutSessionRequest(priceId || ''))
            .pipe()
    }

    fetchCustomerPortalUrl(): Observable<FetchCustomerPortalUrlResponse> {
        return this.http
            .get<CheckoutSessionResponse>(
                CUSTOMER_PORTAL_PATH)
            .pipe()
    }
}
