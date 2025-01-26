// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {HostListener, Injectable} from "@angular/core";
import {CourseDetailsComponent} from "./course-details.component";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Injectable({
    providedIn: 'root'
})
export class KeyboardInputCourseDetails {
    /**
     * React accordingly when key is pressed on course-details page.
     * @param courseDetails CourseDetailsComponent reference
     * @param event Key pressed event
     */
    @HostListener('window:keydown', ['$event'])
    keyboardInput(courseDetails: CourseDetailsComponent, event: any) {
        event.stopPropagation()

        if (event.code == 'ArrowDown') courseDetails.moveToNextLecture()
        if (event.code == 'ArrowUp') courseDetails.moveToPreviousLecture()
        if (event.code == 'KeyM') courseDetails.switchLectureViewedFlag()
        if (event.code == 'ArrowLeft') courseDetails.moveBack()
        if (event.code == 'KeyH') courseDetails.moveToHome()
        if (event.code == 'Enter') courseDetails.playVideo()
    }
}
