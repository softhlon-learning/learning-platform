// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

// @ts-ignore
import version from "../../../package.json"
import {Component, OnInit} from '@angular/core'
import {CookieService} from "ngx-cookie-service"
import {Router} from "@angular/router"
import {AccountsService} from '../service/accounts/accounts.service'

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'app-header',
    templateUrl: './app-header.component.html',
    styleUrls: ['./app-header.component.css']
})
export class AppHeaderComponent implements OnInit {
    protected readonly version = version.version

    constructor(
        private router: Router,
        private accountsService: AccountsService,
        private cookieService: CookieService) {
    }

    ngOnInit(): void {
    }

    /**
     * Check if user is authenticated.
     */
    isAuthenticated(): boolean {
        return this.cookieService.get('Authenticated') === 'true'
    }

    /**
     * Sign out current user.
     */
    signOut(): void {
        this.accountsService.signOut().subscribe({
                next: () => this.handleResponse(),
                error: () => this.handleResponse(),
            }
        )
    }

    /**
     * Check if you are at /sign-in or /sign-up.
     */
    isSignInUpPage() {
        return this.router.url === '/sign-in' || this.router.url === '/sign-up'
    }

    /**
     * Handle success and eerror response, and redirect to /home.
     * @private
     */
    private handleResponse() {
        this.router.navigate(['/home']).then(() => {
            window.location.reload()
        })
    }
}
