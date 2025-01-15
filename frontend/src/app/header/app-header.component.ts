import {Component, OnInit} from '@angular/core';
import {CoursesService} from "../courses/courses.service";
import {CookieService} from "ngx-cookie-service";

@Component({
    selector: 'app-header',
    templateUrl: './app-header.component.html',
    styleUrls: ['./app-header.component.css']
})
export class AppHeaderComponent implements OnInit {

    ngOnInit(): void {
    }

    constructor(
        private coursesService: CoursesService,
        private cookieService: CookieService) {
    }

    isAuthenticated(): boolean {
        return this.cookieService.get('Authenticated') === 'true';
    }

    signOut(): void {
        this.coursesService.signOut().subscribe();
    }
}
