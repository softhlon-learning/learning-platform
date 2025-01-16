import {Component, OnInit} from '@angular/core';
import {Course} from "./course";
import {HomeService} from './home.service';
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    title = 'Softhlon Learning Platform';
    courses: Course[] = [];

    constructor(
        private coursesService: HomeService,
        private cookieService: CookieService,
        private router: Router) {
    }

    get windowRef() {
        return window;
    }

    ngOnInit() {
        this.getCourses();
    }

    getCourses(): void {
        this.coursesService.getCourses()
            .subscribe(courses => (this.courses = courses));
    }
}
