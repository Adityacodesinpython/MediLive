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
import {AdminService} from "../../../services/admin-dash/admin.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'get-all-card',
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
    FormsModule
  ],
  providers: [],
  template: `
    <brn-popover>
      <section hlmCard>
        <div hlmCardHeader>
          <h3 hlmCardTitle>Get information</h3>
          <p hlmCardDescription>All users, doctors, nurses below</p>
        </div>

        <brn-select hlmCardContent class="inline-block" placeholder="Select an option">
          <hlm-select-trigger>
            <hlm-select-value/>
          </hlm-select-trigger>
          <hlm-select-content [(ngModel)]="getAll" class="w-56">
            <hlm-option name="doctor" ngDefaultControl  value="doctors" >Doctors</hlm-option>
            <hlm-option name="user" ngDefaultControl  value="users">Users</hlm-option>
            <hlm-option name="nurse" ngDefaultControl  value="nurses" >Nurses</hlm-option>
          </hlm-select-content>
        </brn-select>

        <button brnPopoverTrigger type="submit" (click)="onSubmit()" hlmCardFooter hlmBtn variant="secondary" class="flex items-center text-md h-12 ml-5 mb-5">Get</button>
      </section>
      <div hlmPopoverContent class="w-80 ml-56 mt-1" *brnPopoverContent="let ctx">
        @if(gotInfo.length > 0){
          @switch (getAll[0]) {
            @case ('doctors') {
                <div class="space-y-2 mb-4">
                    <h4 class="font-medium leading-none">All Doctors in your institution</h4>
                    <p class="text-sm text-muted-foreground">Contact Information</p>
                </div>
              @for (info of gotInfo; track info.id) {
                <div class="grid mb-3">
                  <div class="items-center grid grid-cols-4">
                    <label hlmLabel class="h-8 col-span-3">Name:</label>
                    <label hlmLabel class="h-8 col-span-1" >{{ info["userName"] }}</label>
                  </div>
                  <div class="items-center grid grid-cols-4 gap-2">
                    <label hlmLabel class="h-8 col-span-3">Id:</label>
                    <label hlmLabel class="h-8 col-span-1" >{{ info["doctorId"] }}</label>
                  </div>
                </div>
              }
            }

            @case ('users') {
              <div class="space-y-2 mb-4">
                <h4 class="font-medium leading-none">All Users in your institution</h4>
                <p class="text-sm text-muted-foreground">Contact Information</p>
              </div>
              @for (info of gotInfo; track info.id) {
                <div class="grid mb-3">
                  <div class="items-center grid grid-cols-4">
                    <label hlmLabel class="h-8 col-span-3">Name:</label>
                    <label hlmLabel class="h-8 col-span-1" >{{ info["userName"] }}</label>
                  </div>
                  <div class="items-center grid grid-cols-4 gap-2">
                    <label hlmLabel class="h-8 col-span-3">Id:</label>
                    <label hlmLabel class="h-8 col-span-1" >{{ info["userId"] }}</label>
                  </div>
                </div>
              }
            }

            @case ('nurses') {
              <div class="space-y-2 mb-4">
                <h4 class="font-medium leading-none">All Nurses in your institution</h4>
                <p class="text-sm text-muted-foreground">Contact Information</p>
              </div>
              @for (info of gotInfo; track info.id) {
                <div class="grid mb-3">
                  <div class="items-center grid grid-cols-4">
                    <label hlmLabel class="h-8 col-span-3">Name:</label>
                    <label hlmLabel class="h-8 col-span-1" >{{ info["userName"] }}</label>
                  </div>
                  <div class="items-center grid grid-cols-4 gap-2">
                    <label hlmLabel class="h-8 col-span-3">Id:</label>
                    <label hlmLabel class="h-8 col-span-1" >{{ info["nurseId"] }}</label>
                  </div>
                </div>
              }
            }
          }

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

export class AdminGetAllCardComponent {

  @Input()
  jwtToken: string = '';

  getAll: string = '';
  gotInfo: any = {};

  constructor(
    private adminService: AdminService,
    private router: Router,
  ) { this.gotInfo = {};}

  onSubmit() {

    this.adminService.getAll(this.getAll, this.jwtToken).subscribe({
      next: (res) => {

        console.log(res);
        this.gotInfo = res;
      },

      error: (error) => {
        console.log(error);
      }
    })
  }
}
