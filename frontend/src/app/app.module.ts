import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {PlatformService} from "./service/platform.service";
import {provideHttpClient} from "@angular/common/http";
import {CourseTocComponent} from "./course-toc/course-toc.component";
import {AppRoutingModule} from "./app-routing.module";
import {CourseProgressComponent} from "./course-progress/course-progress.component";
import {VideoItemComponent} from "./video-item/video-item.component";
import {PDFItemComponent} from "./pdf-item/pdf-item.component";
import {SafePipe} from "./common/safe-pipe/safe-pipe";
import {CourseNavigationComponent} from "./course-navigation/course-navigation.component";
import {QuizItemComponent} from "./quiz-item/quiz-item.component";
import {AppHeaderComponent} from "./header/app-header.component";
import { SignInComponent } from './sign-in/sign-in.component';
import {CourseTileComponent} from "./course-tile/course-tile.component";
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        CourseTocComponent,
        CourseProgressComponent,
        CourseNavigationComponent,
        VideoItemComponent,
        PDFItemComponent,
        QuizItemComponent,
        SafePipe,
        AppHeaderComponent,
        SignInComponent,
        CourseTileComponent,

    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule
    ],
    providers: [
        PlatformService,
        provideHttpClient()
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}

