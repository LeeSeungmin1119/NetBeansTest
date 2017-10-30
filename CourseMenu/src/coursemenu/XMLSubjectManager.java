/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursemenu;

import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author LeeSeungmin
 */
public class XMLSubjectManager 
{
    private File xmlFile;
    DocumentBuilderFactory dbFactory;
    DocumentBuilder dBuilder;
    Document doc;
    
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
       
       return xmlDataHashMap;
   }
   
   public void editXML(String filePath, String fileName, String id) throws IOException, ParserConfigurationException, 
           SAXException, TransformerConfigurationException, TransformerException
   {
       xmlFile = new File(filePath, fileName);
       dbFactory = DocumentBuilderFactory.newInstance(); 
       dBuilder = dbFactory.newDocumentBuilder(); 
       doc = dBuilder.parse(xmlFile);
       
       NodeList nList = doc.getElementsByTagName("subject");
       for(int i = 0; i < nList.getLength(); i++)
       {
           Node nNode = nList.item(i);
           if(nNode.getNodeType() == Node.ELEMENT_NODE)
           {
               Element element = (Element) nNode;             
               if(element.getAttribute("id").equals(id))
               {
                   nNode.getParentNode().removeChild(nNode);
               }
           }
       }
       
       doc.getDocumentElement().normalize();
        
       DOMSource xmlDOM = new DOMSource(doc); 
       StreamResult xmlResultFile = new StreamResult(new File(filePath, fileName)); 
       TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlResultFile); 
   }
}
