// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
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
    styleUrls: ['./course-navigation.component.css'],
    standalone: false
})
export class CourseNavigationComponent implements OnInit {
    @Input()
    navigationItems: CourseNavigation = new CourseNavigation()

    @Input()
    progress?: CourseDetailsComponent

    constructor() {
    }

    ngOnInit(): void {
    }

    /**
     * Move to the next lecture handler.
     */
    moveToNextLecture(): void {
        if (this.progress != null) {
            this.progress.moveToNextLecture()
        }
    }

    /**
     * Move to the previous lecture handler.
     */
    moveToPreviousLecture(): void {
        if (this.progress != null) {
            this.progress.moveToPreviousLecture()
        }
    }

    /**
     * Mark lecture as viewed handler.
     */
    markLectureAsViewed(): void {
        this.progress?.markLectureAsViewed()
    }

    /**
     * MArk lecture as not viewed handler.
     */
    markLectureAsNotViewed(): void {
        this.progress?.markLectureAsNotViewed()
    }
}
