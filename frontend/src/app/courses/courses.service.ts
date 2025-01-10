import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {shareReplay} from 'rxjs/operators';

import {Course} from './course';

@Injectable()
export class CoursesService {
    private courseUrl = '/api/v1/course';
    private enrollmentUrl = '/api/v1/course/{courseId}/enrollment';
    private courses$?: Observable<Course[]>;
    private httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json'})
    };

    constructor(
        private http: HttpClient) {
    }

    refreshCourses(): Observable<Course[]> {
        this.courses$ = this.http.get<Course[]>(this.courseUrl).pipe();
        this.courses$ = this.http.get<Course[]>(this.courseUrl).pipe(shareReplay(1));
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

    unenrollCourse(course: Course): Observable<ArrayBuffer> {
        const url = `${this.enrollmentUrl.replace('{courseId}', course.id ?? '')}`;
        return this.http.delete<ArrayBuffer>(url).pipe();
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


