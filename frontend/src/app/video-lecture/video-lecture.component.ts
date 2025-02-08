// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core'
import {CourseNavigation} from "../course-navigation/course-navigation"
import {CourseDetailsComponent} from "../course-details/course-details.component"
import {NgxSpinnerService} from "ngx-spinner";
import {ActivatedRoute, NavigationStart, Router} from "@angular/router";

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

    constructor(
        private spinner: NgxSpinnerService,
        private route: ActivatedRoute) {
    }

    ngOnInit(): void {
    }

    ngOnChanges() {
        // it's necessary to refresh video when clicking on new lecture
        if (this.video != null) {
            this.video.nativeElement.load()
        }
    }

    /**
     * Show spinner when video is loading.
     */
    showSpinner() {
        this.spinner.show()
    }

    /**
     * Hide spinner when video is loaded.
     */
    hideSpinner() {
        this.spinner.hide()
    }

}
