import {Component, OnInit} from '@angular/core';
import {AdminNavbarComponent} from "./admin.navbar.component";
import {AdminGetAllCardComponent} from "./admin-get-all-card.component";
import {ActivatedRoute} from "@angular/router";
import {CreateAdminCardComponent} from "./create-admin-card.component";
import {UpdateAdminCardComponent} from "./update-admin-card.component";
import {DeleteAdminCardComponent} from "./delete-admin-card.component";

@Component({
  selector: 'app-admin',
  templateUrl: './admin-dash.component.html',
  standalone: true,
  imports:[AdminNavbarComponent, AdminGetAllCardComponent, CreateAdminCardComponent, UpdateAdminCardComponent, DeleteAdminCardComponent]
})
export class AdminDashComponent implements OnInit{
  param: string = '';

  constructor(
    private activatedRoute: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.param = this.activatedRoute.snapshot.params["token"]
    console.log(this.param);
  }

}
