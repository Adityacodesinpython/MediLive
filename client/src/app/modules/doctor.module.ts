import {NgModule} from "@angular/core";
import {AdminDashComponent} from "../components/admin-dash/admin/admin-dash.component";
import {AdminService} from "../services/admin-dash/admin.service";
import {DoctorDashComponent} from "../components/doctor-dash/doctor/doctor-dash.component";
import {AdminNavbarComponent} from "../components/admin-dash/admin/admin.navbar.component";
import {DoctorService} from "../services/doctor-dash/doctor.service";

@NgModule({
  imports: [
    DoctorDashComponent,
    AdminNavbarComponent
  ],
  declarations: [],
  providers: [DoctorService],
  exports: []
})

export class DoctorModule {}
