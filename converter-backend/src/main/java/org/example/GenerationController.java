package org.example;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
public class GenerationController {

    private final String xmlFilePath = "src/main/resources/test-class-diagram.xml";
    private final String outputPath = "export.yml";

    @PostMapping("/generate")
    public ResponseEntity<String> generateOpenAPISpec() {
        try {
            Map<String, List<String>> entities = XMLParser.parseXML(xmlFilePath);
            OpenAPISpecGenerator.generateSpec(entities, outputPath);
            return ResponseEntity.ok("Generation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during generation");
        }
    }

    @GetMapping("/export.yml")
    public ResponseEntity<InputStreamResource> getOpenAPISpec() throws FileNotFoundException {
        File file = new File(outputPath);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.yml");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/x-yaml"))
                .body(resource);
    }
}
