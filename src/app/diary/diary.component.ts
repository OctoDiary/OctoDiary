import { Component } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie';
import { Observable } from 'rxjs';
import { UserInfo } from "../../assets/user-info";
import { animate, keyframes, style, transition, trigger } from "@angular/animations";
import { Router } from "@angular/router";

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

  public loaded: boolean = false;

  public userInfo: UserInfo;

  public objStr = (obj: object | undefined) => JSON.stringify(obj, undefined, 2);

  public utfStrings: { [name: string]: string } = {
    dashboard: 'Главная',
    lessons: 'Уроки',
    communication: 'Общение',
  }

  public tabs = {
    dashboard: '/',
    lessons: '/lessons',
    communication: '/social'
  }

  constructor(private http: HttpClient, private cookie: CookieService, private router: Router) {
    this.userInfo = <UserInfo>{}
    this.get('user').subscribe((v) => {
      this.userInfo = <UserInfo>v
      this.loaded = true;
    });
  }

  private get(url: string, params?: { [param: string]: string | number | boolean | ReadonlyArray<string | number | boolean>; }): Observable<Object> {
    return this.http.get(environment.apiBaseURL + url, {
      headers: {
        'Access-Token': this.cookie.get('token'),
        'User-ID': this.cookie.get('uid')
      },
      params: params
    })
  }

  public getRoute = () => {
    return this.router.url
  }

}
