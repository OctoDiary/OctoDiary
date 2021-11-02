import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CookieService } from 'ngx-cookie';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  hide = true;

  constructor(
    private http: HttpClient,
    private cookie: CookieService,
    private snackBar: MatSnackBar,
    private router: Router
  ) { }

  public logIn(username: string, password: string): void {
    this.http.post(environment.apiBaseURL + 'auth', undefined, {
      params: {
        'username': username,
        'password': password
      }
    }).subscribe((response: any) => {
      this.cookie.put('token', response['access_token']);
      this.cookie.put('uid', response['user_id']);
      this.router.navigate(['/'])
    },() => {
      this.snackBar.open('Неверный логин или пароль!', undefined, {duration: 1000})
    })
  }
}
