// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {environment} from "../../environment/environment"

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'activate-account',
    templateUrl: './activate-account.component.html',
    styleUrls: ['./activate-account.component.css'],
    standalone: false
})
export class ActivateAccountComponent implements OnInit {

    protected readonly environment = environment;

    constructor() {
    }

    /**
     * Initialize page.
     */
    ngOnInit() {
    }

}
