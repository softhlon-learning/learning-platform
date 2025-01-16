import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {HomeComponent} from './home/home.component';
import {CourseTocComponent} from './course-toc/course-toc.component';
import {CourseProgressComponent} from "./course-progress/course-progress.component";
import {SignInComponent} from "./sign-in/sign-in.component";

const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'sign-in', component: SignInComponent},
    {path: 'home', component: HomeComponent},
    {path: 'course/:id', component: CourseTocComponent},
    {path: 'course/:id/progress', component: CourseProgressComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
