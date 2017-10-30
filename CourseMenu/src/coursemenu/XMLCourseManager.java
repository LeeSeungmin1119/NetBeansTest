/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursemenu;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author LeeSeungmin
 */
public class XMLCourseManager 
{
    Document doc;
    private File xmlFile;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    
   public HashMap readXML(String filePath, String fileName) throws IOException
   {
       try
       {
           xmlFile = new File(filePath, fileName);
           dbFactory = DocumentBuilderFactory.newInstance(); 
           dBuilder = dbFactory.newDocumentBuilder(); 
           doc = dBuilder.parse(xmlFile);
           doc.getDocumentElement().normalize();
       }
       catch (Exception e) 
       {
           e.printStackTrace();
       }
       
       return dataConvert();
   }
   
   private HashMap dataConvert()
   {
       HashMap xmlDataHashMap = new HashMap();
       HashMap dataMapper;
       
       NodeList nList = doc.getElementsByTagName("subject");
       if("subject".equals(nList.item(0).getNodeName()))
       {
            for(int i = 0; i < nList.getLength(); i++)
            {
                dataMapper = new HashMap();
                Node nNode = nList.item(i);
                if(nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) nNode;
           
                    dataMapper.put("name", element.getElementsByTagName("name").item(0).getTextContent());
                    dataMapper.put("day", element.getElementsByTagName("day").item(0).getTextContent());
                    dataMapper.put("time", element.getElementsByTagName("time").item(0).getTextContent());
                    dataMapper.put("prof", element.getElementsByTagName("prof").item(0).getTextContent());
                
                    xmlDataHashMap.put(element.getAttribute("id"), dataMapper);
                }
            }
       }
       else
       {
           xmlDataHashMap = null;
       }
       
       return xmlDataHashMap;
   }
    
    public void editXML(String filePath, String fileName, HashMap courseInfo) throws IOException, ParserConfigurationException, TransformerConfigurationException, TransformerException, SAXException
    {
        File xmlFiles = new File(filePath, fileName);
        DocumentBuilderFactory dbFactorys = DocumentBuilderFactory.newInstance(); 
        DocumentBuilder dBuilders = dbFactorys.newDocumentBuilder(); 
        Document docs = dBuilders.parse(xmlFiles);
                       
        Node root = docs.getFirstChild();
        if(root.getNodeType() == Node.ELEMENT_NODE)
        {
            Element subject = docs.createElement("subject"); 
            subject.setAttribute("id", courseInfo.get("id").toString());
            root.appendChild(subject);
        
            Element name = docs.createElement("name"); 
            name.appendChild(docs.createTextNode(courseInfo.get("name").toString())); 
            subject.appendChild(name);
            
            Element day = docs.createElement("day"); 
            day.appendChild(docs.createTextNode(courseInfo.get("day").toString())); 
            subject.appendChild(day);
        
            Element time = docs.createElement("time"); 
            time.appendChild(docs.createTextNode(courseInfo.get("time").toString())); 
            subject.appendChild(time); 
            
            Element prof = docs.createElement("prof"); 
            prof.appendChild(docs.createTextNode(courseInfo.get("prof").toString())); 
            subject.appendChild(prof);
        }
                       
        docs.getDocumentElement().normalize();
        
        DOMSource xmlDOM = new DOMSource(docs); 
        StreamResult xmlResultFile = new StreamResult(new File(filePath, fileName)); 
        TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlResultFile); 
    }
}
