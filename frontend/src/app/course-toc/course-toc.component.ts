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
    course?: Course
    courseContent: CourseContent | undefined

    constructor(
        private keyboardInputToc: KeyboardInputCourseToc,
        private coursesService: CoursesService,
        private cookieService: CookieService,
        private router: Router,
        private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.fetchCourses()
    }

    /**
     * Fetch course and init service cache.
     */
    fetchCourses(): void {
        const id = this.route.snapshot.paramMap.get('id')!
        this.coursesService.getCourses()
            .subscribe(courses => {
                for (let i = 0; i < courses.length; i++) {
                    let course = courses[i]
                    if (course.code === id) {
                        this.course = course
                        break
                    }
                }
                this.courseContent = JSON.parse(atob(<string>this.course?.content))
                this.coursesService.initCache(courses)
            })
    }

    /**
     * Pressed key events handler.
     * @param event Pressed key event
     */
    @HostListener('window:keydown', ['$event'])
    keyboardInput(event: any) {
        this.keyboardInputToc.keyboardInput(this, event)
    }

    /**
     * Enroll course, upadte service cache, and redirect to course details page.
     */
    enrollCourse(): void {
        if (this.course?.enrolled) {
            return
        }

        if (!this.isAuthenticated()) {
            this.redirectToSignIn()
        } else {
            this.coursesService.enrollCourse(this.course || {}).subscribe(() => {
                    // @ts-ignore
                    this.course.enrolled = true
                    this.coursesService.updateCache(this.course || {})
                    this.router.navigate(['/course/' + this.course?.code + '/details'])
                }
            )
        }
    }

    /**
     * Unenroll course and upadte service cache.
     */
    unenrollCourse(): void {
        if (!this.course?.enrolled || !this.isAuthenticated()) return
        this.coursesService.unenrollCourse(this.course || {}).subscribe(
            () => {
                // @ts-ignore
                this.course.enrolled = false
                this.coursesService.updateCache(this.course || {})
            }
        )
    }

    /**
     * Redirect to /sign-in.
     */
    redirectToSignIn(): void {
        this.cookieService.set('Redirect', '/course/' + this.course?.code)
        this.router.navigate(['/sign-in'])
            .then(() => {
                window.location.reload()
            })
    }

    /**
     * Check is user is aiuthenticated.
     */
    isAuthenticated(): boolean {
        return this.cookieService.get('Authenticated') === 'true'
    }

    /**
     * Move to /home page.
     */
    moveToHome() {
        this.router.navigate(['/home'])
    }

    /**
     * Open course details.
     */
    openCourse() {
        this.router.navigate(['/course/' + this.course?.code + '/details'])
    }
}
