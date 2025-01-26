// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {environment} from "../../environment/environment"
import {FormBuilder} from '@angular/forms'
import {ActivatedRoute, Router} from "@angular/router"
import {AccountsService} from "../service/accounts/accounts.service"

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const HIDE_ERROR_DELAY = 2000
const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred'

@Component({
    selector: 'sign-in',
    templateUrl: './sign-in.component.html',
    styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
    error: string | undefined
    signInForm = this.formBuilder.group({
        email: '',
        password: ''
    })
    protected readonly environment = environment

    constructor(
        private formBuilder: FormBuilder,
        private accountsService: AccountsService,
        private route: ActivatedRoute,
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

        this.route.queryParamMap.subscribe(item => {
                console.log(item.get("error"))
                if (item.has("error")) {
                    this.error = item.get("error")?.toString()
                }
            }
        )
    }

    /**
     * On submit button press action. handler.
     */
    onSubmit(): void {
        if (this.signInForm.invalid) {
            console.log('Form is invalid')
            this.error = 'Please provide valid email and password'
            return
        }

        const {email = '', password = ''} = this.signInForm.value
        this.accountsService.signIn(email || '', password || '').subscribe({
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
     * Error response handler.
     * @param error Erorr from the server
     * @param defaultErrorMessage Default error message
     * @private
     */
    private handleError(error: any, defaultErrorMessage: string) {
        if (error?.status === 401) {
            this.error = error?.error?.message || defaultErrorMessage
        } else {
            this.error = defaultErrorMessage
        }

        setTimeout(() =>
                this.error = undefined
            , HIDE_ERROR_DELAY)
    }
}
