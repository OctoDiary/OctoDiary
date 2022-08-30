import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DiaryComponent } from './diary/diary.component';
import { LoginComponent } from './login/login.component';
import { LoginGuard } from './login.guard';
import { DiaryGuard } from './diary.guard';
import { LessonsComponent } from './diary/lessons/lessons.component';
import { DashboardComponent } from './diary/dashboard/dashboard.component';
import { SocialComponent } from './diary/social/social.component';
import { ProfileComponent } from './diary/profile/profile.component';

const routes: Routes = [
  {
    path: '',
    component: DiaryComponent,
    canActivate: [DiaryGuard],
    children: [
      {
        path: '',
        component: DashboardComponent,
      },
      {
        path: 'lessons',
        component: LessonsComponent,
      },
      {
        path: 'social',
        component: SocialComponent,
      },
      {
        path: 'profile',
        component: ProfileComponent,
      },
    ],
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [LoginGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
