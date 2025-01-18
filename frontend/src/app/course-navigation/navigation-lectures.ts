import { Lecture } from "../course-content/lecture";

export class NavigationLectures {
    previousLecture?: Lecture;
    currentLecture: Lecture = new Lecture();
    nextLecture?: Lecture;
}
