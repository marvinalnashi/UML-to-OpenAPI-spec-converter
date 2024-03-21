import { Component } from '@angular/core';
import { GenerationComponent } from './generation/generation.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [GenerationComponent],
  template: `
    <app-generation></app-generation>
  `
})
export class AppComponent { }
