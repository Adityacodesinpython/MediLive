import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {List} from "postcss/lib/list";

@Injectable({
  providedIn: 'root'
})

export class AdminService {
  url: string = "http://localhost:5000/admin";

  constructor(
    private http: HttpClient
  ) {}

  getAll(name:string, token: string){
    return this.http.get<List>(`${this.url}/all-${name}`, { headers: { 'Authorization': `Bearer ${token}` }})
  }

  createAdmin(adminInfo: {
    "userName": string,
    "passWord": string
  }, token: string){
    return this.http.post(`${this.url}/create-admin`, adminInfo, { headers: { 'Authorization': `Bearer ${token}` }})
  }

  getDoctorInfo(id: string, token: string){
    return this.http.get(`${this.url}/get-doctor/${id}`, { headers: { 'Authorization': `Bearer ${token}` }})
  }

  getNurseInfo(id: string, token: string){
    return this.http.get(`${this.url}/get-nurse/${id}`, { headers: { 'Authorization': `Bearer ${token}` }})
  }

  updateAdmin(adminInfo: {userName: string, passWord: string}, token: string) {
    return this.http.put(`${this.url}`, adminInfo, { headers: { 'Authorization': `Bearer ${token}` }})
  }

  deleteAdmin(token: string) {
    return this.http.delete(`${this.url}`, { headers: { 'Authorization': `Bearer ${token}` }})
  }
}
