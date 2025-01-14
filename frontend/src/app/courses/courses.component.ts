import {Component, OnInit} from '@angular/core';
import {Course} from "./course";
import {CoursesService} from './courses.service';
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
    selector: 'courses',
    templateUrl: './courses.component.html',
    styleUrls: ['./courses.component.css']
})
export class CoursesComponent implements OnInit {
    title = 'Softhlon Learning Platform';
    courses: Course[] = [];

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

        if (!this.cookieService.check('Authorization')) {
            this.router.navigate(['/sign-in']);
        }
    }

    getCourses(): void {
        this.coursesService.getCourses()
            .subscribe(courses => (this.courses = courses));
    }
}
