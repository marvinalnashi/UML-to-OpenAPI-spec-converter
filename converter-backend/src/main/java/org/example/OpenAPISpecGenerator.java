package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OpenAPISpecGenerator {
    public static void generateSpec(Map<String, List<String>> entities, String outputPath) {
        try {
            Map<String, Object> openAPISpec = new LinkedHashMap<>();
            openAPISpec.put("openapi", "3.0.0");

            Map<String, Object> info = new LinkedHashMap<>();
            info.put("title", "Generated API from Draw.io");
            info.put("version", "1.0.0");
            info.put("description", "API dynamically generated from Draw.io XML.");
            openAPISpec.put("info", info);

            Map<String, Object> paths = new LinkedHashMap<>();
            for (String entity : entities.keySet()) {
                Map<String, Object> pathItem = new LinkedHashMap<>();
                Map<String, Object> operation = new LinkedHashMap<>();
                operation.put("summary", "Get all " + entity);
                operation.put("description", "Retrieve all instances of " + entity);

                Map<String, Object> responses = new LinkedHashMap<>();
                Map<String, Object> response200 = new LinkedHashMap<>();
                response200.put("description", "Successful response");
                responses.put("200", response200);

                operation.put("responses", responses);
                pathItem.put("get", operation);
                paths.put("/" + entity.toLowerCase(), pathItem);
            }
            openAPISpec.put("paths", paths);

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.writeValue(new File(outputPath), openAPISpec);

            System.out.println("OpenAPI specification generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
