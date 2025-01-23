import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";
import {FormBuilder} from '@angular/forms';
import {PlatformService} from "../service/platform.service";
import {Router} from "@angular/router";

@Component({
    selector: 'sign-up',
    templateUrl: './password-recovery.component.html',
    styleUrls: ['./password-recovery.component.css']
})
export class PasswordRecoveryComponent implements OnInit {
    protected readonly environment = environment;
    success: boolean = false;
    error: string | undefined;

    signInForm = this.formBuilder.group({
        name: '',
        email: '',
        password: ''
    });

    constructor(
        private formBuilder: FormBuilder,
        private platformService: PlatformService,
        private router: Router) {
    }

    ngOnInit() {
        this.error = undefined;
        this.success = false;
    }

    onSubmit(): void {
        if (this.signInForm.invalid) {
            console.log('Form is invalid');
            this.error = 'Please provide valid email and password';
            return;
        }

        const {email = ''} = this.signInForm.value;
        const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred';

        this.platformService.recoverPassword(email || '').subscribe({
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
