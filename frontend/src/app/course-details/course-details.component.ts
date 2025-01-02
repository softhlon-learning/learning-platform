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
    const id = parseInt(this.route.snapshot.paramMap.get('id')!);
    this.coursesService.getCourses()
      .subscribe(courses => {
        this.course = courses[id - 1];
        this.courseContent = JSON.parse(atob(<string>this.course.content));
      })
  }

  enrollCourse(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!);
    this.coursesService.enrollCourse(this.course).subscribe(
      item => this.ngOnInit()
    );
  }
}
