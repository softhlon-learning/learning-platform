import { Lecture } from "../course-content/lecture";

export class NavigationItems {
    previousLecture?: Lecture;
    currentLecture: Lecture = new Lecture();
    nextLecture?: Lecture;
}
