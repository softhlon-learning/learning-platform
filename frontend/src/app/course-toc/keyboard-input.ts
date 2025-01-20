import {CourseTocComponent} from "./course-toc.component";
import {HostListener, Injectable} from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class KeyboardInputCourseToc {
    @HostListener('window:keydown', ['$event'])
    keyboardInput(courseToc: CourseTocComponent, event: any) {
        event.stopPropagation()

        if (event.code == 'KeyE') {
            courseToc.enrollCourse();
        }

        if (event.code == 'KeyU') {
            courseToc.unenrollCourse();
        }

        if (event.code == 'ArrowRight') {
            courseToc.open();
        }

        if (event.code == 'KeyH') {
            courseToc.home();
        }
    }
}
