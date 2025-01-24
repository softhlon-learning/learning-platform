import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {HomeComponent} from './home/home.component';
import {CourseTocComponent} from './course-toc/course-toc.component';
import {CourseDetailsComponent} from "./course-details/course-details.component";
import {SignInComponent} from "./sign-in/sign-in.component";
import {SignUpComponent} from './sign-up/sign-up.component';
import {PasswordRecoveryComponent} from "./reset-password/password-recovery.component";
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'sign-in', component: SignInComponent},
    {path: 'sign-up', component: SignUpComponent},
    {path: 'profile', component: ProfileComponent},
    {path: 'reset-password', component: PasswordRecoveryComponent},
    {path: 'home', component: HomeComponent},
    {path: 'course/:id', component: CourseTocComponent},
    {path: 'course/:id/details', component: CourseDetailsComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
