// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {NavigationLectures} from "../course-navigation/navigation-lectures";
import {CourseDetailsComponent} from "../course-details/course-details.component";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'video-lecture',
    templateUrl: './video-lecture.component.html',
    styleUrls: ['./video-lecture.component.css']
})
export class VideoLectureComponent implements OnInit {
    @ViewChild('video') video?: ElementRef;

    @Input()
    navigationItems: NavigationLectures = new NavigationLectures();

    @Input()
    progress?: CourseDetailsComponent;

    @Input()
    coursePath?: string;

    constructor() {
    }

    ngOnInit(): void {
    }

    ngOnChanges() {
        // it's necessary to refresh video when clicking on new lecture
        if (this.video != null) {
            this.video.nativeElement.load();
        }
    }
}
