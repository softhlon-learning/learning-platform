export class EnrollmentRequest {
    enrollment: Enrollment;

    constructor(courseId: string | undefined) {
        this.enrollment = new Enrollment(courseId);
    }
}

export class UpdateLectureRequest {
    lectureId: string;
    processed: boolean;

    constructor(lectureId: string, processed: boolean) {
        this.lectureId = lectureId;
        this.processed = processed;
    }
}

class Enrollment {
    courseId?: string;

    constructor(courseId?: string) {
        this.courseId = courseId;
    }
}

