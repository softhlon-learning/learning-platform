import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";
import {FormBuilder} from '@angular/forms';
import {PlatformService} from "../service/platform.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'sign-in',
    templateUrl: './sign-in.component.html',
    styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
    error: string | undefined;
    signInForm = this.formBuilder.group({
        email: '',
        password: ''
    });
    protected readonly environment = environment;

    constructor(
        private formBuilder: FormBuilder,
        private platformService: PlatformService,
        private route: ActivatedRoute,
        private router: Router) {
    }

    ngOnInit() {
        this.error = undefined;
        const button = document.querySelector("#g_id_onload") as HTMLElement | null;
        button?.setAttribute("data-client_id", environment.googleClientId);
        button?.setAttribute("data-login_uri", environment.loginUri);

        this.route.queryParamMap.subscribe(item => {
                console.log(item.get("error"));
                if (item.has("error")) {
                    this.error = item.get("error")?.toString();
                }
            }
        );
    }

    onSubmit(): void {
        if (this.signInForm.invalid) {
            console.log('Form is invalid');
            this.error = 'Please provide valid email and password';
            return;
        }

        const {email = '', password = ''} = this.signInForm.value;
        const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred';

        this.platformService.signIn(email || '', password || '').subscribe({
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
        console.log(signInError);
        if (signInError?.status === 401) {
            this.error = signInError?.error?.message || defaultErrorMessage;
        } else {
            this.error = defaultErrorMessage;
        }
    }
}
