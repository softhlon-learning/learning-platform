// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {NavigationLectures} from "../course-navigation/navigation-lectures";
import {CourseDetailsComponent} from "../course-details/course-details.component";

@Component({
    selector: 'quiz-lecture',
    templateUrl: './quiz-item.component.html',
    styleUrls: ['./quiz-item.component.css']
})
export class QuizItemComponent implements OnInit {
    @Input()
    navigationItems: NavigationLectures = new NavigationLectures();

    @Input()
    progress?: CourseDetailsComponent;

    constructor(
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
    }
}
