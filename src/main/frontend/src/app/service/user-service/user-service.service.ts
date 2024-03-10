import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../model/user/user'

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

   private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/parser/users';
  }

    public findAll(): Observable<User[]> {
      return this.http.get<User[]>(this.usersUrl);
    }
}
