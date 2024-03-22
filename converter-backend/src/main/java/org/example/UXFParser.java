package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UXFParser {

    public static Map<String, List<String>> parseUXF(InputStream uxfInputStream) {
        Map<String, List<String>> entities = new HashMap<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(uxfInputStream);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("element");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String type = element.getElementsByTagName("type").item(0).getTextContent();

                // Check if it's a class element
                if ("com.baselet.element.old.element.Class".equals(type)) {
                    // Panel attributes contain the class name and properties
                    String panelAttributes = element.getElementsByTagName("panel_attributes").item(0).getTextContent();
                    processPanelAttributes(panelAttributes, entities);
                }
                // Extend this condition to handle other types like relations if necessary
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }

    private static void processPanelAttributes(String panelAttributes, Map<String, List<String>> entities) {
        String[] lines = panelAttributes.split("\n");
        if (lines.length > 0) {
            String className = lines[0].trim();
            List<String> properties = new ArrayList<>();
            for (int i = 1; i < lines.length; i++) {
                // You can further refine this logic to separate properties and methods if needed
                properties.add(lines[i].trim());
            }
            entities.put(className, properties);
        }
    }
}
