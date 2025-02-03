// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) SOFTHLON-LEARNING.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {NgModule} from '@angular/core'
import {AppComponent} from './app.component'
import {HomeComponent} from './home/home.component'
import {provideHttpClient} from "@angular/common/http"
import {CourseTocComponent} from "./course-toc/course-toc.component"
import {AppRoutingModule} from "./app-routing.module"
import {CourseDetailsComponent} from "./course-details/course-details.component"
import {VideoLectureComponent} from "./video-lecture/video-lecture.component"
import {DocumentLectureComponent} from "./document-lecture/document-lecture.component"
import {SafePipe} from "./common/safe-pipe/safe-pipe"
import {CourseNavigationComponent} from "./course-navigation/course-navigation.component"
import {QuizItemComponent} from "./quiz-lecture/quiz-item.component"
import {AppHeaderComponent} from "./header/app-header.component"
import {SignInComponent} from './sign-in/sign-in.component'
import {CourseTileComponent} from "./course-tile/course-tile.component"
import {ReactiveFormsModule} from '@angular/forms'
import {BrowserModule} from '@angular/platform-browser'
import {SignUpComponent} from "./sign-up/sign-up.component"
import {ResetPasswordComponent} from "./reset-password/reset-password.component"
import {ProfileComponent} from "./profile/profile.component"
import {KeyboardInputCourseToc} from "./course-toc/keyboard-input"
import {KeyboardInputCourseDetails} from "./course-details/keyboard-input"
import {UpdatePasswordComponent} from "./update-password/update-password.component"
import {AccountsService} from "./service/accounts/accounts.service"
import {CoursesService} from "./service/courses/courses.service"
import {NgxStripeModule, provideNgxStripe} from 'ngx-stripe'
import {SubscribeComponent} from "./subscribe/subscribe.component";
import {ManageSubscriptionComponent} from "./manage-subscription/manage-subscription.component";
import {NgxSpinnerModule} from 'ngx-spinner'
import {AppFooterComponent} from "./footer/app-footer.component";
import {LockedLectureComponent} from "./locked-lecture/locked-lecture.component";

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        CourseTocComponent,
        CourseDetailsComponent,
        CourseNavigationComponent,
        VideoLectureComponent,
        DocumentLectureComponent,
        QuizItemComponent,
        SafePipe,
        AppHeaderComponent,
        SignInComponent,
        CourseTileComponent,
        SignUpComponent,
        ResetPasswordComponent,
        ProfileComponent,
        UpdatePasswordComponent,
        SubscribeComponent,
        ManageSubscriptionComponent,
        AppFooterComponent,
        LockedLectureComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        NgxStripeModule.forRoot(),
        NgxSpinnerModule
    ],
    providers: [
        AccountsService,
        CoursesService,
        KeyboardInputCourseToc,
        KeyboardInputCourseDetails,
        provideHttpClient(),
        provideNgxStripe()
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
