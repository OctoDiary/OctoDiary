import { Component } from '@angular/core';
import { DataService } from '../../data.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent {
  constructor(public data: DataService) {}

  stringify(o?: object) {
    return JSON.stringify(o, null, 4);
  }
}
