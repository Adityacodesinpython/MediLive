import {Component, OnInit} from '@angular/core';
import {AdminNavbarComponent} from "../../admin-dash/admin/admin.navbar.component";
import {DoctorGetPatientCardComponent} from "./doctor-get-patient-card.component";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-doctor',
  templateUrl: './doctor-dash.component.html',
  standalone: true,
  imports: [
    AdminNavbarComponent,
    DoctorGetPatientCardComponent,
  ]
})
export class DoctorDashComponent implements OnInit{
  param: string = '';

  constructor(
    private activatedRoute: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.param = this.activatedRoute.snapshot.params["token"]
    console.log(this.param);
  }
}
