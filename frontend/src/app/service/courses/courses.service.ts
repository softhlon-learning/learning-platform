// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Injectable} from '@angular/core'
import {HttpClient, HttpHeaders} from '@angular/common/http'
import {Observable, of} from 'rxjs'
import {Course} from '../../home/course'
import {EnrollmentRequest, UpdateLectureRequest} from './courses.model'

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const COURSE_PATH = '/api/v1/course'
const ENROLL_PATH = '/api/v1/course/{courseId}/enrollment'
const UPDATE_LECTURE_PATH = '/api/v1/course/{courseId}/enrollment/lecture'

const HTTP_OPTIONS = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
}

@Injectable({
    providedIn: 'root',
})
export class CoursesService {
    private courses?: Course[]

    constructor(
        private http: HttpClient) {
    }

    /**
     * Replays GET /api/v1/course request.
     */
    getCourses(): Observable<Course[]> {
        if (!this.courses) {
            return this.http
                .get<Course[]>(COURSE_PATH)
                .pipe()
        } else {
            // @ts-ignore
            return of(this.courses)
        }
    }

    /**
     * Init courses cache.
     * @param courses Up-to-date courses data
     */
    initCache(courses: Course[]) {
        this.courses = courses
    }

    /**
     * Update course cache
     * @param updatedCourse Course to be update
     */
    updateCache(updatedCourse: Course) {
        if (this.courses != null) {
            for (let i = 0; i < this.courses.length; i++) {
                let course = this.courses[i]
                if (course.code === updatedCourse.code) {
                    this.courses[i] = course
                    break
                }
            }
        }
    }

    /**
     * Sends POST /api/v1/course/{courseId}/enrollment request.
     * @param course Course structure
     */
    enrollCourse(course: Course): Observable<ArrayBuffer> {
        const PATH = `${ENROLL_PATH.replace('{courseId}', course.id ?? '')}`
        const request = new EnrollmentRequest(course.externalId)

        return this.http
            .post<ArrayBuffer>(
                PATH,
                request,
                HTTP_OPTIONS).pipe()
    }

    /**
     * Sends POST /api/v1/course/{courseId}/enrollment request.
     * @param course Course strcuture
     */
    unenrollCourse(course: Course): Observable<ArrayBuffer> {
        const PATH = `${ENROLL_PATH.replace('{courseId}', course.id ?? '')}`
        return this.http
            .delete<ArrayBuffer>(PATH)
            .pipe()
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

        const PATH = `${UPDATE_LECTURE_PATH.replace('{courseId}', courseId)}`
        const updateLectureRequest = new UpdateLectureRequest(
            lectureId,
            processed)

        return this.http
            .patch<ArrayBuffer>(
                PATH,
                updateLectureRequest).pipe()
    }
}
