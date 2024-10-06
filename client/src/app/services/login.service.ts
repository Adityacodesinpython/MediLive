import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";

interface LoginResponse {
  role:string,
  token: string
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url: string = "http://localhost:5000";

  constructor(
    private http: HttpClient
  ) { }

  login(userInfo: {
    "userName": string,
    "passWord": string
  }){
    return this.http.post<LoginResponse>(`${this.url}/public/login`, userInfo)
  }
}
