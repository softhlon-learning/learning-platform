// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

package tech.softhlon.learning.courses.gateway;

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

class RestResources {
    static final String API_PREFIX = "/api/v1";
    static final String COURSE = API_PREFIX + "/course";
    static final String ENROLL_COURSE = COURSE + "/{courseId}/enrollment";
    static final String UNENROLL_COURSE = COURSE + "/{courseId}/enrollment";
    static final String GET_COURSE = COURSE + "/{courseId}";
    static final String LIST_COURSES = COURSE;
    static final String UPDATE_COURSE = COURSE + "/{courseId}";
}
