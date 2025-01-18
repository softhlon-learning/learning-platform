import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";
import {FormBuilder} from '@angular/forms';
import {PlatformService} from "../service/platform.service";
import {Router} from "@angular/router";

@Component({
    selector: 'profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    protected readonly environment = environment;
    error: string | undefined;


    constructor(
        private platformService: PlatformService,
        private router: Router) {
    }

    ngOnInit() {
        this.error = undefined;
    }

    deleteAccount(): void {
        const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred';
        this.platformService.deleteAccount().subscribe({
            next: () => this.handleSuccess(),
            error: (signInError) => this.handleError(signInError, DEFAULT_ERROR_MESSAGE),
        })
    }

    private handleSuccess() {
        this.router.navigate(['/home'])
            .then(() => {
                window.location.reload();
            });
    }

    private handleError(signInError: any, defaultErrorMessage: string) {
        this.error = signInError?.error?.message || defaultErrorMessage;
    }
}
