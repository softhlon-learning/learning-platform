import {Component, OnInit} from '@angular/core';
import {environment} from "../../environment/environment";
import {PlatformService, Profile} from "../service/platform.service";
import {Router} from "@angular/router";
import {FormBuilder} from "@angular/forms";

@Component({
    selector: 'profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
    error: string | undefined;
    success: string | undefined;
    profile?: Profile;
    profileForm = this.formBuilder.group({
        name: ''
    });
    protected readonly environment = environment;

    constructor(
        private formBuilder: FormBuilder,
        private platformService: PlatformService,
        private router: Router) {
    }

    ngOnInit() {
        this.error = undefined;
        this.getProfile();
    }

    onSubmit(): void {
        if (this.profileForm.invalid) {
            console.log('Form is invalid');
            this.error = 'Please provide valid name';
            return;
        }
        console.log('Form is valid');
        const {name = ''} = this.profileForm.value;
        const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred';

        this.platformService.updateProfile(name || '').subscribe({
            next: () => this.handleSuccess(),
            error: (signInError) => this.handleError(signInError, DEFAULT_ERROR_MESSAGE),
        });
    }

    deleteAccount(): void {
        const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred';
        this.platformService.deleteAccount().subscribe({
            next: () => this.handleSuccess(),
            error: (signInError) => this.handleError(signInError, DEFAULT_ERROR_MESSAGE),
        })
    }

    getProfile(): void {
        this.platformService.getProfile()
            .subscribe(profile => {
                this.profileForm.setValue({name: profile.name});
                this.profile = profile;
            });
    }

    private handleSuccess() {
        this.error = undefined;
        this.success = 'Profile successfully saved';
    }

    private handleError(signInError: any, defaultErrorMessage: string) {
        this.success = undefined;
        this.error = signInError?.error?.message || defaultErrorMessage;
    }
}
