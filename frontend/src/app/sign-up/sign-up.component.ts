import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";
import {FormBuilder} from '@angular/forms';
import {PlatformService} from "../service/platform.service";
import {Router} from "@angular/router";

@Component({
    selector: 'sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
    protected readonly environment = environment;
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
        const button = document.querySelector("#g_id_onload") as HTMLElement | null;
        button?.setAttribute("data-client_id", environment.googleClientId);
        button?.setAttribute("data-login_uri", environment.loginUri);
    }

    onSubmit(): void {
        if (this.signInForm.invalid) {
            console.log('Form is invalid');
            this.error = 'Please provide valid email and password';
            return;
        }

        const {name = '', email = '', password = ''} = this.signInForm.value;
        const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred';

        this.platformService.signUp(name || '', email || '', password || '').subscribe({
            next: () => this.handleSignInSuccess(),
            error: (signInError) => this.handleSignInError(signInError, DEFAULT_ERROR_MESSAGE),
        });
    }

    private handleSignInSuccess() {
        this.router.navigate(['/home'])
            .then(() => {
                window.location.reload();
            });
    }

    private handleSignInError(signInError: any, defaultErrorMessage: string) {
        if (signInError?.status === 401) {
            this.error = signInError?.error || defaultErrorMessage;
        } else {
            this.error = defaultErrorMessage;
        }
    }
}
