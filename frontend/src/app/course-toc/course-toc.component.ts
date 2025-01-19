import {Component, HostListener, OnInit} from '@angular/core';
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
    course?: Course;
    courseContent: CourseContent | undefined;

    constructor(
        private coursesService: PlatformService,
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
        event.stopPropagation()

        if (event.code == 'KeyE') {
            this.enrollCourse();
        }

        if (event.code == 'KeyU') {
            this.unenrollCourse();
        }

        if (event.code == 'ArrowRight') {
            this.open();
        }

        if (event.code == 'KeyH') {
            this.home();
        }
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
                this.courseContent = JSON.parse(atob(<string>this.course?.content));
            })
    }

    enrollCourse(): void {
        if (this.course?.enrolled) return;
        if (!this.isAuthenticated()) {
            this.redirectToSignIn();
        } else {
            this.coursesService.enrollCourse(this.course || {}).subscribe(item => {
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
            item => this.update()
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
