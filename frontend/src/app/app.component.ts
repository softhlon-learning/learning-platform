// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {Component, enableProdMode} from '@angular/core'
import {
    NgcCookieConsentService,
    NgcInitializationErrorEvent,
    NgcInitializingEvent,
    NgcNoCookieLawEvent,
    NgcStatusChangeEvent
} from 'ngx-cookieconsent';
import {Subscription} from 'rxjs';

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
    standalone: false
})
export class AppComponent {
    private popupOpenSubscription!: Subscription;
    private popupCloseSubscription!: Subscription;
    private initializingSubscription!: Subscription;
    private initializedSubscription!: Subscription;
    private initializationErrorSubscription!: Subscription;
    private statusChangeSubscription!: Subscription;
    private revokeChoiceSubscription!: Subscription;
    private noCookieLawSubscription!: Subscription;

    constructor(private ccService: NgcCookieConsentService) {
    }

    ngOnInit() {
        // subscribe to cookieconsent observables to react to main events
        this.popupOpenSubscription = this.ccService.popupOpen$.subscribe(
            () => {
                // you can use this.ccService.getConfig() to do stuff...
            });

        this.popupCloseSubscription = this.ccService.popupClose$.subscribe(
            () => {
                // you can use this.ccService.getConfig() to do stuff...
            });

        this.initializingSubscription = this.ccService.initializing$.subscribe(
            (event: NgcInitializingEvent) => {
                // the cookieconsent is initilializing... Not yet safe to call methods like `NgcCookieConsentService.hasAnswered()`
                console.log(`initializing: ${JSON.stringify(event)}`);
            });

        this.initializedSubscription = this.ccService.initialized$.subscribe(
            () => {
                // the cookieconsent has been successfully initialized.
                // It's now safe to use methods on NgcCookieConsentService that require it, like `hasAnswered()` for eg...
                console.log(`initialized: ${JSON.stringify(event)}`);
            });

        this.initializationErrorSubscription = this.ccService.initializationError$.subscribe(
            (event: NgcInitializationErrorEvent) => {
                // the cookieconsent has failed to initialize...
                console.log(`initializationError: ${JSON.stringify(event.error?.message)}`);
            });

        this.statusChangeSubscription = this.ccService.statusChange$.subscribe(
            (event: NgcStatusChangeEvent) => {
                // you can use this.ccService.getConfig() to do stuff...
            });

        this.revokeChoiceSubscription = this.ccService.revokeChoice$.subscribe(
            () => {
                // you can use this.ccService.getConfig() to do stuff...
            });

        this.noCookieLawSubscription = this.ccService.noCookieLaw$.subscribe(
            (event: NgcNoCookieLawEvent) => {
                // you can use this.ccService.getConfig() to do stuff...
            });
    }

    ngOnDestroy() {
        // unsubscribe to cookieconsent observables to prevent memory leaks
        this.popupOpenSubscription.unsubscribe();
        this.popupCloseSubscription.unsubscribe();
        this.initializingSubscription.unsubscribe();
        this.initializedSubscription.unsubscribe();
        this.initializationErrorSubscription.unsubscribe();
        this.statusChangeSubscription.unsubscribe();
        this.revokeChoiceSubscription.unsubscribe();
        this.noCookieLawSubscription.unsubscribe();
    }
}

enableProdMode()
