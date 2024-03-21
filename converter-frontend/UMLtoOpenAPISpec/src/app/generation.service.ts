import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class GenerationService {
  private generateUrl = 'http://localhost:8080/generate';

  constructor(private http: HttpClient) { }

  generateSpec(): Observable<any> {
    return this.http.post(this.generateUrl, {});
  }
}
