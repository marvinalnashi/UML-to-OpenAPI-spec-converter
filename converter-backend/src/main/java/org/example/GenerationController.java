package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GenerationController {

    @PostMapping("/generate")
    public ResponseEntity<String> generateOpenAPISpec() {
        try {
            String xmlFilePath = "src/main/resources/test-class-diagram.xml";
            String outputPath = "src/main/resources/static/export.yml";
            Map<String, List<String>> entities = XMLParser.parseXML(xmlFilePath);
            OpenAPISpecGenerator.generateSpec(entities, outputPath);
            return ResponseEntity.ok("Generation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during generation");
        }
    }
}
