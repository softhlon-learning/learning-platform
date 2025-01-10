import {Component, OnInit} from '@angular/core';
import {Course} from "../courses/course";
import {CoursesService} from '../courses/courses.service';
import {ActivatedRoute} from "@angular/router";
import {CourseContent} from "../course-content/course-content";

@Component({
    selector: 'course-toc',
    templateUrl: './course-details.component.html',
    styleUrls: ['./course-details.component.css']
})
export class CourseDetailsComponent implements OnInit {
    course: Course = {};
    courseContent: CourseContent | undefined;

    constructor(
        private coursesService: CoursesService,
        private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.getCourse();
    }

    update() {
        console.log("Updating courses");
        this.coursesService.refreshCourses().subscribe();
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
}
