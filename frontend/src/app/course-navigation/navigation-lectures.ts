// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Lecture} from "../model/lecture";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

export class NavigationLectures {
    previousLecture?: Lecture;
    currentLecture: Lecture = new Lecture();
    nextLecture?: Lecture;
}
