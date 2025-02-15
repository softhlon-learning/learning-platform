// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, Input, OnInit} from '@angular/core'
import {NgxSpinnerService} from "ngx-spinner";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'document-generic',
    templateUrl: './document-generic.component.html',
    styleUrls: ['./document-generic.component.css'],
    standalone: false
})
export class DocumentGenericComponent implements OnInit {
    spinnerShown = false;
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
     * Show spinner when document is loading.
     */
    showSpinner() {
        this.spinner.show()
    }

    /**
     * Hide or show spinner.
     */
    switchSpinner() {
        if (this.spinnerShown === true) {
            this.spinner.hide()
            this.spinnerShown = false;
        } else {
            this.spinnerShown = true;
            this.spinner.show();
        }
    }
}
