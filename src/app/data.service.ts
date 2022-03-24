import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../environments/environment";
import { UserInfo } from "../assets/user-info";
import { CookieService } from "ngx-cookie";
import { Week } from "../assets/week";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  userInfo?: UserInfo;
  weeks?: Week[];

  constructor(private http: HttpClient, private cookie: CookieService) {
  }

  public logIn(username: string, password: string): Observable<any> {
    return this.http.post(environment.apiBaseURL + 'auth', undefined, {
      params: {
        'username': username,
        'password': password
      }
    });
  }

  public addUserInfo(): void {
    this.get<UserInfo>('user').subscribe((v) => {
      this.userInfo = <UserInfo>v;
    });
  }

  public addWeeks(): void {
    this.get<{ weeks: Week[] }>('diary').subscribe((v) => {
      this.weeks = <Week[]>v.weeks;
    });
  }

  private get<T>(url: string, params?: { [param: string]: string | number | boolean | ReadonlyArray<string | number | boolean>; }): Observable<T> {
    return this.http.get<T>(environment.apiBaseURL + url, {
      headers: {
        'Access-Token': this.cookie.get('token'),
        'User-ID': this.cookie.get('uid')
      },
      params: params
    });
  }
}
