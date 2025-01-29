// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strongly encouraged.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, OnInit} from '@angular/core'
import {environment} from "../../environment/environment"
import {FormBuilder} from '@angular/forms'
import {AccountsService} from '../service/accounts/accounts.service'

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const HIDE_ERROR_DELAY = 2000
const DEFAULT_ERROR_MESSAGE = 'An unexpected error occurred'

@Component({
    selector: 'sign-up',
    templateUrl: './subscribe.component.html',
    styleUrls: ['./subscribe.component.css'],
    standalone: false
})
export class SubscribeComponent implements OnInit {
    success: boolean = false
    error: string | undefined

    protected readonly environment = environment

    constructor() {
    }

    /**
     * Init page.
     */
    ngOnInit() {
        this.error = undefined
        this.success = false
    }
}
