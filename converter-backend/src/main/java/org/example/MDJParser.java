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

            // Assuming the UML model is within a "ownedElements" node
            JsonNode ownedElements = rootNode.path("ownedElements");

            for (JsonNode element : ownedElements) {
                // This is just an example path, adjust according to your .mdj structure
                String type = element.path("_type").asText();
                if ("UMLClass".equals(type)) {
                    String className = element.path("name").asText();
                    List<String> properties = new ArrayList<>();
                    if (element.has("attributes")) {
                        for (JsonNode attribute : element.path("attributes")) {
                            String attributeName = attribute.path("name").asText();
                            String attributeType = attribute.path("type").path("name").asText();
                            properties.add(attributeName + " : " + attributeType);
                        }
                    }
                    entities.put(className, properties);
                }
                // You can extend this to parse other UML elements such as relations
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
}
