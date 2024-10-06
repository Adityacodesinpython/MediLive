import { Component } from '@angular/core';
import { HlmButtonDirective } from '@spartan-ng/ui-button-helm';
import { HlmInputDirective } from '@spartan-ng/ui-input-helm';
import {Router} from "@angular/router";
import {LoginService} from "../../services/login.service";
import {FormsModule} from "@angular/forms";
import { BrnAlertDialogContentDirective, BrnAlertDialogTriggerDirective } from '@spartan-ng/ui-alertdialog-brain';
import {
  HlmAlertDialogActionButtonDirective,
  HlmAlertDialogCancelButtonDirective,
  HlmAlertDialogComponent,
  HlmAlertDialogContentComponent,
  HlmAlertDialogDescriptionDirective,
  HlmAlertDialogFooterComponent,
  HlmAlertDialogHeaderComponent,
  HlmAlertDialogOverlayDirective,
  HlmAlertDialogTitleDirective,
} from '@spartan-ng/ui-alertdialog-helm';

interface LoginResponse {
  role: string,
  token: string
}

@Component({
  selector: 'login',
  standalone: true,
  imports: [HlmAlertDialogActionButtonDirective,
    HlmAlertDialogCancelButtonDirective,
    HlmAlertDialogComponent,
    HlmAlertDialogContentComponent,
    HlmAlertDialogDescriptionDirective,
    HlmAlertDialogFooterComponent,
    HlmAlertDialogHeaderComponent,
    HlmAlertDialogOverlayDirective,
    HlmAlertDialogTitleDirective,HlmButtonDirective, HlmInputDirective, FormsModule,
    BrnAlertDialogContentDirective, BrnAlertDialogTriggerDirective],
  templateUrl: './login.component.html'
})

export class LoginComponent {
  password: string = '';
  username: string = '';

  gotError: boolean = false;

  jwtToken: string = '';
  role: string = '';

  constructor(
    private loginService:LoginService,
    private router: Router
  ) {}

  onSubmit() {
    this.loginService.login({
      userName: this.username,
      passWord: this.password,
    }).subscribe({
      next: (res: LoginResponse) => {
        this.jwtToken = res.token
        this.role = res.role.substring(5)

        switch (this.role) {
          case 'USER':
            this.router.navigate([`/user/${this.jwtToken}`])
              .then(r => console.log(r));
            break;
          case 'ADMIN':
            this.router.navigate([`/admin/${this.jwtToken}`])
              .then(r => console.log(r));
            break;
          case 'DOCTOR':
            this.router.navigate([`/doctor/${this.jwtToken}`])
              .then(r => console.log(r));
            break;
          case 'NURSE':
            this.router.navigate([`/nurse/${this.jwtToken}`])
              .then(r => console.log(r));
            break;
        }



      },
      error: (error) => {
        this.gotError = true;
        console.log(error);
      }
    })


  }
}
