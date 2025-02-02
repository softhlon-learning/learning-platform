// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core'
import {CourseNavigation} from "../course-navigation/course-navigation"
import {CourseDetailsComponent} from "../course-details/course-details.component"

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'video-lecture',
    templateUrl: './video-lecture.component.html',
    styleUrls: ['./video-lecture.component.css'],
    standalone: false
})
export class VideoLectureComponent implements OnInit {
    @ViewChild('video') video?: ElementRef

    @Input()
    navigationItems: CourseNavigation = new CourseNavigation()

    @Input()
    progress?: CourseDetailsComponent

    @Input()
    coursePath?: string

    constructor() {
    }

    ngOnInit(): void {
    }

    ngOnChanges() {
        // it's necessary to refresh video when clicking on new lecture
        if (this.video != null) {
            this.video.nativeElement.load()
        }
    }
}
