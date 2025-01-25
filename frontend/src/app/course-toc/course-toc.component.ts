// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, HostListener, OnInit} from '@angular/core';
import {Course} from "../home/course";
import {ActivatedRoute, Router} from "@angular/router";
import {CourseContent} from "../model/course-content";
import {CookieService} from "ngx-cookie-service";
import {KeyboardInputCourseToc} from "./keyboard-input";
import {CoursesService} from "../service/courses/courses.service";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'course-toc',
    templateUrl: './course-toc.component.html',
    styleUrls: ['./course-toc.component.css']
})
export class CourseTocComponent implements OnInit {
    course?: Course;
    courseContent: CourseContent | undefined;

    constructor(
        private keyboardInputToc: KeyboardInputCourseToc,
        private coursesService: CoursesService,
        private cookieService: CookieService,
        private router: Router,
        private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.getCourse()
        this.coursesService.refreshCourses().subscribe(() => this.getCourse());
    }

    @HostListener('window:keydown', ['$event'])
    keyboardInput(event: any) {
        this.keyboardInputToc.keyboardInput(this, event);
    }

    update() {
        console.log("Updating home");
        this.coursesService.refreshCourses().subscribe(
            () => this.getCourse()
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
                this.courseContent = JSON.parse(atob(<string>this.course?.content));
            })
    }

    enrollCourse(): void {
        if (this.course?.enrolled) return;
        if (!this.isAuthenticated()) {
            this.redirectToSignIn();
        } else {
            this.coursesService.enrollCourse(this.course || {}).subscribe(() => {
                    this.update();
                    this.router.navigate(['/course/' + this.course?.code + '/details']);
                }
            );
        }
    }

    redirectToSignIn(): void {
        this.cookieService.set('Redirect', '/course/' + this.course?.code);
        this.router.navigate(['/sign-in'])
            .then(() => {
                window.location.reload();
            });
    }

    unenrollCourse(): void {
        if (!this.course?.enrolled || !this.isAuthenticated()) return;
        this.coursesService.unenrollCourse(this.course || {}).subscribe(
            () => this.update()
        );
    }

    isAuthenticated(): boolean {
        return this.cookieService.get('Authenticated') === 'true';
    }

    home() {
        this.router.navigate(['/home']);
    }

    open() {
        this.router.navigate(['/course/' + this.course?.code + '/details']);
    }
}
