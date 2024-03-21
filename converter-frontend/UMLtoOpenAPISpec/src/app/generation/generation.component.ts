import { Component } from '@angular/core';
import { GenerationService } from '../generation.service';
import { MockServerService } from '../mock-server.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-generation',
  standalone: true,
  templateUrl: './generation.component.html',
  providers: [GenerationService, MockServerService]
})
export class GenerationComponent {
  constructor(
    private generationService: GenerationService,
    private mockServerService: MockServerService,
    private http: HttpClient
  ) { }

  generate() {
    this.generationService.generateSpec().subscribe({
      next: (response) => console.log('Generation successful', response),
      error: (error) => console.error('Generation failed', error)
    });
  }

  startMockServer(): void {
    this.http.post('http://localhost:8080/start-mock-server', {}).subscribe({
      next: (response: any) => console.log(response),
      error: (error: any) => console.error(error)
    });
  }
}
