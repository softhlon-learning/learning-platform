// ---------------------------------------------------------------------------------------------------------------------
// Copyright (C) JAVA-FULLSTACK.TECH - All Rights Reserved
// Unauthorized copying of this file via any medium is strictly prohibited.
// ---------------------------------------------------------------------------------------------------------------------

import {NgModule} from '@angular/core'
import {RouterModule, Routes} from '@angular/router'
import {HomeComponent} from './home/home.component'
import {CourseTocComponent} from './course-toc/course-toc.component'
import {CourseDetailsComponent} from "./course-details/course-details.component"
import {SignInComponent} from "./sign-in/sign-in.component"
import {SignUpComponent} from './sign-up/sign-up.component'
import {ResetPasswordComponent} from "./reset-password/reset-password.component"
import {ProfileComponent} from './profile/profile.component'
import {UpdatePasswordComponent} from "./update-password/update-password.component"
import {SubscribeComponent} from "./subscribe/subscribe.component";
import {ManageSubscriptionComponent} from './manage-subscription/manage-subscription.component'
import {ActivateAccountComponent} from './activate-account/activate-account.component'

// ---------------------------------------------------------------------------------------------------------------------
// Implementation
// ---------------------------------------------------------------------------------------------------------------------

const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'sign-in', component: SignInComponent},
    {path: 'sign-up', component: SignUpComponent},
    {path: 'activate-account', component: ActivateAccountComponent},
    {path: 'profile', component: ProfileComponent},
    {path: 'subscribe', component: SubscribeComponent},
    {path: 'manage-subscription', component: ManageSubscriptionComponent},
    {path: 'reset-password', component: ResetPasswordComponent},
    {path: 'update-password', component: UpdatePasswordComponent},
    {path: 'home', component: HomeComponent},
    {path: 'course/:id', component: CourseTocComponent},
    {path: 'course/:id/details', component: CourseDetailsComponent}
]

@NgModule({
    imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
