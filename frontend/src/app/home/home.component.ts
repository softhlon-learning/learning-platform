// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {Course} from "./course"
import {Router} from "@angular/router"
import {CookieService} from "ngx-cookie-service"
import {CoursesService} from '../service/courses/courses.service'
import {GO_BACK_APTH_COOKIE, REDIRECT_COOKIE} from "../common/constants";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css'],
    standalone: false
})
export class HomeComponent implements OnInit {
    title = 'Java Fullstack Academy'
    courses?: Course[]

    constructor(
        private coursesService: CoursesService,
        private cookieService: CookieService,
        private router: Router) {
    }

    /**
     * Init /home page view.
     */
    ngOnInit() {
        this.fetchCourses()
        this.cookieService.set(GO_BACK_APTH_COOKIE, this.router.url)
        const redirect = this.cookieService.get(REDIRECT_COOKIE) || ''
        if (redirect != '') {
            this.router.navigate([redirect])
            this.cookieService.delete('Redirect')
        }
    }

    /**
     * Fetch courses from the service nad init its cache.
     * @private
     */
    private fetchCourses(): void {
        this.coursesService.getCourses()
            .subscribe(courses => {
                this.courses = courses
                this.coursesService.initCache(this.courses)
            })
    }
}
