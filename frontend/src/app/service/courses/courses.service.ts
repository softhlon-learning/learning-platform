import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {shareReplay} from 'rxjs/operators';
import {Course} from '../../home/course';

const COURSE_PATH = '/api/v1/course';
const ENROLL_PATH = '/api/v1/course/{courseId}/enrollment';
const UPDATE_LECTURE_PATH = '/api/v1/course/{courseId}/enrollment/lecture';

const HTTP_OPTIONS = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root',
})
export class CoursesService {
    private courses$?: Observable<Course[]>;

    constructor(
        private http: HttpClient) {
    }

    refreshCourses(): Observable<Course[]> {
        this.courses$ = this.http.get<Course[]>(COURSE_PATH).pipe();
        this.courses$ = this.http.get<Course[]>(COURSE_PATH).pipe(shareReplay(1));
        return this.courses$;
    }

    getCourses(): Observable<Course[]> {
        if (!this.courses$) {
            this.courses$ = this.http.get<Course[]>(COURSE_PATH).pipe(shareReplay(1));
        }
        return this.courses$;
    }

    enrollCourse(course: Course): Observable<ArrayBuffer> {
        const url = `${ENROLL_PATH.replace('{courseId}', course.id ?? '')}`;
        const request = new EnrollmentRequest(course.externalId);
        return this.http.post<ArrayBuffer>(url, request, HTTP_OPTIONS).pipe();
    }

    unenrollCourse(course: Course): Observable<ArrayBuffer> {
        const url = `${ENROLL_PATH.replace('{courseId}', course.id ?? '')}`;
        return this.http.delete<ArrayBuffer>(url).pipe();
    }

    updateLecture(id: string, lectureId: string, processed: boolean): Observable<ArrayBuffer> {
        const url = `${UPDATE_LECTURE_PATH.replace('{courseId}', id)}`;
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
