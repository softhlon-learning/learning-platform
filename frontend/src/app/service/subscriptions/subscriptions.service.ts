// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Injectable} from '@angular/core'
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs'
import {CheckoutSessionResponse} from "./subscriptions.model";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const SUBSCRIBE_PATH = '/api/v1/subscription/checkout-session'

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
                null)
            .pipe()
    }
}
