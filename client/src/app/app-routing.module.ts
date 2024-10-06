import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from "./app.component";
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {LoginComponent} from "./components/login/login.component";
import {UserDashComponent} from "./components/user-dash/user/user.dash.component";
import {AdminDashComponent} from "./components/admin-dash/admin/admin-dash.component";
import {DoctorDashComponent} from "./components/doctor-dash/doctor/doctor-dash.component";

const routes: Routes = [
  {
    path: 'sign-up',
    component: SignUpComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'user/:token',
    component: UserDashComponent
  },
  {
    path: 'admin/:token',
    component:AdminDashComponent
  },
  {
    path: 'doctor/:token',
    component:DoctorDashComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
