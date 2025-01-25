// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core';
import {Course} from "./course";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {CoursesService} from '../service/courses/courses.service';

@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    title = 'Java/Fullstack Developer Academy';
    courses?: Course[];

    constructor(
        private coursesService: CoursesService,
        private cookieService: CookieService,
        private router: Router) {
    }

    get windowRef() {
        return window;
    }

    ngOnInit() {
        this.getCourses();
        const redirect = this.cookieService.get('Redirect') || '';
        if (redirect != '') {
            this.router.navigate([redirect]);
            this.cookieService.delete('Redirect');
        }
    }

    getCourses(): void {
        this.coursesService.getCourses()
            .subscribe(courses => (this.courses = courses));
    }
}
