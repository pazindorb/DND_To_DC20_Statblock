import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { UserListComponent } from './component/user-list/user-list.component';

export const routes: Routes = [
  { path: 'users', component: UserListComponent },
];
