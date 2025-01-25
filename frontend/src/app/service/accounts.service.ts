import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class AccountsService {
    private signOutUrl = '/api/v1/account/auth/sign-out';
    private signInUrl = '/api/v1/account/auth/sign-in';
    private signUpUrl = '/api/v1/account/sign-up';
    private deleteAccountUrl = '/api/v1/account';
    private profileUrl = '/api/v1/account/profile';
    private resetPassworUrl = '/api/v1/account/reset-password';
    private updatePassworUrl = '/api/v1/account/update-password';

    private httpOptions = {
        headers: new HttpHeaders({'Content-Type': 'application/json'})
    };

    constructor(
        private http: HttpClient) {
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

    resetPassword(name: string): Observable<ArrayBuffer> {
        const recoverPasswordRequest = new RecoverPasswordRequest(name);
        return this.http.post<ArrayBuffer>(this.resetPassworUrl, recoverPasswordRequest).pipe();
    }

    updatePassword(token: string, password: string): Observable<ArrayBuffer> {
        const updatePasswordRequest = new UpdatePasswordRequest(token, password);
        return this.http.post<ArrayBuffer>(this.updatePassworUrl, updatePasswordRequest).pipe();
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
