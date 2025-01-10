import {Component, OnInit} from '@angular/core';
import {Course} from "../courses/course";
import {CoursesService} from '../courses/courses.service';
import {ActivatedRoute} from "@angular/router";
import {CourseContent} from "../course-content/course-content";

@Component({
  selector: 'course-details',
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
    const id = parseInt(this.route.snapshot.paramMap.get('code')!);
    this.coursesService.enrollCourse(this.course).subscribe(
      item => this.ngOnInit()
    );
  }
}
