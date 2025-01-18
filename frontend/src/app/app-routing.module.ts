import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {HomeComponent} from './home/home.component';
import {CourseTocComponent} from './course-toc/course-toc.component';
import {CourseDetailsComponent} from "./course-details/course-details.component";
import {SignInComponent} from "./sign-in/sign-in.component";
import {SignUpComponent} from './sign-up/sign-up.component';
import {PasswordRecoveryComponent} from "./password-recovery/password-recovery.component";

const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'sign-in', component: SignInComponent},
    {path: 'sign-up', component: SignUpComponent},
    {path: 'password-recovery', component: PasswordRecoveryComponent},
    {path: 'home', component: HomeComponent},
    {path: 'course/:id', component: CourseTocComponent},
    {path: 'course/:id/progress', component: CourseDetailsComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
