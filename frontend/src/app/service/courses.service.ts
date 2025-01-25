import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {shareReplay} from 'rxjs/operators';

import {Course} from '../home/course';

@Injectable({
    providedIn: 'root',
})
export class CoursesService {
    private courseUrl = '/api/v1/course';
    private enrollmentUrl = '/api/v1/course/{courseId}/enrollment';
    private updateLectureUrl = '/api/v1/course/{courseId}/enrollment/lecture';
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

    updateLecture(id: string, lectureId: string, processed: boolean): Observable<ArrayBuffer> {
        const url = `${this.updateLectureUrl.replace('{courseId}', id)}`;
        const updateLectureRequest = new UpdateLectureRequest(lectureId, processed);
        return this.http.patch<ArrayBuffer>(url, updateLectureRequest).pipe();
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

class UpdateLectureRequest {
    lectureId: string;
    processed: boolean;

    constructor(lectureId: string, processed: boolean) {
        this.lectureId = lectureId;
        this.processed = processed;
    }
}

export class Profile {
    name: string;
    email: string;

    constructor(name: string, email: string) {
        this.name = name;
        this.email = email;
    }
}
