import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";
import {FormBuilder} from '@angular/forms';
import {PlatformService} from "../service/platform.service";

@Component({
    selector: 'sign-in',
    templateUrl: './sign-in.component.html',
    styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
    protected readonly environment = environment;

    signInForm = this.formBuilder.group({
        email: '',
        password: ''
    });

    constructor(
        private formBuilder: FormBuilder,
        private platformService: PlatformService,) {
    }

    ngOnInit() {
        const button = document.querySelector("#g_id_onload") as HTMLElement | null;
        button?.setAttribute("data-client_id", environment.googleClientId);
        button?.setAttribute("data-login_uri", environment.loginUri);
    }

    onSubmit(): void {
        this.platformService.signIn(
            this.signInForm.value.email ?? '',
            this.signInForm.value.password ?? '')
            .subscribe();
    }
}
