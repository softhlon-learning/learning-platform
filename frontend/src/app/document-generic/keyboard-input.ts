// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {HostListener, Injectable} from "@angular/core";
import {DocumentGenericComponent} from "./document-generic.component";

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
    keyboardInput(courseToc: DocumentGenericComponent, event: any) {
        event.stopPropagation()
        if (event.code == 'KeyH') courseToc.moveToHome();
    }
}
