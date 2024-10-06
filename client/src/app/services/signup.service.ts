import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SignupService{

  url: string = "http://localhost:5000";

  constructor(
    private http: HttpClient
  ) { }

  signUp(userInfo: {
    "userName": string,
    "passWord": string,
    "firstName": string,
    "lastName": string
  }) {
    return this.http.post(`${this.url}/public/sign-up`, userInfo)
  }

}
