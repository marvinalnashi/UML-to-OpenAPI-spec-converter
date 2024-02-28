package org.example;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String xmlFilePath = "src/main/resources/test-class-diagram.xml";
        String outputPath = "src/main/resources/export.yml";
        Map<String, List<String>> entities = XMLParser.parseXML(xmlFilePath);
        OpenAPISpecGenerator.generateSpec(entities, outputPath);
    }
}

