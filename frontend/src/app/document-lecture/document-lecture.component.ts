// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, Input, OnInit} from '@angular/core';
import {NavigationLectures} from "../course-navigation/navigation-lectures";
import {CourseDetailsComponent} from "../course-details/course-details.component";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'document-lecture',
    templateUrl: './document-lecture.component.html',
    styleUrls: ['./document-lecture.component.css']
})
export class DocumentLectureComponent implements OnInit {
    @Input()
    navigationItems: NavigationLectures = new NavigationLectures();

    @Input()
    progress?: CourseDetailsComponent;

    @Input()
    coursePath: string = '';

    constructor() {
    }

    ngOnInit(): void {
    }

    width() {
        return window.innerWidth;
    }
}
