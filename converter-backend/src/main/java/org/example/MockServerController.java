package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@RestController
public class MockServerController {

    @GetMapping("/start-prism-mock")
    public ResponseEntity<Object> startPrismMockServer() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        try {
            processBuilder.command("bash", "-c", "prism mock -p 4010 --cors --host 0.0.0.0 /data/export.yml");

            // Start the process
            Process process = processBuilder.start();

            // Read output and errors (for debugging)
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            return ResponseEntity.ok().body(Map.of("message", "Prism Mock Server is starting..."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Failed to start Prism Mock Server due to exception: " + e.getMessage()));
        }
    }
}
