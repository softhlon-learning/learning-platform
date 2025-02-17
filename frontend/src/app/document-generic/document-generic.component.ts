// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, HostListener, Input, OnInit} from '@angular/core'
import {NgxSpinnerService} from "ngx-spinner";
import {Router} from "@angular/router";
import { KeyboardInputCourseToc } from './keyboard-input';

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const HIDE_ERROR_DELAY = 1500

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
        private keyboardInputToc: KeyboardInputCourseToc,
        private router: Router,
        private spinner: NgxSpinnerService) {
    }

    ngOnInit(): void {
        this.showSpinner()
    }

    /**
     * Pressed key events handler.
     * @param event Pressed key event
     */
    @HostListener('window:keydown', ['$event'])
    keyboardInput(event: any) {
        this.keyboardInputToc.keyboardInput(this, event)
    }

    /**
     * Show spinner when document is loading.
     */
    showSpinner() {

        this.spinner.show()
        setTimeout(() => {
            this.spinner.hide()
        }, HIDE_ERROR_DELAY)
    }

    /**
     * Move to /home page.
     */
    moveToHome() {
        this.router.navigate(['/home'])
    }
}
