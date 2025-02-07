// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {environment} from "../../environment/environment"
import {FormBuilder} from '@angular/forms'
import {Router} from "@angular/router"
import {AccountsService} from "../service/accounts/accounts.service"

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const HIDE_ERROR_DELAY = 2000
const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred'

@Component({
    selector: 'sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: ['./sign-up.component.css'],
    standalone: false
})
export class SignUpComponent implements OnInit {
    error: string | undefined
    signInForm = this.formBuilder.group({
        name: '',
        email: '',
        password: ''
    })
    protected readonly environment = environment

    constructor(
        private formBuilder: FormBuilder,
        private accountsService: AccountsService,
        private router: Router) {
    }

    /**
     * Initialize page.
     */
    ngOnInit() {
        this.error = undefined
        const button = document.querySelector("#g_id_onload") as HTMLElement | null
        button?.setAttribute("data-client_id", environment.googleClientId)
        button?.setAttribute("data-login_uri", environment.loginUri)
    }

    /**
     * On submit button press action. handler.
     */
    onSubmit(): void {
        if (this.signInForm.invalid) {
            this.error = 'Please provide valid email and password'
            return
        }

        const {name = '', email = '', password = ''} = this.signInForm.value
        this.accountsService.signUp(name || '', email || '', password || '').subscribe({
            next: () => this.handleSuccess(),
            error: (error) => this.handleError(error, DEFAULT_ERROR_MESSAGE),
        })
    }

    /**
     * Success response handler.
     * @private
     */
    private handleSuccess() {
        this.router.navigate(['/home'])
            .then(() => {
                window.location.reload()
            })
    }

    /**
     * Error response handler
     * @param error Eror from the server
     * @param defaultErrorMessage Default error message
     * @private
     */
    private handleError(error: any, defaultErrorMessage: string) {
        this.error = error?.error?.message || defaultErrorMessage
        setTimeout(() => {
            this.error = undefined
        }, HIDE_ERROR_DELAY)
    }
}
