import {NgModule} from "@angular/core";
import {AdminDashComponent} from "../components/admin-dash/admin/admin-dash.component";
import {AdminService} from "../services/admin-dash/admin.service";

@NgModule({
  imports: [
    AdminDashComponent,
  ],
  declarations: [],
  providers: [AdminService],
  exports: []
})

export class AdminModule {}
