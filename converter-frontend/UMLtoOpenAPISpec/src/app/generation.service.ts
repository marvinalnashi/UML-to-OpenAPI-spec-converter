import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class GenerationService {
  private baseUrl = 'http://localhost:8080';
  private generateUrl = 'http://localhost:8080/generate';

  constructor(private http: HttpClient) { }

  generateSpec(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);

    // Note: Adjust the URL as needed based on your backend setup
    return this.http.post(this.generateUrl, formData);
  }
}
