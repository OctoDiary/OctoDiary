import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DiaryComponent } from './diary/diary.component';
import { LoginComponent } from './login/login.component';
import { LoginGuard } from './login.guard';
import { DiaryGuard } from './diary.guard';

const routes: Routes = [
  {
    path: '',
    component: DiaryComponent,
    canActivate: [DiaryGuard]
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [LoginGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
