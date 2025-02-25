import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';

const USER_API = 'http://localhost:8080/api/user/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${USER_API}${id}`);
  }

  getCurrentUser(): Observable<User> {
    const params = new HttpParams().set('include', 'profile,posts');
    return this.http.get<User>(USER_API, { params });
  }

  updateUser(user: User): Observable<User> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.http.put<User>(`${USER_API}update`, user, { headers });
  }
}

