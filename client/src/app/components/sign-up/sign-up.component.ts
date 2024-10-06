import { Component } from '@angular/core';
import { HlmButtonDirective } from '@spartan-ng/ui-button-helm';
import { HlmInputDirective } from '@spartan-ng/ui-input-helm';
import {SignupService} from "../../services/signup.service";
import {FormsModule} from "@angular/forms";
import {Router, RouterModule, Routes} from '@angular/router';

@Component({
  selector: 'sign-up',
  standalone: true,
  imports: [HlmButtonDirective, HlmInputDirective, FormsModule],
  templateUrl: './sign-up.component.html'
})

export class SignUpComponent {
  username: string = '';
  firstname: string = '';
  lastname: string = '';
  password: string = '';

  constructor(
    private signupService:SignupService,
    private router: Router
  ) {
  }
  onSubmit() {
    this.signupService.signUp({
      userName: this.username,
      passWord: this.password,
      firstName: this.firstname,
      lastName:this.lastname
    }).subscribe({
        next: (res) => {
          console.log(res)
        },
        error: (error) => {
          alert(error)
        }
      })

    this.router.navigate(['/login'])
      .then(r => console.log(r));

  }
}
