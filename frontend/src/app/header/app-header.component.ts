import {Component, OnInit} from '@angular/core';
import {PlatformService} from "../service/platform.service";
import {CookieService} from "ngx-cookie-service";
import {Router} from "@angular/router";
// @ts-ignore
import version from "../../../package.json";

@Component({
    selector: 'app-header',
    templateUrl: './app-header.component.html',
    styleUrls: ['./app-header.component.css']
})
export class AppHeaderComponent implements OnInit {

    ngOnInit(): void {
    }

    constructor(
        private router: Router,
        private coursesService: PlatformService,
        private cookieService: CookieService) {
    }

    isAuthenticated(): boolean {
        return this.cookieService.get('Authenticated') === 'true';
    }

    signOut(): void {
        this.coursesService.signOut().subscribe({
                next: () => this.handleResponse(),
                error: (signInError) => this.handleResponse(),
            }
        );
    }

    isSignInUpPage() {
        return this.router.url === '/sign-in' || this.router.url === '/sign-up';
    }

    private handleResponse() {
        this.router.navigate(['/home']).then(() => {
            window.location.reload();
        })
    }

    protected readonly version = version.version;
}
