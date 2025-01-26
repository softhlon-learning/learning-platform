// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

/**
 * Create enrollment request body.
 */
export class EnrollmentRequest {
    enrollment: Enrollment

    constructor(courseId: string | undefined) {
        this.enrollment = new Enrollment(courseId)
    }
}

/**
 * Update lecture request body
 */
export class UpdateLectureRequest {
    lectureId: string
    processed: boolean

    constructor(lectureId: string, processed: boolean) {
        this.lectureId = lectureId
        this.processed = processed
    }
}

/**
 * Enrollment request model.
 */
class Enrollment {
    courseId?: string

    constructor(courseId?: string) {
        this.courseId = courseId
    }
}

