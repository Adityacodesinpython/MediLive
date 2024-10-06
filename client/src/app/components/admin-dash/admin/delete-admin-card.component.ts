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
  selector: 'delete-admin-card',
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
          <h3 hlmCardTitle>Delete your profile</h3>
          <p hlmCardDescription>Remove Admin profile from below</p>

        </div>
        <button brnPopoverTrigger type="submit" (click)="onSubmit()" hlmCardFooter hlmBtn class="flex items-center text-md h-12 ml-5 mb-5">Delete</button>

      </section>
      <div hlmPopoverContent class="w-80 ml-56 mt-1" *brnPopoverContent="let ctx">
        @if(gotInfo){
          <div class="space-y-2 mb-4">
            <h4 class="font-medium leading-none">Admin deleted</h4>
            <p class="text-sm text-muted-foreground">Thank you</p>
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

export class DeleteAdminCardComponent {

  @Input()
  jwtToken: string = '';

  username: string = '';
  password: string = '';

  gotInfo: any = {};

  constructor(
    private adminService: AdminService,
    private router: Router,
  ) { this.gotInfo = {};}

  onSubmit() {
    this.adminService.deleteAdmin(this.jwtToken).subscribe({
      next: (res) => {
        this.gotInfo = res;
        this.router.navigate(['/login']).then(r => console.log(r));
      },
      error: (error) => {
        this.gotInfo = null;
        console.log(error);
      }
    })
  }
}
