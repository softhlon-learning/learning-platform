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

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'not-found',
    templateUrl: './not-found.component.html',
    styleUrls: ['./not-found.component.css'],
    standalone: false
})
export class NotFoundComponent implements OnInit {
    protected readonly version = version.version

    constructor(
        private router: Router,
        private accountsService: AccountsService,
        private cookieService: CookieService) {
    }

    ngOnInit(): void {
    }
}
