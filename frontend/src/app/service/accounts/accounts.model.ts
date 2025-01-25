// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

export class SignInRequest {
    email: string;
    password: string;

    constructor(email: string, password: string) {
        this.email = email;
        this.password = password;
    }
}

export class SignUpRequest {
    name: string;
    email: string;
    password: string;

    constructor(name: string, email: string, password: string) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

export class UpdateProfileRequest {
    name: string;

    constructor(name: string) {
        this.name = name;
    }
}

export class RecoverPasswordRequest {
    email: string;

    constructor(email: string) {
        this.email = email;
    }
}

export class UpdatePasswordRequest {
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
