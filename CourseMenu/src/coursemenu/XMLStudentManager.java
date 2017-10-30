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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author LeeSeungmin
 */
public class XMLStudentManager 
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
       HashMap dataMapper = new HashMap();
       
       NodeList nList = doc.getElementsByTagName("student");
       for(int i = 0; i < nList.getLength(); i++)
       {
           Node nNode = nList.item(i);
           if(nNode.getNodeType() == Node.ELEMENT_NODE)
           {
                Element element = (Element) nNode;
                
                dataMapper.put("id", element.getAttribute("id"));
                dataMapper.put("name", element.getElementsByTagName("name").item(0).getTextContent());
                dataMapper.put("year", element.getElementsByTagName("year").item(0).getTextContent());
                dataMapper.put("semester", element.getElementsByTagName("semester").item(0).getTextContent());
           }
       }
       
       return dataMapper;
   }
}
