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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
public class GenerationController {

    private final String outputPath = "/data/export.yml"; // Adjusted path for Docker volume

    @PostMapping("/generate")
    public ResponseEntity<String> generateOpenAPISpec(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            InputStream fileInputStream = file.getInputStream();
            Map<String, List<String>> parsedData;
            String fileName = file.getOriginalFilename();

            if (fileName != null && fileName.endsWith(".xml")) {
                parsedData = XMLParser.parseXML(fileInputStream);
            } else if (fileName != null && fileName.endsWith(".uxf")) {
                parsedData = UXFParser.parseUXF(fileInputStream);
            } else if (fileName != null && fileName.endsWith(".mdj")) {
                parsedData = MDJParser.parseMDJ(fileInputStream);
            } else {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Unsupported file type");
            }

            OpenAPISpecGenerator.generateSpec(parsedData, outputPath);

            return ResponseEntity.ok("Generation successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during generation: " + e.getMessage());
        }
    }

    @GetMapping("/export.yml")
    public ResponseEntity<Resource> getOpenAPISpec() {
        Resource fileResource = new FileSystemResource(outputPath);
        if (!fileResource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"export.yml\"")
                .contentType(MediaType.parseMediaType("application/x-yaml"))
                .body(fileResource);
    }
}
