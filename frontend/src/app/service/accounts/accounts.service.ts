import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

const SIGN_IN_PATH = '/api/v1/account/auth/sign-in';
const SIGN_UP_PATH = '/api/v1/account/sign-up';
const SIGN_OUT_PATH = '/api/v1/account/auth/sign-out';
const DELETE_ACCOUNT_PATH = '/api/v1/account';
const PROFILE_PATH = '/api/v1/account/profile';
const RESET_ACCOUNT_PATH = '/api/v1/account/reset-account';
const UPDATE_PASSWORD_ACCOUNT_PATH = '/api/v1/account/update-password';

const HTTP_OPTIONS = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root',
})
export class AccountsService {
    constructor(
        private http: HttpClient) {
    }

    /**
     * Sends POST /api/v1/account/auth/sign-in request.
     * @param email User's email
     * @param password User's password
     */
    signIn(email: string, password: string): Observable<ArrayBuffer> {
        const signInRequest = new SignInRequest(
            email,
            password);

        return this.http
            .post<ArrayBuffer>(
                SIGN_IN_PATH,
                signInRequest,
                HTTP_OPTIONS)
            .pipe();
    }

    /**
     * Sends POST /api/v1/account/auth/sign-out request.
     *
     * @param name User's name
     * @param email User's email
     * @param password User's password
     */
    signUp(name: string, email: string, password: string): Observable<ArrayBuffer> {
        const signUpRequest = new SignUpRequest(
            name,
            email,
            password);

        return this.http
            .post<ArrayBuffer>(
                SIGN_UP_PATH,
                signUpRequest,
                HTTP_OPTIONS)
            .pipe();
    }

    /**
     * Sends POST /api/v1/account/auth/sign-out request.
     */
    signOut(): Observable<ArrayBuffer> {
        return this.http
            .post<ArrayBuffer>(
                SIGN_OUT_PATH,
                HTTP_OPTIONS)
            .pipe();
    }

    /**
     * Sends DELETE /api/v1/account/auth/delete request.
     */
    deleteAccount(): Observable<ArrayBuffer> {
        return this.http
            .delete<ArrayBuffer>(
                DELETE_ACCOUNT_PATH,
                HTTP_OPTIONS)
            .pipe();
    }

    /**
     * Sends GET /api/v1/account/auth/profile request.
     */
    getProfile(): Observable<Profile> {
        return this.http
            .get<Profile>(PROFILE_PATH)
            .pipe();
    }

    /**
     * Sends PUT /api/v1/account/auth/profile request.
     * @param name User's name
     */
    updateProfile(name: string): Observable<ArrayBuffer> {
        const updateProfileRequest = new UpdateProfileRequest(name);

        return this.http
            .put<ArrayBuffer>(
                PROFILE_PATH,
                updateProfileRequest)
            .pipe();
    }

    /**
     * Sends POST /api/v1/account/reset-account request.
     * @param name User's email
     */
    resetPassword(email: string): Observable<ArrayBuffer> {
        const recoverPasswordRequest = new RecoverPasswordRequest(email);

        return this.http
            .post<ArrayBuffer>(
                RESET_ACCOUNT_PATH,
                recoverPasswordRequest)
            .pipe();
    }

    /**
     * Sends POST /api/v1/account/auth/update request.
     * @param token Reset pasword request token
     * @param password User's new password
     */
    updatePassword(token: string, password: string): Observable<ArrayBuffer> {
        const updatePasswordRequest = new UpdatePasswordRequest(
            token,
            password);

        return this.http
            .post<ArrayBuffer>(
                UPDATE_PASSWORD_ACCOUNT_PATH,
                updatePasswordRequest)
            .pipe();
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

class UpdatePasswordRequest {
    token: string;
    password: string;

    constructor(token: string, password: string) {
        this.token = token;
        this.password = password;
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
