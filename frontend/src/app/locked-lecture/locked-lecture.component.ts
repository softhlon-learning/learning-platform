// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, Input, OnInit} from '@angular/core'
import {CourseNavigation} from "../course-navigation/course-navigation"
import {CourseDetailsComponent} from "../course-details/course-details.component"

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'locked-lecture',
    templateUrl: './locked-lecture.component.html',
    styleUrls: ['./locked-lecture.component.css'],
    standalone: false
})
export class LockedLectureComponent implements OnInit {
    @Input()
    navigationItems: CourseNavigation = new CourseNavigation()

    @Input()
    progress?: CourseDetailsComponent

    @Input()
    coursePath: string = ''

    constructor() {
    }

    ngOnInit(): void {
    }
}
