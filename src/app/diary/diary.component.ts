import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie';
import { animate, keyframes, style, transition, trigger } from "@angular/animations";
import { Router } from "@angular/router";
import { DataService } from "../data.service";

@Component({
  selector: 'app-diary',
  templateUrl: './diary.component.html',
  styleUrls: ['./diary.component.scss'],
  animations: [
    trigger('fadeOut', [
      transition(':leave', animate(
        1000,
        keyframes([style({opacity: 1, easing: 'ease', offset: 0}), style({opacity: 0, easing: 'ease', offset: 1})])
      ))
    ])
  ]
})
export class DiaryComponent {

  public objStr = (obj: object | undefined) => JSON.stringify(obj, undefined, 2);

  public utfStrings: { [name: string]: string } = {
    home: 'Главная',
    school: 'Уроки',
    edit: 'Общение',
  };

  public tabs = {
    home: '/',
    school: '/lessons',
    edit: '/social'
  };

  constructor(private http: HttpClient,
              private cookie: CookieService,
              private router: Router,
              public data: DataService) {
    data.addUserInfo();
    data.addWeeks();
  }

  public getRoute(): string {
    return this.router.url;
  }

}
