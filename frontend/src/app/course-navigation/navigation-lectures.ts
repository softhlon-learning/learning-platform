import {Lecture} from "../model/lecture";

export class NavigationLectures {
    previousLecture?: Lecture;
    currentLecture: Lecture = new Lecture();
    nextLecture?: Lecture;
}
