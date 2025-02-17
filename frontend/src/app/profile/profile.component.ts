// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {environment} from "../../environment/environment"
import {FormBuilder} from "@angular/forms"
import {AccountsService} from '../service/accounts/accounts.service'
import {Profile} from '../service/accounts/accounts.model'
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
import {AUTHENTICATED_COOKIE} from "../common/constants";

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
    accountToBeDeleted: boolean = false
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
        if (this.cookieService.get(AUTHENTICATED_COOKIE) !== 'true') {
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
        this.accountToBeDeleted = true;
    }

    /**
     * Delete account.
     */
    doNotDeleteAccount(): void {
        this.accountToBeDeleted = false;
        this.router.navigate(['/profile'])
    }

    /**
     * Delete account.
     */
    confirmDeleteAccount(): void {
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
        this.success = 'Profile successfully saved'
    }

    /**
     * Success response handler (for delete account).
     * @private
     */
    private handleDeleteAccountSuccess() {
        this.error = undefined
        this.success = 'Account has been deleted'
        this.accountDeleted = true;
        this.accountToBeDeleted = false;
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
