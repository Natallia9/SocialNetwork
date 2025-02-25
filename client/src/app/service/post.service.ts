import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Post } from '../models/Post';
import { Observable } from 'rxjs';

const POST_API = 'http://localhost:8080/api/post/';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  createPost(post: Post): Observable<Post> {
    return this.http.post<Post>(POST_API + 'create', post);
  }

  getAllPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(POST_API + 'all');
  }

  getPostForCurrentUser(): Observable<Post[]> {
    return this.http.get<Post[]>(POST_API + 'user/posts');
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${POST_API}${id}/delete`);
  }

  likePost(id: number, username: string): Observable<any> {
    const headers = new HttpHeaders().set('Username', username); // Передаем имя пользователя в заголовке
    return this.http.post(`${POST_API}${id}/like`, null, { headers });
  }
}

