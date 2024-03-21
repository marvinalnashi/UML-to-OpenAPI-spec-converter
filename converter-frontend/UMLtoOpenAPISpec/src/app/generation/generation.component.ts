import { Component } from '@angular/core';
import { GenerationService } from '../generation.service';

@Component({
  selector: 'app-generation',
  standalone: true,
  template: `<button (click)="generate()">Generate</button>`,
  providers: [GenerationService]
})
export class GenerationComponent {
  constructor(private generationService: GenerationService) { }

  generate() {
    this.generationService.generateSpec().subscribe({
      next: (response) => console.log('Generation successful', response),
      error: (error) => console.error('Generation failed', error)
    });
  }
}
