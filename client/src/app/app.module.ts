import { NgModule } from '@angular/core';
import {BrowserModule, provideClientHydration} from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {LoginComponent} from "./components/login/login.component";
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {UserModule} from "./modules/user.module";
import {HttpClient, provideHttpClient} from "@angular/common/http";
import {SignupService} from "./services/signup.service";
import {LoginService} from "./services/login.service";
import {FormsModule} from "@angular/forms";
import {AdminModule} from "./modules/admin.module";
import { AdminDashComponent } from './components/admin-dash/admin/admin-dash.component';
import { DoctorDashComponent } from './components/doctor-dash/doctor/doctor-dash.component';
import {DoctorModule} from "./modules/doctor.module";

@NgModule({
  declarations: [   // components made inside source i.e not standalone
    AppComponent,
  ],
  imports: [    // other components or modules are injected i.e standalone components
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    UserModule,
    AdminModule,
    LoginComponent,
    SignUpComponent,
    DoctorModule
  ],
  providers: [      // services
    provideHttpClient(),
    HttpClient,
    SignupService,
    LoginService
  ],
  bootstrap: [AppComponent]   // root component
})
export class AppModule { }
