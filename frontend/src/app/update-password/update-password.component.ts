// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute} from "@angular/router";
import {AccountsService} from '../service/accounts/accounts.service';

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'sign-up',
    templateUrl: './update-password.component.html',
    styleUrls: ['./update-password.component.css']
})
export class UpdatePasswordComponent implements OnInit {
    success: boolean = false;
    token: string | undefined;
    error: string | undefined;
    updatePasswordForm = this.formBuilder.group({
        password: ''
    });
    protected readonly environment = environment;

    constructor(
        private formBuilder: FormBuilder,
        private accountsService: AccountsService,
        private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.error = undefined;
        this.success = false;

        this.route.queryParamMap.subscribe(item => {
                if (item.has("token")) {
                    this.token = item.get("token")?.toString();
                }
            }
        );
    }

    onSubmit(): void {
        if (this.updatePasswordForm.invalid) {
            this.error = 'Please provide valid password';
            return;
        }

        const {password = ''} = this.updatePasswordForm.value;
        const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred';

        this.accountsService.updatePassword(this.token || '', password || '').subscribe({
            next: () => this.handleSuccess(),
            error: (signInError) => this.handleError(signInError, DEFAULT_ERROR_MESSAGE),
        });
    }

    private handleSuccess() {
        this.success = true;
        this.error = undefined;
    }

    private handleError(signInError: any, defaultErrorMessage: string) {
        this.error = signInError?.error?.message || defaultErrorMessage;
        setTimeout(() => {
            this.error = undefined
        }, 2000);
    }
}
