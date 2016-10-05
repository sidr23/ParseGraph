import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;

public class ParseGraph {
    public static void main(String args[]) {

        HashMap<String, String> idValueMap = new HashMap<>();
        HashMap<String, ArrayList<String>> adjacencyMap = new HashMap<>();

        try {
            File fXmlFile = new File("src/com/company/TestX.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("mxCell");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String currentId = eElement.getAttribute("id");
                    if(eElement.getAttribute("value") == "") {
                        if(((eElement.getAttribute("source") == "") && (eElement.getAttribute("target") != "")) ||((eElement.getAttribute("source") != "") && (eElement.getAttribute("target") == "")))
                        {
                            System.out.println("Arrows aren't connected properly" + " Source: "+ eElement.getAttribute("source") + " Target: " + eElement.getAttribute("target") + " id: " + eElement.getAttribute("id"));
                        }
                        else
                        {
                            String currentSource = eElement.getAttribute("source");
                            String currentTarget = eElement.getAttribute("target");
                            if ("" != currentSource && "" != currentTarget) {
                                if (adjacencyMap.containsKey(currentSource)) {
                                    ArrayList<String> adjacencyList = adjacencyMap.get(currentSource);
                                    if (!adjacencyList.contains(currentTarget)) {
                                        adjacencyList.add(currentTarget);
                                        adjacencyMap.put(currentSource, adjacencyList);
//									for (int i=0;i< adjacencyList.size();i++)
//									System.out.println(currentSource + " " + adjacencyList.get(i));
                                    }
                                } else {
                                    adjacencyMap.put(currentSource, new ArrayList<String>(){{add(currentTarget);}});
                                }
                            }
                        }
                    }
                    else {
                        idValueMap.put(currentId, eElement.getAttribute("value"));
                    }
                }

            }

            for (Map.Entry<String, ArrayList<String>> entry : adjacencyMap.entrySet()) {
                String fromNode = entry.getKey();
                ArrayList<String> toNodeList = entry.getValue();

                System.out.print(idValueMap.get(fromNode));
                System.out.print(" (Number of Outbound links=" + toNodeList.size() + ")   ---> [");
                for (String toNode: toNodeList) {
                    System.out.print(idValueMap.get(toNode) + "  ");
                }
                System.out.print("]\n\n");
            }

            int v=idValueMap.size();

            System.out.println(idValueMap);
            System.out.println(adjacencyMap);
			/*
			for (Map.Entry<String, ArrayList<String>> entry2 : adjacencyMap.entrySet()) {
			    String X = entry2.getKey();
		    System.out.println(X);

			}*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
