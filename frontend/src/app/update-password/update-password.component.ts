// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {environment} from "../../environment/environment"
import {FormBuilder} from '@angular/forms'
import {ActivatedRoute} from "@angular/router"
import {AccountsService} from '../service/accounts/accounts.service'

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const HIDE_ERROR_DELAY = 2000
const TOKEN_QUERY_PARAM = 'token'
const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred'

@Component({
    selector: 'sign-up',
    templateUrl: './update-password.component.html',
    styleUrls: ['./update-password.component.css'],
    standalone: false
})
export class UpdatePasswordComponent implements OnInit {
    success: boolean = false
    token: string | undefined
    error: string | undefined
    updatePasswordForm = this.formBuilder.group({
        password: ''
    })
    protected readonly environment = environment

    constructor(
        private formBuilder: FormBuilder,
        private accountsService: AccountsService,
        private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.error = undefined
        this.success = false

        this.route.queryParamMap.subscribe(
            paramMap => {
                if (paramMap.has(TOKEN_QUERY_PARAM)) {
                    this.token = paramMap.get(TOKEN_QUERY_PARAM)?.toString()
                }
            }
        )
    }

    /**
     * On submit button press action. handler.
     */
    onSubmit(): void {
        if (this.updatePasswordForm.invalid) {
            this.error = 'Please provide valid password'
            return
        }

        const {password} = this.updatePasswordForm.value
        this.accountsService.updatePassword(this.token, password || undefined).subscribe({
            next: () => this.handleSuccess(),
            error: (error) => this.handleError(error, DEFAULT_ERROR_MESSAGE),
        })
    }

    /**
     * Success response handler.
     * @private
     */
    private handleSuccess() {
        this.success = true
        this.error = undefined
    }

    /**
     * Error response handler.
     * @private
     */
    private handleError(error: any, defaultErrorMessage: string) {
        this.error = error?.error?.message
            || defaultErrorMessage

        setTimeout(
            () => this.error = undefined,
            HIDE_ERROR_DELAY)
    }
}
