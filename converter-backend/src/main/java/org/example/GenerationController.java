package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class GenerationController {

    @Value("classpath:test-class-diagram.xml")
    private Resource xmlFileResource;

    private final String outputPath = "/app/data/export.yml"; // Adjusted path for Docker volume

    @PostMapping("/generate")
    public ResponseEntity<String> generateOpenAPISpec() {
        try {
            // Use the getInputStream method to pass the InputStream directly to XMLParser
            Map<String, List<String>> entities = XMLParser.parseXML(xmlFileResource.getInputStream());
            OpenAPISpecGenerator.generateSpec(entities, outputPath);
            return ResponseEntity.ok("Generation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during generation");
        }
    }

    @GetMapping("/export.yml")
    public ResponseEntity<Resource> getOpenAPISpec() throws IOException {
        Resource fileResource = new FileSystemResource(outputPath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export.yml")
                .contentType(MediaType.parseMediaType("application/x-yaml"))
                .body(fileResource);
    }
}
