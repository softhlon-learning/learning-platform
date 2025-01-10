import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {CoursesComponent} from './courses/courses.component';
import {CourseTocComponent} from './course-toc/course-toc.component';
import {CourseProgressComponent} from "./course-progress/course-progress.component";

const routes: Routes = [
    {path: '', redirectTo: '/courses', pathMatch: 'full'},
    {path: 'courses', component: CoursesComponent},
    {path: 'course/:id', component: CourseTocComponent},
    {path: 'course/:id/progress', component: CourseProgressComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
