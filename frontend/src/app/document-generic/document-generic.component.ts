// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, Input, OnInit} from '@angular/core'
import {CourseNavigation} from "../course-navigation/course-navigation"
import {CourseDetailsComponent} from "../course-details/course-details.component"
import {NgxSpinnerService} from "ngx-spinner";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'document-lecture',
    templateUrl: './document-generic.component.html',
    styleUrls: ['./document-generic.component.css'],
    standalone: false
})
export class DocumentGenericComponent implements OnInit {
    @Input()
    documentPath: string = ''
    protected readonly alert = alert;

    constructor(
        private spinner: NgxSpinnerService,) {
    }

    ngOnInit(): void {
        this.showSpinner()
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
