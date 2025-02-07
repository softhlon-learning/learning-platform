// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Lecture} from "../model/lecture";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

export class CourseNavigation {
    previousLecture?: Lecture;
    currentLecture: Lecture = new Lecture();
    nextLecture?: Lecture;
}
