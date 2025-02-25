import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const IMAGE_API = 'http://localhost:8080/api/image/';

@Injectable({
  providedIn: 'root'
})
export class ImageUploadService {

  constructor(private http: HttpClient) { }

  private uploadImage(endpoint: string, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post(IMAGE_API + endpoint, formData);
  }

  uploadImageToUser(file: File): Observable<any> {
    return this.uploadImage('upload', file);
  }

  uploadImageToPost(file: File, postId: number): Observable<any> {
    return this.uploadImage(`${postId}/upload`, file);
  }

  getProfileImage(): Observable<Blob> {
    return this.http.get(IMAGE_API + 'profileImage', { responseType: 'blob' });
  }

  getImageToPost(postId: number): Observable<Blob> {
    return this.http.get(IMAGE_API + `${postId}/image`, { responseType: 'blob' });
  }
}

