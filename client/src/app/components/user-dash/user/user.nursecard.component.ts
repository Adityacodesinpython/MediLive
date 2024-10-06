import {
  HlmCardContentDirective,
  HlmCardDescriptionDirective,
  HlmCardDirective,
  HlmCardFooterDirective,
  HlmCardHeaderDirective,
  HlmCardTitleDirective,
} from '@spartan-ng/ui-card-helm';

import {Component, Input} from "@angular/core";
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

@Component({
  selector: 'nurse-card',
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
    NgIf
  ],
  providers: [],
  template: `
    <brn-popover>
      <section hlmCard>
        <div hlmCardHeader>
          <h3 hlmCardTitle>Need a nurse?</h3>
          <p hlmCardDescription>Schedule one with the button below</p>
        </div>
        <p hlmCardContent>Nurse timings:</p>
        <p hlmCardContent>day - 9 to 5</p>
        <p hlmCardContent>night - 5 to 9</p>
        <p hlmCardContent>We provide separate nurses for day and night as per their shifts</p>

        <button brnPopoverTrigger type="submit" (click)="onSubmit()" hlmCardFooter hlmBtn variant="secondary" class="flex items-center text-md h-12 ml-5 mb-5">Get Nurse</button>
      </section>
      <div hlmPopoverContent class="w-80 ml-52 mt-1" *brnPopoverContent="let ctx">
        @if(gotNurse){
          <div class="space-y-2 mb-4">
            <h4 class="font-medium leading-none">Nurse has been Allocated</h4>
            <p class="text-sm text-muted-foreground">Contact Information</p>
          </div>
          <div class="grid">
            <div class="items-center grid grid-cols-4 gap-2">
              <label hlmLabel class="h-8 col-span-3">Day Nurse Name:</label>
              <label hlmLabel class="h-8 col-span-1" >{{ dayNurseInfo["userName"] }}</label>
            </div>
            <div class="items-center grid grid-cols-4 gap-2">
              <label hlmLabel class="h-8 col-span-3">Night Nurse Name:</label>
              <label hlmLabel class="h-8 col-span-1" >{{ nightNurseInfo["userName"] }}</label>
            </div>
            <div class="items-center grid grid-cols-4 gap-2">
              <label hlmLabel class="h-8 col-span-3">Day Nurse id:</label>
              <label hlmLabel class="h-8 col-span-1" >{{ dayNurseInfo["nurseId"] }}</label>
            </div>
            <div class="items-center grid grid-cols-4 gap-2">
              <label hlmLabel class="h-8 col-span-3">Night Nurse id:</label>
              <label hlmLabel class="h-8 col-span-1" >{{ nightNurseInfo["nurseId"] }}</label>
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

export class UserNurseCardComponent {

  @Input()
  jwtToken: string = '';

  gotNurse: boolean = false;
  dayNurseInfo: any = '';
  nightNurseInfo: any = '';
  day: string = '';

  constructor(
    private userService: UserService,
    private router: Router,
  ) { this.gotNurse = false }

  onSubmit() {
    const date = new Date();

    this.userService.getNurse(this.jwtToken).subscribe({
      next: (res: List) => {
        this.day = date.toLocaleDateString('en-US', { weekday: 'long' });
        // @ts-ignore
        this.dayNurseInfo = res[0];
        // @ts-ignore
        this.nightNurseInfo = res[1];
        this.gotNurse = true;
      },
      error: (error) => {
        console.log(error);
      }
    })
  }
}
