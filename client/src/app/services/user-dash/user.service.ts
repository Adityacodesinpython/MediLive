import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {List} from "postcss/lib/list";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url: string = "http://localhost:5000/user";

  constructor(
    private http: HttpClient
  ) { }

  getNurse(token: string){
    return this.http.get<List>(`${this.url}/get-nurse`, { headers: { 'Authorization': `Bearer ${token}` }})
  }

  getDoctor(token: string){
    return this.http.get(`${this.url}/get-doctor`, { headers: { 'Authorization': `Bearer ${token}` }})
  }

  getEmergencyDoctor(token: string, department: string){
    return this.http.get(`${this.url}/get-doctor/${department}`, { headers: { 'Authorization': `Bearer ${token}` }})
  }
}
