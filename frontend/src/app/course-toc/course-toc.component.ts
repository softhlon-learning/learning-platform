import {Component, OnInit} from '@angular/core';
import {Course} from "../home/course";
import {PlatformService} from '../service/platform.service';
import {ActivatedRoute, Router} from "@angular/router";
import {CourseContent} from "../course-content/course-content";
import {CookieService} from "ngx-cookie-service";

@Component({
    selector: 'course-toc',
    templateUrl: './course-toc.component.html',
    styleUrls: ['./course-toc.component.css']
})
export class CourseTocComponent implements OnInit {
    course: Course = {};
    courseContent: CourseContent | undefined;

    constructor(
        private coursesService: PlatformService,
        private cookieService: CookieService,
        private router: Router,
        private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.getCourse();
    }

    update() {
        console.log("Updating home");
        this.coursesService.refreshCourses().subscribe(
            item => this.getCourse()
        );
    }

    getCourse(): void {
        const id = this.route.snapshot.paramMap.get('id')!;
        this.coursesService.getCourses()
            .subscribe(courses => {
                for (let i = 0; i < courses.length; i++) {
                    let course = courses[i];
                    if (course.code === id) {
                        this.course = course;
                        break;
                    }
                }
                this.courseContent = JSON.parse(atob(<string>this.course.content));
            })
    }

    enrollCourse(): void {
        this.coursesService.enrollCourse(this.course).subscribe(
            item => this.update()
        );
    }

    redirectToSignIn(): void {
        this.router.navigate(['/sign-in'])
            .then(() => {
                window.location.reload();
            });
    }

    unenrollCourse(): void {
        this.coursesService.unenrollCourse(this.course).subscribe(
            item => this.update()
        );
    }

    isAuthenticated(): boolean {
        return this.cookieService.get('Authenticated') === 'true';
    }
}
