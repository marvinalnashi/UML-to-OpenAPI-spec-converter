package org.example;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GenerationService {

    public String generateSpec() {
        try {
            String xmlFilePath = "src/main/resources/test-class-diagram.xml";
            String outputPath = "src/main/resources/static/export.yml";
            Map<String, List<String>> entities = XMLParser.parseXML(xmlFilePath);
            OpenAPISpecGenerator.generateSpec(entities, outputPath);
            return "Generation successful";
        } catch (Exception e) {
            return "Error during generation: " + e.getMessage();
        }
    }
}
