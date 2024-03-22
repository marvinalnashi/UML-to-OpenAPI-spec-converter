import { Component } from '@angular/core';
import { GenerationService } from '../generation.service';
import { MockServerService } from '../mock-server.service';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";

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

  onStartMockServer(): void {
    this.mockServerService.startMockServer().subscribe({
      next: (response) => {
        console.log(response.message);
      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }
}
