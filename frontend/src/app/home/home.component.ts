import {Component, OnInit} from '@angular/core';
import {Course} from "./course";
import {PlatformService} from '../service/platform.service';
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
    selector: 'home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    title = 'Java/Fullstack Developer Academy';
    courses?: Course[];

    constructor(
        private coursesService: PlatformService,
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
