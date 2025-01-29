// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Injectable} from '@angular/core'
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs'
import {Course} from '../../home/course'
import {CheckoutSessionRequest} from "./subscriptions.model";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const SUBSCRIBE_PATH = '/api/v1/subscription/checkout-session'

@Injectable({
    providedIn: 'root',
})
export class CoursesService {
    private courses?: Course[]

    constructor(
        private http: HttpClient) {
    }

    createCheckoutSession(priceId?: string): Observable<ArrayBuffer> {
        const checkoutSessionRequest =
            new CheckoutSessionRequest(priceId || '')

        return this.http
            .post<ArrayBuffer>(
                SUBSCRIBE_PATH,
                checkoutSessionRequest)
            .pipe()
    }
}
