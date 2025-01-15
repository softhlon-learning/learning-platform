import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";
import {Router} from "@angular/router";

@Component({
    selector: 'sign-in',
    templateUrl: './sign-in.component.html',
    styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
    protected readonly environment = environment;

    constructor() {
    }

    ngOnInit() {
        const button = document.querySelector("#g_id_onload") as HTMLElement | null;
        button?.setAttribute("data-client_id", environment.googleClientId);
        button?.setAttribute("data-login_uri", environment.loginUri);
    }
}
