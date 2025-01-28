// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {environment} from "../../environment/environment"
import {FormBuilder} from '@angular/forms'
import {AccountsService} from '../service/accounts/accounts.service'

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const HIDE_ERROR_DELAY = 2000
const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred'

@Component({
    selector: 'sign-up',
    templateUrl: './reset-password.component.html',
    styleUrls: ['./reset-password.component.css'],
    standalone: false
})
export class ResetPasswordComponent implements OnInit {
    success: boolean = false
    error: string | undefined
    resetPasswordForm = this.formBuilder.group({
        email: ''
    })
    protected readonly environment = environment

    constructor(
        private formBuilder: FormBuilder,
        private accountsService: AccountsService) {
    }

    /**
     * Init page.
     */
    ngOnInit() {
        this.error = undefined
        this.success = false
    }

    /**
     * React to submit button pressed action.
     */
    onSubmit(): void {
        if (this.resetPasswordForm.invalid) {
            this.error = 'Please provide valid password'
            return
        }

        const {email = ''} = this.resetPasswordForm.value
        this.accountsService.resetPassword(email || '').subscribe({
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
