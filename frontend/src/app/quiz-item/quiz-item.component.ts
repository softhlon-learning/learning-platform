import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NavigationLectures} from "../course-navigation/navigation-lectures";
import {CourseProgressComponent} from "../course-progress/course-progress.component";

@Component({
    selector: 'quiz-item',
    templateUrl: './quiz-item.component.html',
    styleUrls: ['./quiz-item.component.css']
})
export class QuizItemComponent implements OnInit {
    @Input()
    navigationItems: NavigationLectures = new NavigationLectures();

    @Input()
    progress?: CourseProgressComponent;

    constructor(
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
    }
}
