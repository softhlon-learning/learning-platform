import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {CoursesComponent} from './courses/courses.component';
import {CoursesService} from "./courses/courses.service";
import { provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import {CourseTocComponent} from "./course-toc/course-toc.component";
import {AppRoutingModule} from "./app-routing.module";
import {CourseProgressComponent} from "./course-progress/course-progress.component";
import {VideoItemComponent} from "./video-item/video-item.component";
import {PDFItemComponent} from "./pdf-item/pdf-item.component";
import {SafePipe} from "./common/safe-pipe/safe-pipe";
import {CourseNavigationComponent} from "./course-navigation/course-navigation.component";
import {QuizItemComponent} from "./quiz-item/quiz-item.component";

@NgModule({ declarations: [
        AppComponent,
        CoursesComponent,
        CourseTocComponent,
        CourseProgressComponent,
        CourseNavigationComponent,
        VideoItemComponent,
        PDFItemComponent,
        QuizItemComponent,
        SafePipe
    ],
    bootstrap: [AppComponent], imports: [BrowserModule,
        AppRoutingModule], providers: [
        CoursesService,
        provideHttpClient(withInterceptorsFromDi())
    ] })
export class AppModule {
}

