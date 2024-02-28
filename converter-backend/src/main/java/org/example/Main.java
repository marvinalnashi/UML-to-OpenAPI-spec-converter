package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        String xmlFilePath = "src/main/resources/test-class-diagram.xml";
        String outputPath = "src/main/resources/static/export.yml";
        Map<String, List<String>> entities = XMLParser.parseXML(xmlFilePath);
        OpenAPISpecGenerator.generateSpec(entities, outputPath);
    }
}

