import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {shareReplay} from 'rxjs/operators';

import {Course} from './course';

@Injectable()
export class CoursesService {
    courseUrl = '/api/v1/course';
    enrollmentUrl = '/api/v1/course/{courseId}/enrollment';
    httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json'})
    };
    private courses$?: Observable<Course[]>;

    constructor(
        private http: HttpClient) {
    }

    refreshCourses(): Observable<Course[]> {
        this.courses$ = this.http.get<Course[]>(this.courseUrl).pipe();
        return this.courses$;
    }

    getCourses(): Observable<Course[]> {
        if (!this.courses$) {
            this.courses$ = this.http.get<Course[]>(this.courseUrl).pipe(shareReplay(1));
        }
        return this.courses$;
    }

    enrollCourse(course: Course): Observable<ArrayBuffer> {
        const url = `${this.enrollmentUrl.replace('{courseId}', course.id ?? '')}`;
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


