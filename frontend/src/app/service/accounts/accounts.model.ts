// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Sing In request body.
 */
export class SignInRequest {
    email: string
    password: string

    constructor(email: string, password: string) {
        this.email = email
        this.password = password
    }
}

/**
 * Sign Up request body.
 */
export class SignUpRequest {
    name: string
    email: string
    password: string

    constructor(name: string, email: string, password: string) {
        this.name = name
        this.email = email
        this.password = password
    }
}

/**
 * Update profile request body.
 */
export class UpdateProfileRequest {
    name: string

    constructor(name: string) {
        this.name = name
    }
}

/**
 * Reset password request body.
 */
export class ResetPasswordRequest {
    email: string

    constructor(email: string) {
        this.email = email
    }
}

/**
 * Update password request body.
 */
export class UpdatePasswordRequest {
    token?: string
    password?: string

    constructor(token?: string, password?: string) {
        this.token = token
        this.password = password
    }
}

/**
 * Activate account request body.
 */
export class ActivateAccountRequest {
    token?: string

    constructor(token?: string) {
        this.token = token;
    }
}

/**
 * Profile response model.
 */
export class Profile {
    name: string
    email: string

    constructor(name: string, email: string) {
        this.name = name
        this.email = email
    }
}
