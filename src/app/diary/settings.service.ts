import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class SettingsService {
  constructor(private http: HttpClient) {
    this.http.get(
      'https://www.googleapis.com/webfonts/v1/webfonts?key=AIzaSyDelssfp2zxefxvtFoKBxz6-CYB_ywQMOQ'
    );
  }
}
