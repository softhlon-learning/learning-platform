// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {HostListener, Injectable} from "@angular/core";
import {CourseDetailsComponent} from "./course-details.component";

@Injectable({
    providedIn: 'root'
})
export class KeyboardInputCourseDetails {
    @HostListener('window:keydown', ['$event'])
    keyboardInput(courseDetails: CourseDetailsComponent, event: any) {
        event.stopPropagation()

        if (event.code == 'ArrowDown') courseDetails.next();
        if (event.code == 'ArrowUp') courseDetails.previous()
        if (event.code == 'KeyM') courseDetails.switchLectureViewedFlag();
        if (event.code == 'ArrowLeft') courseDetails.back();
        if (event.code == 'KeyH') courseDetails.home();
        if (event.code == 'Enter') courseDetails.play();
    }
}
