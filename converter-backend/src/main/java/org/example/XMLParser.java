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

public class XMLParser {

    public static Map<String, List<String>> parseXML(InputStream xmlInputStream) {
        Map<String, List<String>> entities = new HashMap<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlInputStream);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("mxCell");
            String currentEntity = null;

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String value = element.getAttribute("value");
                String style = element.getAttribute("style");
                if (style != null && style.contains("swimlane")) {
                    currentEntity = value;
                    entities.putIfAbsent(value, new ArrayList<>());
                } else if (currentEntity != null && value != null && !value.trim().isEmpty()) {
                    entities.get(currentEntity).add(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entities;
    }
}
