import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {shareReplay} from 'rxjs/operators';
import {Course} from '../../home/course';
import {EnrollmentRequest, UpdateLectureRequest} from './courses.model';

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

    /**
     * Sends GET /api/v1/course request.
     */
    refreshCourses(): Observable<Course[]> {
        this.courses$ = this.http
            .get<Course[]>(COURSE_PATH)
            .pipe();

        this.courses$ = this.http
            .get<Course[]>(COURSE_PATH)
            .pipe(shareReplay(1));

        return this.courses$;
    }

    /**
     * Replays GET /api/v1/course request.
     */
    getCourses(): Observable<Course[]> {
        if (!this.courses$) {
            this.courses$ = this.http
                .get<Course[]>(COURSE_PATH)
                .pipe(shareReplay(1));
        }
        return this.courses$;
    }

    /**
     * Sends POST /api/v1/course/{courseId}/enrollment request.
     * @param course Course structure
     */
    enrollCourse(course: Course): Observable<ArrayBuffer> {

        const PATH = `${ENROLL_PATH.replace('{courseId}', course.id ?? '')}`;
        const request = new EnrollmentRequest(course.externalId);

        return this.http
            .post<ArrayBuffer>(
                PATH,
                request,
                HTTP_OPTIONS).pipe();
    }

    /**
     * Sends POST /api/v1/course/{courseId}/enrollment request.
     * @param course Course strcuture
     */
    unenrollCourse(course: Course): Observable<ArrayBuffer> {

        const PATH = `${ENROLL_PATH.replace('{courseId}', course.id ?? '')}`;
        return this.http
            .delete<ArrayBuffer>(PATH)
            .pipe();
    }

    /**
     * Sends /api/v1/course/{courseId}/enrollment/lecture'request.
     * @param courseId Course id
     * @param lectureId Lecture id
     * @param processed Lecture processed flag
     */
    updateLecture(
        courseId: string,
        lectureId: string,
        processed: boolean): Observable<ArrayBuffer> {

        const PATH = `${UPDATE_LECTURE_PATH.replace('{courseId}', courseId)}`;
        const updateLectureRequest = new UpdateLectureRequest(
            lectureId,
            processed);

        return this.http
            .patch<ArrayBuffer>(
                PATH,
                updateLectureRequest).pipe();
    }
}
