import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {PlatformService} from "./service/platform.service";
import {provideHttpClient} from "@angular/common/http";
import {CourseTocComponent} from "./course-toc/course-toc.component";
import {AppRoutingModule} from "./app-routing.module";
import {CourseDetailsComponent} from "./course-details/course-details.component";
import {VideoItemComponent} from "./video-lecture/video-item.component";
import {PDFItemComponent} from "./pdf-lecture/pdf-item.component";
import {SafePipe} from "./common/safe-pipe/safe-pipe";
import {CourseNavigationComponent} from "./course-navigation/course-navigation.component";
import {QuizItemComponent} from "./quiz-lecture/quiz-item.component";
import {AppHeaderComponent} from "./header/app-header.component";
import {SignInComponent} from './sign-in/sign-in.component';
import {CourseTileComponent} from "./course-tile/course-tile.component";
import {ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';
import {SignUpComponent} from "./sign-up/sign-up.component";
import {PasswordRecoveryComponent} from "./password-recovery/password-recovery.component";
import {ProfileComponent} from "./profile/profile.component";
import {KeyboardInputCourseToc} from "./course-toc/keyboard-input";

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        CourseTocComponent,
        CourseDetailsComponent,
        CourseNavigationComponent,
        VideoItemComponent,
        PDFItemComponent,
        QuizItemComponent,
        SafePipe,
        AppHeaderComponent,
        SignInComponent,
        CourseTileComponent,
        SignUpComponent,
        PasswordRecoveryComponent,
        ProfileComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule
    ],
    providers: [
        PlatformService,
        KeyboardInputCourseToc,
        provideHttpClient()
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}

