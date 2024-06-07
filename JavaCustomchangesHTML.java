import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModifyHTML {
    public static void main(String[] args) {
        try {
            // Load the HTML file
            File inputFile = new File("input.html");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            // Find the <h2> element with specified text content
            NodeList nodes = doc.getElementsByTagName("h2");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                if (element.getTextContent().startsWith("Details")) {
                    // Find the <section> element following the <h2> element
                    Node sectionNode = element.getNextSibling();
                    while (sectionNode != null && !(sectionNode instanceof Element && sectionNode.getNodeName().equals("section"))) {
                        sectionNode = sectionNode.getNextSibling();
                    }

                    // Remove the <section> element and its contents
                    if (sectionNode != null) {
                        sectionNode.getParentNode().removeChild(sectionNode);
                        break; // Remove only the first occurrence
                    }
                }
            }

            // Save the modified document
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("output.html"));
            transformer.transform(source, result);
            System.out.println("HTML file modified successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
