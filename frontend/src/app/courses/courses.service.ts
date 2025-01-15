import {Injectable} from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {shareReplay} from 'rxjs/operators';

import {Course} from './course';

@Injectable({
    providedIn: 'root',
})
export class CoursesService {
    private courseUrl = '/api/v1/course';
    private enrollmentUrl = '/api/v1/course/{courseId}/enrollment';
    private updateCourseUrl = '/api/v1/course/{courseId}';
    private signOutUrl = '/api/v1/account/auth/sign-out';
    private courses$?: Observable<Course[]>;
    private httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json'})
    };

    constructor(
        private http: HttpClient,
        private cookieService: CookieService) {
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

    updateCourse(id: string, content: string): Observable<ArrayBuffer> {
        const url = `${this.updateCourseUrl.replace('{courseId}', id)}`;
        const request = new UpdateCourseRequest(content);
        return this.http.patch<ArrayBuffer>(url, request, this.httpOptions).pipe();
    }

    signOut(): Observable<ArrayBuffer> {
        return this.http.post<ArrayBuffer>(this.signOutUrl, this.httpOptions).pipe();
    }
}

class EnrollmentRequest {
    enrollment: Enrollment;

    constructor(courseId: string | undefined) {
        this.enrollment = new Enrollment(courseId);
    }
}

class UpdateCourseRequest {
    content?: string;

    constructor(content?: string) {
        this.content = content;
    }
}

class Enrollment {
    courseId?: string;

    constructor(courseId?: string) {
        this.courseId = courseId;
    }
}


