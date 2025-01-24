import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {PlatformService} from "./service/platform.service";
import {provideHttpClient} from "@angular/common/http";
import {CourseTocComponent} from "./course-toc/course-toc.component";
import {AppRoutingModule} from "./app-routing.module";
import {CourseDetailsComponent} from "./course-details/course-details.component";
import {VideoItemComponent} from "./video-lecture/video-item.component";
import {DocumentLectureComponent} from "./document-lecture/document-lecture.component";
import {SafePipe} from "./common/safe-pipe/safe-pipe";
import {CourseNavigationComponent} from "./course-navigation/course-navigation.component";
import {QuizItemComponent} from "./quiz-lecture/quiz-item.component";
import {AppHeaderComponent} from "./header/app-header.component";
import {SignInComponent} from './sign-in/sign-in.component';
import {CourseTileComponent} from "./course-tile/course-tile.component";
import {ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {SignUpComponent} from "./sign-up/sign-up.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";
import {ProfileComponent} from "./profile/profile.component";
import {KeyboardInputCourseToc} from "./course-toc/keyboard-input";
import {KeyboardInputCourseDetails} from "./course-details/keyboard-input";
import {UpdatePasswordComponent} from "./update-password/update-password.component";

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        CourseTocComponent,
        CourseDetailsComponent,
        CourseNavigationComponent,
        VideoItemComponent,
        DocumentLectureComponent,
        QuizItemComponent,
        SafePipe,
        AppHeaderComponent,
        SignInComponent,
        CourseTileComponent,
        SignUpComponent,
        ResetPasswordComponent,
        ProfileComponent,
        UpdatePasswordComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule
    ],
    providers: [
        PlatformService,
        KeyboardInputCourseToc,
        KeyboardInputCourseDetails,
        provideHttpClient()
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
