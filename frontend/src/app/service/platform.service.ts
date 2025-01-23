import {Injectable} from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {shareReplay} from 'rxjs/operators';

import {Course} from '../home/course';

@Injectable({
    providedIn: 'root',
})
export class PlatformService {
    private courseUrl = '/api/v1/course';
    private enrollmentUrl = '/api/v1/course/{courseId}/enrollment';
    private updateCourseUrl = '/api/v1/course/{courseId}';
    private signOutUrl = '/api/v1/account/auth/sign-out';
    private signInUrl = '/api/v1/account/auth/sign-in';
    private signUpUrl = '/api/v1/account/sign-up';
    private deleteAccountUrl = '/api/v1/account';
    private profileUrl = '/api/v1/account/profile';
    private passwordRecoveryUrl = '/api/v1/account/password-recovery';
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

    signIn(email: string, password: string): Observable<ArrayBuffer> {
        const signInRequest = new SignInRequest(email, password);
        return this.http.post<ArrayBuffer>(this.signInUrl, signInRequest, this.httpOptions).pipe();
    }

    signUp(name: string, email: string, password: string): Observable<ArrayBuffer> {
        const signUpRequest = new SignUpRequest(name, email, password);
        return this.http.post<ArrayBuffer>(this.signUpUrl, signUpRequest, this.httpOptions).pipe();
    }

    deleteAccount(): Observable<ArrayBuffer> {
        return this.http.delete<ArrayBuffer>(this.deleteAccountUrl, this.httpOptions).pipe();
    }

    getProfile(): Observable<Profile> {
        return this.http.get<Profile>(this.profileUrl).pipe();
    }

    updateProfile(name: string): Observable<ArrayBuffer> {
        const updateProfileRequest = new UpdateProfileRequest(name);
        return this.http.put<ArrayBuffer>(this.profileUrl, updateProfileRequest).pipe();
    }

    recoverPassword(name: string): Observable<ArrayBuffer> {
        const recoverPasswordRequest = new RecoverPasswordRequest(name);
        return this.http.post<ArrayBuffer>(this.passwordRecoveryUrl, recoverPasswordRequest).pipe();
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

class SignInRequest {
    email: string;
    password: string;

    constructor(email: string, password: string) {
        this.email = email;
        this.password = password;
    }
}

class SignUpRequest {
    name: string;
    email: string;
    password: string;

    constructor(name: string, email: string, password: string) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

class UpdateProfileRequest {
    name: string;

    constructor(name: string) {
        this.name = name;
    }
}

class RecoverPasswordRequest {
    email: string;

    constructor(email: string) {
        this.email = email;
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
