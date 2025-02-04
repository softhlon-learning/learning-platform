// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

// @ts-ignore
import version from "../../../package.json"
import {Component, OnInit} from '@angular/core'
import {CookieService} from "ngx-cookie-service"
import {Router} from "@angular/router"
import {AccountsService} from '../service/accounts/accounts.service'
import {AUTHENTICATED_COOKIE, SUBSCRIPTION_COOKIE} from "../common/constants";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'app-footer',
    templateUrl: './app-footer.component.html',
    styleUrls: ['./app-footer.component.css'],
    standalone: false
})
export class AppFooterComponent implements OnInit {
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
        return this.cookieService.get(AUTHENTICATED_COOKIE) === 'true'
    }

    /**
     * Check if user is subscribed.
     */
    isSubscribed(): boolean {
        return this.cookieService.get(SUBSCRIPTION_COOKIE) === 'true'
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
