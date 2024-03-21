import { Component } from '@angular/core';
import { GenerationService } from '../generation.service';
import { MockServerService } from '../mock-server.service';

@Component({
  selector: 'app-generation',
  standalone: true,
  templateUrl: './generation.component.html',
  providers: [GenerationService, MockServerService]
})
export class GenerationComponent {
  constructor(
    private generationService: GenerationService,
    private mockServerService: MockServerService
  ) { }

  generate() {
    this.generationService.generateSpec().subscribe({
      next: (response) => console.log('Generation successful', response),
      error: (error) => console.error('Generation failed', error)
    });
  }

  startMockServer() {
    this.mockServerService.startMockServer().subscribe({
      next: (response) => console.log(response),
      error: (error) => console.error('Error starting mock server:', error)
    });
  }

}
