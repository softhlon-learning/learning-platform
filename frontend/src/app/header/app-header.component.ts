// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

// @ts-ignore
import version from "../../../package.json"
import {Component, OnInit} from '@angular/core'
import {CookieService} from "ngx-cookie-service"
import {ActivatedRoute, NavigationStart, Router} from "@angular/router"
import {AccountsService} from '../service/accounts/accounts.service'
import {AUTHENTICATED_COOKIE, SUBSCRIPTION_COOKIE} from "../common/constants";
import {SubscriptionsService} from "../service/subscriptions/subscriptions.service"

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const FREE_TRIAL_REFRESH_DELAY = 60 * 1000;

@Component({
    selector: 'app-header',
    templateUrl: './app-header.component.html',
    styleUrls: ['./app-header.component.css'],
    standalone: false
})
export class AppHeaderComponent implements OnInit {
    freeTrialTimeLeft?: string
    freeTrialExpired = false
    stopRefresh = false
    initialized?: boolean
    protected readonly version = version.version

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private accountsService: AccountsService,
        private subscriptionsService: SubscriptionsService,
        private cookieService: CookieService) {
        this.router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                this.ngOnInit()
            }
        });
    }

    ngOnInit(): void {
        this.route.queryParamMap.subscribe(item => {
                if (item.has("stopRefresh")) {
                    this.stopRefresh = item.get("stopRefresh")?.toString() === 'true';
                }

                console.log('c: ' + this.stopRefresh)

                if (this.initialized) {
                    return;
                }
                if (!this.isAuthenticated() || this.isSubscribed()) {
                    this.initialized = true
                    return;
                }

                console.log("ngInit call")
                this.fetchFreeTrial(true)
                setInterval(() => {
                    if (this.isAuthenticated() && !this.isSubscribed()) {
                        console.log("interval call")
                        this.fetchFreeTrial()
                    }
                }, FREE_TRIAL_REFRESH_DELAY)
            }
        )
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
        return this.cookieService.get(SUBSCRIPTION_COOKIE) === 'subscribed'
    }

    /**
     * Check if user is not subscribed.
     */
    isNotSubscribed(): boolean {
        return this.cookieService.get(SUBSCRIPTION_COOKIE) === 'not_subscribed'
    }

    /**
     * Check if user has free trial.
     */
    isFreeTrial(): boolean {
        return this.cookieService.get(SUBSCRIPTION_COOKIE) === 'free_trial'
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
     * Fetch free trial info from service.
     * @param init Init flag
     * @private
     */
    private fetchFreeTrial(init: boolean = false) {
        this.initialized = true
        if (this.stopRefresh) {
            return
        }
        this.subscriptionsService.fetchFreeTrial().subscribe(
            freeTrialInfo => {
                if (freeTrialInfo.expired === false || init) {
                    this.freeTrialTimeLeft = freeTrialInfo.timeLeft
                    this.freeTrialExpired = freeTrialInfo.expired
                }

                if (freeTrialInfo.expired === true && !init) {
                    this.router.navigate(
                        ['/home'],
                        {queryParams: {stopRefresh: true}})
                        .then(() => {
                            window.location.reload()
                        })
                }
            }
        );
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
