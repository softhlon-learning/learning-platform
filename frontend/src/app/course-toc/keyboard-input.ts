// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {CourseTocComponent} from "./course-toc.component";
import {HostListener, Injectable} from "@angular/core";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Injectable({
    providedIn: 'root'
})
export class KeyboardInputCourseToc {
    /**
     * React accordingly when key is pressed on course-toc page.
     * @param courseDetails CourseDetailsComponent reference
     * @param event Key pressed event
     */
    @HostListener('window:keydown', ['$event'])
    keyboardInput(courseToc: CourseTocComponent, event: any) {
        event.stopPropagation()

        if (event.code == 'KeyE') courseToc.enrollCourse();
        if (event.code == 'KeyU') courseToc.unenrollCourse();
        if (event.code == 'ArrowRight') courseToc.openCourse();
        if (event.code == 'KeyH' || event.code == 'ArrowLeft') courseToc.moveToHome();
    }
}
