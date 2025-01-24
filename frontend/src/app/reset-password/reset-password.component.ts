import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";
import {FormBuilder} from '@angular/forms';
import {PlatformService} from "../service/platform.service";

@Component({
    selector: 'sign-up',
    templateUrl: './reset-password.component.html',
    styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
    success: boolean = false;
    error: string | undefined;
    resetPasswordForm = this.formBuilder.group({
        email: ''
    });
    protected readonly environment = environment;

    constructor(
        private formBuilder: FormBuilder,
        private platformService: PlatformService) {
    }

    ngOnInit() {
        this.error = undefined;
        this.success = false;
    }

    onSubmit(): void {
        if (this.resetPasswordForm.invalid) {
            this.error = 'Please provide valid password';
            return;
        }

        const {email = ''} = this.resetPasswordForm.value;
        const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred';

        this.platformService.resetPassword(email || '').subscribe({
            next: () => this.handleSuccess(),
            error: (signInError) => this.handleError(signInError, DEFAULT_ERROR_MESSAGE),
        });
    }

    private handleSuccess() {
        this.success = true;
    }

    private handleError(signInError: any, defaultErrorMessage: string) {
        this.error = signInError?.error?.message || defaultErrorMessage;
    }
}
