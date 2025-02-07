// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {environment} from "../../environment/environment"
import {ActivatedRoute} from "@angular/router";
import {AccountsService} from "../service/accounts/accounts.service";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const TOKEN_QUERY_PARAM = 'token'
const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred'
const HIDE_ERROR_DELAY = 2000

@Component({
    selector: 'activate-account',
    templateUrl: './activate-account.component.html',
    styleUrls: ['./activate-account.component.css'],
    standalone: false
})
export class ActivateAccountComponent implements OnInit {
    success: boolean = false
    error: string | undefined

    protected readonly environment = environment;

    constructor(
        private route: ActivatedRoute,
        private accountsService: AccountsService) {
    }

    /**
     * Initialize page.
     */
    ngOnInit() {
        this.route.queryParamMap.subscribe(
            paramMap => {
                if (paramMap.has(TOKEN_QUERY_PARAM)) {
                    const token = paramMap.get(TOKEN_QUERY_PARAM)?.toString()
                    this.accountsService.activateAccount(token).subscribe({
                        next: () => this.handleSuccess(),
                        error: (error) => this.handleError(error, DEFAULT_ERROR_MESSAGE),
                    })
                }
            }
        )
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
