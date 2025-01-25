// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {CourseTocComponent} from "./course-toc.component";
import {HostListener, Injectable} from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class KeyboardInputCourseToc {
    @HostListener('window:keydown', ['$event'])
    keyboardInput(courseToc: CourseTocComponent, event: any) {
        event.stopPropagation()

        if (event.code == 'KeyE') courseToc.enrollCourse();
        if (event.code == 'KeyU') courseToc.unenrollCourse();
        if (event.code == 'ArrowRight') courseToc.open();
        if (event.code == 'KeyH' || event.code == 'ArrowLeft') courseToc.home();
    }
}
