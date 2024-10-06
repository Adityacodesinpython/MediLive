import {
  HlmCardContentDirective,
  HlmCardDescriptionDirective,
  HlmCardDirective,
  HlmCardFooterDirective,
  HlmCardHeaderDirective,
  HlmCardTitleDirective,
} from '@spartan-ng/ui-card-helm';

import {HlmButtonDirective} from "@spartan-ng/ui-button-helm";
import {Router } from "@angular/router";
import { Component, Input } from '@angular/core';
import { BrnSelectImports } from '@spartan-ng/ui-select-brain';
import { HlmSelectImports } from '@spartan-ng/ui-select-helm';

import {
  BrnPopoverCloseDirective,
  BrnPopoverComponent,
  BrnPopoverContentDirective,
  BrnPopoverTriggerDirective,
} from '@spartan-ng/ui-popover-brain';
import {HlmPopoverCloseDirective, HlmPopoverContentDirective} from "@spartan-ng/ui-popover-helm";
import { HlmLabelDirective } from '@spartan-ng/ui-label-helm';
import {NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {DoctorService} from "../../../services/doctor-dash/doctor.service";

@Component({
  selector: 'doctor-get-patient',
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
    BrnSelectImports,
    HlmSelectImports,
    FormsModule,
    HlmCardContentDirective
  ],
  providers: [],
  template: `
    <brn-popover>
      <section hlmCard>
        <div hlmCardHeader>
          <h3 hlmCardTitle>Check Patient status</h3>
          <p hlmCardDescription>If any</p>
        </div>
        <button brnPopoverTrigger type="submit" (click)="onSubmit()" hlmCardFooter hlmBtn variant="secondary" class="flex items-center text-md h-12 ml-5 mb-5">Get</button>

      </section>
      <div hlmPopoverContent class="w-80 ml-56 mt-1" *brnPopoverContent="let ctx">
        @if(gotInfo){
          <div class="space-y-2 mb-4">
              <h4 class="font-medium leading-none">You have a patient</h4>
              <p class="text-sm text-muted-foreground">Information</p>
          </div>
          <div class="grid mb-3">
            <div class="items-center grid grid-cols-2">
              <label hlmLabel class="h-8 col-span-1">Name:</label>
              <label hlmLabel class="h-8 col-span-1" >{{ gotInfo["userName"] }}</label>
            </div>
            <div class="items-center grid grid-cols-2">
              <label hlmLabel class="h-8 col-span-1">Id:</label>
              <label hlmLabel class="h-8 col-span-1" >{{ gotInfo["userId"] }}</label>
            </div>
            <div class="items-center grid grid-cols-2">
              <label hlmLabel class="h-8 col-span-1">Roles:</label>
              <label hlmLabel class="h-8 col-span-1" >{{ gotInfo["roles"] }}</label>
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

export class DoctorGetPatientCardComponent {

  @Input()
  jwtToken: string = '';

  username: string = '';
  password: string = '';
  gotInfo: any = {};

  constructor(
    private doctorService: DoctorService,
    private router: Router,
  ) { this.gotInfo = {};}

  onSubmit() {

    this.doctorService.getPatientInfo(this.jwtToken).subscribe({
      next: (res) => {
        this.gotInfo = res;
      },
      error: (error) => {
        this.gotInfo = null;
      }
    })
  }
}
