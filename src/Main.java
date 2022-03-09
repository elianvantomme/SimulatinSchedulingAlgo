import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File("processen50000.xml"));

            document.getDocumentElement().normalize();

            NodeList processList = document.getElementsByTagName("process");
            for (int i = 0; i < processList.getLength(); i++) {
                Node process = processList.item(i);

                if (process.getNodeType() == Node.ELEMENT_NODE){
                    NodeList processParams = process.getChildNodes();
                    for (int j = 0; j < processParams.getLength(); j++) {
                        Node param = processParams.item(j);

                        if (param.getNodeType() == Node.ELEMENT_NODE){
                            Element detailElement = (Element) param;
                            System.out.println("        "+detailElement.getTagName()+":"+detailElement.getTextContent());
                        }
                    }
                    System.out.println("\n");
                }
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
