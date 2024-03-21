package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockServerController {

    @GetMapping("/start-prism-mock")
    public String startPrismMockServer() {
        try {
            String command = "prism mock -p 4010 --cors export.yml";
            Runtime.getRuntime().exec(command);
            return "Prism Mock Server is starting...";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to start Prism Mock Server.";
        }
    }
}
