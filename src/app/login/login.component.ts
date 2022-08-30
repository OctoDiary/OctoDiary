import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { DataService } from '../data.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  hide = true;

  constructor(
    private data: DataService,
    private cookie: CookieService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  public logIn(username: string, password: string): void {
    this.data.logIn(username, password).subscribe(
      (response: any) => {
        this.cookie.put('token', response['access_token']);
        this.cookie.put('uid', response['user_id']);
        this.router.navigate(['/']);
      },
      () => {
        this.snackBar.open('Неверный логин или пароль!', undefined, {
          duration: 1000,
        });
      }
    );
  }
}
