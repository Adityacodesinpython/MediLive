import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {List} from "postcss/lib/list";

@Injectable({
  providedIn: 'root'
})

export class DoctorService {
  url: string = "http://localhost:5000/doctor";

  constructor(
    private http: HttpClient
  ) {}

  getPatientInfo(token: string){
    return this.http.get(`${this.url}/patient`, { headers: { 'Authorization': `Bearer ${token}` }})
  }
}
