import {Component, OnInit} from '@angular/core';
import {Course} from "./course";
import {CoursesService} from './courses.service';

@Component({
  selector: 'courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CoursesComponent implements OnInit {
  title = 'Softhlon Learning Platform';
  courses: Course[] = [];

  constructor(private coursesService: CoursesService) {
  }

  ngOnInit() {
    this.getCourses();
  }

  getCourses(): void {
    this.coursesService.getCourses()
      .subscribe(courses => (this.courses = courses));
  }
}
