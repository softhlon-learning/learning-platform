import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

import {Course} from './course';

@Injectable()
export class CoursesService {
  courseUrl = 'http://localhost:8080/course';
  enrollmentUrl = 'http://localhost:8080/enrollment';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(
    private http: HttpClient) {
  }

  getCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(this.courseUrl).pipe();
  }

  enrollCourse(course: Course): Observable<ArrayBuffer> {
    const url = `${this.enrollmentUrl}`;
    const request = new EnrollmentRequest(course.externalId);
    return this.http.post<ArrayBuffer>(url, request, this.httpOptions).pipe();
  }
}

class EnrollmentRequest {
  enrollment: Enrollment;

  constructor(courseId: string | undefined) {
    this.enrollment = new Enrollment(courseId);
  }
}

class Enrollment {
  courseId?: string;

  constructor(courseId?: string) {
    this.courseId = courseId;
  }
}


