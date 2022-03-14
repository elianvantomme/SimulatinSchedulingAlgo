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
import java.util.ArrayList;

public class XMLparser {

    private ArrayList<Process> processLijst;
    private String file;



    public XMLparser(String file) {
        this.processLijst = new ArrayList<>();
        this.file = file;
    }


    public ArrayList<Process> readProcesses() {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(file));

            document.getDocumentElement().normalize();

            NodeList processList = document.getElementsByTagName("process");
            for (int i = 0; i < processList.getLength(); i++) {
                Node processNode = processList.item(i);

                if (processNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList processParams = processNode.getChildNodes();

                    Process process = new Process();
                    for (int j = 0; j < processParams.getLength(); j++) {
                        Node param = processParams.item(j);

                        if (param.getNodeType() == Node.ELEMENT_NODE) {
                            Element detailElement = (Element) param;
                            if (detailElement.getNodeName().equals("pid")){
                                process.setpId(Integer.parseInt(detailElement.getTextContent()));
                            }else if (detailElement.getNodeName().equals("arrivaltime")){
                                process.setArrivaltime(Integer.parseInt(detailElement.getTextContent()));
                            }else{
                                process.setServiceTime(Integer.parseInt(detailElement.getTextContent()));
                            }
                        }
                    }
                    processLijst.add(process);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return processLijst;

    }
}

