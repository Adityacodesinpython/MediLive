import {Component, OnInit} from '@angular/core';
import {UserNavbarComponent} from "./user.navbar.component";
import {UserNurseCardComponent} from "./user.nursecard.component";
import {ActivatedRoute} from "@angular/router";
import {UserDoctorCardComponent} from "./user.doctorcard.component";

@Component({
  selector: 'user-dash',
  templateUrl: './user.dash.component.html',
  standalone: true,
  imports: [
    UserNavbarComponent,
    UserNurseCardComponent,
    UserDoctorCardComponent,
  ]
})
export class UserDashComponent implements OnInit {
  param: string = '';

  constructor(
    private activatedRoute: ActivatedRoute
  ) {

  }

  ngOnInit(): void {
    this.param = this.activatedRoute.snapshot.params["token"]
    console.log(this.param);
  }
}
