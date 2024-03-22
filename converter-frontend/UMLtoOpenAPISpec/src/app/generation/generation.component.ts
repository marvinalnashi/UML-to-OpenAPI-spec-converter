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

  selectedFile: File | null = null;

  constructor(
    private generationService: GenerationService,
    private mockServerService: MockServerService,
    private http: HttpClient
  ) { }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  generate() {
    if (this.selectedFile) {
      this.generationService.generateSpec(this.selectedFile).subscribe({
        next: (response) => console.log('Generation successful', response),
        error: (error) => console.error('Generation failed', error)
      });
    } else {
      console.error('No file selected');
    }
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
