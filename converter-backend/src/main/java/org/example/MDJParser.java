package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MDJParser {

    public static Map<String, List<String>> parseMDJ(InputStream mdjInputStream) {
        Map<String, List<String>> entities = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(mdjInputStream);

            // Assuming the structure you provided, navigating through the JSON to find classes
            JsonNode ownedElements = rootNode.path("ownedElements");
            for (JsonNode ownedElement : ownedElements) {
                JsonNode modelElements = ownedElement.path("ownedElements");
                for (JsonNode modelElement : modelElements) {
                    if ("UMLClass".equals(modelElement.path("_type").asText())) {
                        String className = modelElement.path("name").asText();
                        List<String> attributes = new ArrayList<>();
                        for (JsonNode attribute : modelElement.path("attributes")) {
                            String attributeName = attribute.path("name").asText();
                            String attributeType = attribute.path("type").asText("");
                            attributes.add("+ " + attributeName + ": " + attributeType);
                        }
                        entities.put(className, attributes);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
}
