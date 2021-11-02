import { Component } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-diary',
  templateUrl: './diary.component.html',
  styleUrls: ['./diary.component.scss']
})
export class DiaryComponent {

  public userInfo: object | undefined;

  public objStr = (obj: object | undefined) => JSON.stringify(obj, undefined, 2);

  constructor(private http: HttpClient, private cookie: CookieService) {
    this.get('user').subscribe((v) => {this.userInfo = v});
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

}
