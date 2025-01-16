import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";
import { FormBuilder } from '@angular/forms';

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

    constructor(private formBuilder: FormBuilder) {
    }

    ngOnInit() {
        const button = document.querySelector("#g_id_onload") as HTMLElement | null;
        button?.setAttribute("data-client_id", environment.googleClientId);
        button?.setAttribute("data-login_uri", environment.loginUri);
    }

    onSubmit(): void {
        console.warn('Your order has been submitted', this.signInForm.value);
    }
}
