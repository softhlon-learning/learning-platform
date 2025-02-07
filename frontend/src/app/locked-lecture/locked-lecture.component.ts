// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, Input, OnInit} from '@angular/core'
import {CourseNavigation} from "../course-navigation/course-navigation"
import {CourseDetailsComponent} from "../course-details/course-details.component"
import {FormBuilder} from "@angular/forms";
import {AccountsService} from "../service/accounts/accounts.service";
import {ActivatedRoute, Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {GO_BACK_APTH_COOKIE} from "../common/constants";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'locked-lecture',
    templateUrl: './locked-lecture.component.html',
    styleUrls: ['./locked-lecture.component.css'],
    standalone: false
})
export class LockedLectureComponent implements OnInit {
    @Input()
    navigationItems: CourseNavigation = new CourseNavigation()

    @Input()
    progress?: CourseDetailsComponent

    @Input()
    coursePath: string = ''

    constructor(
        private cookieService: CookieService,
        private router: Router) {
    }

    ngOnInit(): void {
        this.cookieService.set(GO_BACK_APTH_COOKIE, this.router.url)
    }
}
