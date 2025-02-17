// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {FormBuilder} from "@angular/forms";
import {AccountsService} from "../service/accounts/accounts.service";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const HIDE_ERROR_DELAY = 2000
const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred'

@Component({
    selector: 'contact',
    templateUrl: './contact.component.html',
    styleUrls: ['./contact.component.css'],
    standalone: false
})
export class ContactComponent implements OnInit {
    success: boolean = false
    error: string | undefined
    contactForm = this.formBuilder.group({
        subject: '',
        email: '',
        message: ''
    })

    constructor(
        private formBuilder: FormBuilder,
        private accountsService: AccountsService) {
    }

    /**
     * Init component.
     */
    ngOnInit() {
        this.error = undefined
        this.success = false
    }

    /**
     * React to submit button pressed action.
     */
    onSubmit(): void {
        if (this.contactForm.invalid) {
            this.error = 'Please provide valid password'
            return
        }

        const {subject = '', email = '', message = ''} = this.contactForm.value
        this.accountsService.sendContactMessage(subject || '', email || '', message || '').subscribe({
            next: () => this.handleSuccess(),
            error: (error) => this.handleError(error, DEFAULT_ERROR_MESSAGE),
        })
    }

    /**
     * Handle success response.
     * @private
     */
    private handleSuccess() {
        this.success = true
    }

    /**
     * Handle error response.
     * @param error Error from the service
     * @param defaultErrorMessage Default errorn message
     * @private
     */
    private handleError(error: any, defaultErrorMessage: string) {
        this.error = error?.error?.message || defaultErrorMessage
        setTimeout(() => {
            this.error = undefined
        }, HIDE_ERROR_DELAY)
    }
}
