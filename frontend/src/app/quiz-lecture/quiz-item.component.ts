// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, Input, OnInit} from '@angular/core'
import {ActivatedRoute} from "@angular/router"
import {CourseNavigation} from "../course-navigation/course-navigation"
import {CourseDetailsComponent} from "../course-details/course-details.component"

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'quiz-lecture',
    templateUrl: './quiz-item.component.html',
    styleUrls: ['./quiz-item.component.css'],
    standalone: false
})
export class QuizItemComponent implements OnInit {
    @Input()
    navigationItems: CourseNavigation = new CourseNavigation()

    @Input()
    progress?: CourseDetailsComponent

    constructor(
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
    }
}
