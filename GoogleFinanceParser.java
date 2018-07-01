package ua.prog.kiev.lesson2.taskThree;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GoogleFinanceParser {
    private String googleFianaceQueryUrl = "";

    public GoogleFinanceParser(String googleFianaceQueryUrl) throws WrongUrlException {
        this.googleFianaceQueryUrl = googleFianaceQueryUrl;
        if (!isURLValid(googleFianaceQueryUrl)) {
            throw new WrongUrlException();
        }
    }

    public GoogleFinanceParser() {
    }

    public String getGoogleFianaceQueryLink() {
        return googleFianaceQueryUrl;
    }

    public void setGoogleFianaceQueryLink(String googleFianaceQueryLink) {
        this.googleFianaceQueryUrl = googleFianaceQueryLink;
    }

    private Boolean isURLValid(String checkedUrl) {
        try {
            URL url = new URL(googleFianaceQueryUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int responceCode = connection.getResponseCode();
            if (responceCode >= 200 && responceCode < 300) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String cutUselessInfo(String initialXmlResponce) {
        int startSubString = initialXmlResponce.indexOf("<resources");
        int endSubstring = initialXmlResponce.indexOf("</resources>") + "</resources>".length();
        return initialXmlResponce.substring(startSubString, endSubstring);
    }

    public String getGoogleResponce() {
        String xmlResponce = "";
        try {
            int readData;
            URL url = new URL(googleFianaceQueryUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try (InputStream is = urlConnection.getInputStream()) {
                StringBuilder result = new StringBuilder();
                for (; (readData = is.read()) != -1; ) {
                    result.append((char) readData);
                }
                xmlResponce = result.toString();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return xmlResponce;
    }

    private String[] checkAttribute(Node node) {
        if (node.hasAttributes()) {
            //System.out.print(" Attributes: ");
            Node currentNode = node.getAttributes().item(0);
            String attributeValue = currentNode.getNodeValue();
            //System.out.print(attributeValue + "=");
            String nodeValue = node.getTextContent();
            //System.out.println(nodeValue + ";");
            return new String[]{attributeValue, nodeValue};
        }
        return new String[]{"null", "null"};
    }

    private void putDataToResource(Resource currentResource, Map<String, String> map) {
        currentResource.setName(map.get("name"));
        currentResource.setPrice(map.get("price"));
        currentResource.setSymbol(map.get("symbol"));
        currentResource.setTs(map.get("ts"));
        currentResource.setType(map.get("type"));

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        String dateInValidView = "";
        if (map.containsKey("utctime")) {
            dateInValidView = map.get("utctime").substring(0, map.get("utctime").length() - 5);
            Date date = null;
            try {
                date = format.parse(dateInValidView);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            currentResource.setUtctime(date);
        }
        if (map.containsKey("volume")) {
            currentResource.setVolume(Integer.valueOf(map.get("volume")));
        }
    }

    public Resources parseDataByDOM(Resources xmlDOMStructureInObjects, String data) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(data)));
            Element root = document.getDocumentElement();
            //System.out.println("Root Name: " + root.getTagName());
            NodeList resources = root.getChildNodes();
            for (int i = 1; i < resources.getLength(); i++) {
                Node node = resources.item(i);
                //System.out.println("Child Name: " + node.getNodeName() + " " + i + ":");
                Resource resource = new Resource();
                resource.setClassName(checkAttribute(node)[0]);
                Map<String, String> attributeNameNodeValueMap = new HashMap<>();
                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    String[] valuesForMap = checkAttribute(childNode);
                    String attributeName = valuesForMap[0];
                    String nodeValue = valuesForMap[1];
                    attributeNameNodeValueMap.put(attributeName, nodeValue);
                }
                putDataToResource(resource, attributeNameNodeValueMap);
                xmlDOMStructureInObjects.addResource(resource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlDOMStructureInObjects;

    }
}
