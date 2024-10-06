import {NgModule} from "@angular/core";
import {UserService} from "../services/user-dash/user.service";
import {UserDashComponent} from "../components/user-dash/user/user.dash.component";
import {UserNavbarComponent} from "../components/user-dash/user/user.navbar.component";
import {UserNurseCardComponent} from "../components/user-dash/user/user.nursecard.component";
import {UserDoctorCardComponent} from "../components/user-dash/user/user.doctorcard.component";

@NgModule({
  imports: [
    UserDashComponent,
    UserNavbarComponent,
    UserNurseCardComponent,
    UserDoctorCardComponent,
  ],
  declarations: [],
  providers: [UserService],
  exports: []
})

export class UserModule {}
