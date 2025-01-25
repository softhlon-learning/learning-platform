import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

const SIGN_IN_PATH = '/api/v1/account/auth/sign-in';
const SIGN_UP_PATH = '/api/v1/account/auth/signup';
const SIGN_OUT_PATH = '/api/v1/account/auth/sign-out';
const DELETE_ACCOUNT_PATH = '/api/v1/account/auth/delete';
const PROFILE_PATH = '/api/v1/account/auth/profile';
const RESET_ACCOUNT_PATH = '/api/v1/account/reset-account';
const UPDATE_ACCOUNT_PATH = '/api/v1/account/auth/update';

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

    signOut(): Observable<ArrayBuffer> {
        return this.http
            .post<ArrayBuffer>(
                SIGN_OUT_PATH,
                HTTP_OPTIONS)
            .pipe();
    }

    /**
     * Sends PATCH /api/v1/account/auth/sign-in request.
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

    deleteAccount(): Observable<ArrayBuffer> {
        return this.http
            .delete<ArrayBuffer>(
                DELETE_ACCOUNT_PATH,
                HTTP_OPTIONS)
            .pipe();
    }

    getProfile(): Observable<Profile> {
        return this.http
            .get<Profile>(PROFILE_PATH)
            .pipe();
    }

    updateProfile(name: string): Observable<ArrayBuffer> {
        const updateProfileRequest = new UpdateProfileRequest(name);

        return this.http
            .put<ArrayBuffer>(
                PROFILE_PATH,
                updateProfileRequest)
            .pipe();
    }

    resetPassword(name: string): Observable<ArrayBuffer> {
        const recoverPasswordRequest = new RecoverPasswordRequest(name);

        return this.http
            .post<ArrayBuffer>(
                RESET_ACCOUNT_PATH,
                recoverPasswordRequest)
            .pipe();
    }

    updatePassword(token: string, password: string): Observable<ArrayBuffer> {
        const updatePasswordRequest = new UpdatePasswordRequest(
            token,
            password);

        return this.http
            .post<ArrayBuffer>(
                UPDATE_ACCOUNT_PATH,
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
