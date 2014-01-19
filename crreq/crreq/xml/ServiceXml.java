package xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ServiceXml 
{

	private Document doc;
	
	public static Node getChildNodeByName(Node parentNode,String nodeName)
	{
		for(Node currChild=parentNode.getFirstChild();currChild!=null;currChild=currChild.getNextSibling())
		{
			if(nodeName.equals(currChild.getNodeName())) return currChild;
			//aa
		}
	return null;
	}
	
	
	public static void removeElementByNameFromNode(Node parentNode, String nodeName)
	{
		Node currChild;
		for(int i=0;i<parentNode.getChildNodes().getLength();i++)
		{
			currChild=parentNode.getChildNodes().item(i);
			if(nodeName.equals(currChild.getNodeName()))
				{
					parentNode.removeChild(currChild);
					i--;
				}
		
		}
	}
	
	
	public static void removeElementByNameFromNodeRecursive(Node parentNode, String nodeName)
	{
		Node currChild;
		for(int i=0;i<parentNode.getChildNodes().getLength();i++)
		{
			
				currChild=parentNode.getChildNodes().item(i);
				if(nodeName.equals(currChild.getNodeName()))
				{
					parentNode.removeChild(currChild);
					i--;
				}
				else
				{
					if(currChild.hasChildNodes())
					{
						ServiceXml.removeElementByNameFromNodeRecursive(currChild , nodeName);
					}
				}
		
		}
	}
	
	
	
	
	public static NodeList getChildNodesByName(Node parentNode,String nodeName)
	{
		
		
		ServiceXml findedNodesServiceXml = new ServiceXml();
		Element findedNodesElement = findedNodesServiceXml.addElement("findedNodesElement", findedNodesServiceXml.returnDoc());
		
		
		
		for(Node currChild=parentNode.getFirstChild();currChild!=null;currChild=currChild.getNextSibling())
		{
			if(nodeName.equals(currChild.getNodeName()))
			{
				findedNodesServiceXml.addElementFromNode(currChild, findedNodesElement);
			}
		}
		
		
		
		return findedNodesElement.getChildNodes();
	
	
	}
	
	
	
	public static String getCurrentTimeStamp() 
	{
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	
	
	public static boolean checkResponse(String response)
	{
		if (response.contains("Exception")) return false;
		return true;
	}
	
	
		
	public ServiceXml() 
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// root elements
		doc = docBuilder.newDocument();
		
	
	
	}
	
	public Element addElement(String name, Node childof)
	{
		Element element = doc.createElement(name);
		childof.appendChild(element);
		return element;
			
	
	}
	
	public Element addElement(String name,String value ,Node childof)
	{
		Element element = doc.createElement(name);
		element.appendChild(doc.createTextNode(value));
		childof.appendChild(element);
		return element;
			
	
	}
	
	
	
		
	public Element addElementWithPrefix(String prefix,String name,Node childof)
	{
		
		Element element = doc.createElement(prefix+':'+name);
		childof.appendChild(element);
		return element;
	
	}
	
	public Element addElementWithPrefix(String prefix, String name,String value ,Node childof)
	{
		Element element = doc.createElement(prefix+':'+name);
		element.appendChild(doc.createTextNode(value));
		childof.appendChild(element);
		return element;
		
	}
	
	public void renameNode(Node elem,String newName)
	{
		doc.renameNode(elem, elem.getNamespaceURI(), newName);
	}
	
	
	
	
	
	/*public Node addNode(Node node, Node childof)
	{
		Node importNode = doc.importNode(node, true);
		childof.appendChild(importNode);
		
		return importNode;
			
	
	}*/
	
	public Element addElementFromNode(Node node, Node childof)
	{
		
		try
		{
			
			//Node importNode = doc.importNode(node, true);
			//Node importNode = node.cloneNode(true);
			//Node importNode = doc.importNode(node.cloneNode(true), true);
			Node importNode = doc.adoptNode(node.cloneNode(true));
			Element importElement = (Element)importNode;
			childof.appendChild(importElement);
			return importElement;
		}
		catch(Exception e)
		{
			return null;
		}
	
	}
	
	

	
	
	
	
	
	
	
	
	
	
	public void addAtribute(String attrName,String attrValue  ,Element setNode)
	{
		
		Attr attr = doc.createAttribute(attrName);
		attr.setValue(attrValue);
		setNode.setAttributeNode(attr);
		
	}
	
	
	
	public void addAtributeWithPrefix(String prefix, String attrName,String attrValue  ,Element setNode)
	{
		
		Attr attr = doc.createAttribute(prefix +':'+ attrName);
		attr.setValue(attrValue);
		setNode.setAttributeNode(attr);
		
	}
	
	
	
	public void stringToDoc(String xmlSource)     
	{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			doc = builder.parse(new InputSource(new StringReader(xmlSource)));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
	
	public Document returnDoc()
	{
		return doc;
	}
	
	public void addDoc(Document addDoc)
	{
		doc=addDoc;
	}
	
	
	
	
	
	public String docToString (boolean declaration,boolean html) throws TransformerException
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		if(!declaration)
		{
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			
		}
		if(html)
		{
			transformer.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, "html");
		}
		transformer.transform(source, new StreamResult(writer));
		
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		return output;
	
	
	}
	
	public void getDocumentFromFile (String filename)
	{
	    try {

	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	        factory.setIgnoringComments(true);
	        factory.setIgnoringElementContentWhitespace(true);
	        factory.setValidating(false);

	        DocumentBuilder builder = factory.newDocumentBuilder();
	        doc = builder.parse(new InputSource(filename));        
	    }
	    catch (Exception e){
	        System.out.println("Error reading configuration file:");
	        System.out.println(e.getMessage());
	    }
	    
	}
	



}
