import {
  HlmCardContentDirective,
  HlmCardDescriptionDirective,
  HlmCardDirective,
  HlmCardFooterDirective,
  HlmCardHeaderDirective,
  HlmCardTitleDirective,
} from '@spartan-ng/ui-card-helm';

import {Component, Input, OnInit} from "@angular/core";
import {HlmButtonDirective} from "@spartan-ng/ui-button-helm";
import {LoginService} from "../../../services/login.service";
import {Router } from "@angular/router";
import {UserService} from "../../../services/user-dash/user.service";
import {List} from "postcss/lib/list";
import {
  BrnPopoverCloseDirective,
  BrnPopoverComponent,
  BrnPopoverContentDirective,
  BrnPopoverTriggerDirective,
} from '@spartan-ng/ui-popover-brain';
import {HlmPopoverCloseDirective, HlmPopoverContentDirective} from "@spartan-ng/ui-popover-helm";
import { HlmLabelDirective } from '@spartan-ng/ui-label-helm';
import {NgIf} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HlmInputDirective} from "@spartan-ng/ui-input-helm";

@Component({
  selector: 'doctor-card',
  standalone: true,
  imports: [
    HlmCardDirective,
    HlmCardHeaderDirective,
    HlmCardTitleDirective,
    HlmCardDescriptionDirective,
    HlmCardContentDirective,
    HlmCardFooterDirective,
    HlmButtonDirective,
    BrnPopoverComponent,
    BrnPopoverTriggerDirective,
    BrnPopoverContentDirective,
    BrnPopoverCloseDirective,
    HlmPopoverContentDirective,
    HlmPopoverCloseDirective,
    HlmLabelDirective,
    NgIf,
    ReactiveFormsModule,
    HlmInputDirective,
    FormsModule
  ],
  providers: [],
  template: `
    <brn-popover>
      <section hlmCard>
        @if(hasDoctor){
          <div hlmCardHeader>
            <h3 hlmCardTitle>You have a doctor scheduled</h3>
            <p hlmCardDescription>Get more information below</p>
          </div>
          <button brnPopoverTrigger type="submit" hlmCardFooter hlmBtn variant="secondary" class="pt-2 flex items-center text-md h-12 ml-5 mb-5">Get Doctor</button>

        } @else {
          <div hlmCardHeader>
            <h3 hlmCardTitle>Get an Emergency doctor!</h3>
            <p hlmCardDescription>Insert department below</p>
          </div>
          <input class="mx-5 mb-5 w-80 h-14 text-md" hlmInput placeholder='Department' name="department" [(ngModel)]="department" type='text' />

          <button brnPopoverTrigger type="submit" (click)="onSubmit()" hlmCardFooter hlmBtn variant="secondary" class="pt-2 flex items-center text-md h-12 ml-5 mb-5">Emergency!</button>
        }
        </section>
      <div hlmPopoverContent class="ml-52 w-80 mt-1" *brnPopoverContent="let ctx">
        @if(hasDoctor){
          <div class="grid">
            <div class="items-center grid grid-cols-4 gap-2">
              <label hlmLabel class="h-8 col-span-3">Doctor Id:</label>
              <label hlmLabel class="h-8 col-span-1" >{{ doctorInfo["doctorId"] }}</label>
            </div>
            <div class="items-center grid grid-cols-4 gap-2">
              <label hlmLabel class="h-8 col-span-3">Doctor Name:</label>
              <label hlmLabel class="h-8 col-span-1" >{{ doctorInfo["userName"] }}</label>
            </div>
          </div>
        } @else {
          <div class="space-y-2 mb-4">
            <h4 class="font-medium leading-none">An error occurred</h4>
            <p class="text-sm text-muted-foreground">Please try again later</p>
          </div>
        }
      </div>
    </brn-popover>
  `
})

export class UserDoctorCardComponent implements OnInit{

  @Input()
  jwtToken: string = '';

  doctorInfo: any = {};
  hasDoctor: boolean = false;
  department: string = '';

  constructor(
    private userService: UserService,
    private router: Router,
  ) {}

  onSubmit(){
    this.userService.getEmergencyDoctor(this.jwtToken, this.department).subscribe({
      next: (res) => {
        this.doctorInfo = res;
        this.hasDoctor = true;
      },
      error: (error) => {
        console.log(error);
      }
    })
  }

  ngOnInit(): void {
    this.userService.getDoctor(this.jwtToken).subscribe({
      next: (res) => {

        this.doctorInfo = res;
        this.hasDoctor = true;
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
