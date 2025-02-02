// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {environment} from "../../environment/environment"
import {FormBuilder} from "@angular/forms"
import {AccountsService} from '../service/accounts/accounts.service'
import {Profile} from '../service/accounts/accounts.model'
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred'

@Component({
    selector: 'profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css'],
    standalone: false
})
export class ProfileComponent implements OnInit {
    error: string | undefined
    success: string | undefined
    accountDeleted: boolean = false
    profile?: Profile
    profileForm = this.formBuilder.group({
        name: ''
    })
    protected readonly environment = environment

    constructor(
        private formBuilder: FormBuilder,
        private cookieService: CookieService,
        private router: Router,
        private accountsService: AccountsService) {
    }

    /**
     * Init component (fetch profile).
     */
    ngOnInit() {
        this.error = undefined
        if (this.cookieService.get('Authenticated') !== 'true') {
            this.router.navigate(['/sign-in'])
                .then(() => {
                    window.location.reload()
                })
        }
        this.fetchProfile()
    }

    /**
     * Process submit button action.
     */
    onSubmit(): void {
        if (this.profileForm.invalid) {
            this.error = 'Please provide valid name'
            return
        }
        const {name = ''} = this.profileForm.value

        this.accountsService.updateProfile(name || '').subscribe({
            next: () => this.handleSaveProfileSuccess(),
            error: (error) => this.handleError(error, DEFAULT_ERROR_MESSAGE),
        })
    }

    /**
     * Delete account.
     */
    deleteAccount(): void {
        this.accountsService.deleteAccount().subscribe({
            next: () => this.handleDeleteAccountSuccess(),
            error: (error) => this.handleError(error, DEFAULT_ERROR_MESSAGE),
        })
    }

    /**
     * Fetch profile from service.
     * @private
     */
    private fetchProfile(): void {
        this.accountsService.getProfile()
            .subscribe(profile => {
                this.profileForm.setValue({name: profile.name})
                this.profile = profile
            })
    }

    /**
     * Success response handler (for update profile).
     * @private
     */
    private handleSaveProfileSuccess() {
        this.error = undefined
        this.accountDeleted = true
        this.success = 'Profile successfully saved'
    }

    /**
     * Success response handler (for delete account).
     * @private
     */
    private handleDeleteAccountSuccess() {
        this.error = undefined
        this.success = 'Account has been deleted'
    }

    /**
     * Fail response handler
     * @param error Error from service
     * @param defaultErrorMessage Default error message
     * @private
     */
    private handleError(error: any, defaultErrorMessage: string) {
        this.success = undefined
        this.error = error?.error?.message || defaultErrorMessage
    }
}
