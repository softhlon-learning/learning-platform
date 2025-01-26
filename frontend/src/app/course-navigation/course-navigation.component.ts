// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, Input, OnInit} from '@angular/core';
import {CourseNavigation} from "./course-navigation";
import {CourseDetailsComponent} from "../course-details/course-details.component";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'course-navigation',
    templateUrl: './course-navigation.component.html',
    styleUrls: ['./course-navigation.component.css']
})
export class CourseNavigationComponent implements OnInit {
    @Input()
    navigationItems: CourseNavigation = new CourseNavigation();

    @Input()
    progress?: CourseDetailsComponent;

    constructor() {
    }

    ngOnInit(): void {
    }

    moveToPreviousLecture(): void {
        if (this.progress != null) {
            this.progress.moveToPreviousLecture();
        }
    }

    moveToNextLecture(): void {
        if (this.progress != null) {
            this.progress.moveToNextLecture();
        }
    }

    markLectureAsViewed(): void {
        this.progress?.markLectureAsViewed();
    }

    markLectureAsNotViewed(): void {
        this.progress?.markLectureAsNotViewed();
    }
}
