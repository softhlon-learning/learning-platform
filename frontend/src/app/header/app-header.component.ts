// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core';
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
// @ts-ignore
import version from "../../../package.json";
import {AccountsService} from '../service/accounts/accounts.service';

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'app-header',
    templateUrl: './app-header.component.html',
    styleUrls: ['./app-header.component.css']
})
export class AppHeaderComponent implements OnInit {
    protected readonly version = version.version;

    constructor(
        private router: Router,
        private accountsService: AccountsService,
        private cookieService: CookieService) {
    }

    ngOnInit(): void {
    }

    isAuthenticated(): boolean {
        return this.cookieService.get('Authenticated') === 'true';
    }

    signOut(): void {
        this.accountsService.signOut().subscribe({
                next: () => this.handleResponse(),
                error: () => this.handleResponse(),
            }
        );
    }

    isSignInUpPage() {
        return this.router.url === '/sign-in' || this.router.url === '/sign-up';
    }

    private handleResponse() {
        this.router.navigate(['/home']).then(() => {
            window.location.reload();
        })
    }
}
