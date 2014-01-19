package xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.File;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.xml.sax.InputSource;

//import org.w3c.dom.Document;

public class PostGetRequests 
{
	
	public static void writeFile(String body,String file) 
	{
    	try {
            //String str = "SomeMoreTextIsHere";
            File newTextFile = new File("C:\\111\\debug\\"+file);

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(body);
            fw.close();

        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
		//StreamResult result = new StreamResult(new File("C:\\111\\file2.xml"));
 
		
		
	}
	
	
	public static Document sendPostRequest(String urlString,Document body,String[] head,boolean declaration)
	{
		//String urlString = "http://sles11fc-bp";;
        //String searchFor = context.getParameter( "SEARCHFOR" );
        InputStream instream;
        Document response = null;
        DocumentBuilderFactory inFactory = null;
        DocumentBuilder inBuilder = null;
        
     
       
        
         
        try 
        {
            java.net.URL url = new java.net.URL(urlString);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection)url.openConnection(); // have to cast connection
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            //connection.setReadTimeout(60000);
            
            //connection.
            
            if(head!=null)
            {
	            for(int i=0;i<head.length;i=i+2)
	            {
	            	connection.setRequestProperty(head[i], head[i+1]);
	            	
	            }
            }
            
            
            OutputStream os = connection.getOutputStream();
            TransformerFactory outtf = TransformerFactory.newInstance();
            Transformer outtrans = outtf.newTransformer();
            
            outtrans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            if(!declaration)
    		{
            	outtrans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
    		}
            
            
            
            outtrans.transform(new DOMSource(body), new StreamResult(os));
            
            //outtrans.setOutputProperty(OutputKeys.);
            
            
            connection.connect();
            
            instream=connection.getInputStream();
            inFactory = DocumentBuilderFactory.newInstance();
            inBuilder = inFactory.newDocumentBuilder();
            response = inBuilder.parse(new InputSource(instream));
           
            connection.disconnect();
            
            
        } catch (Exception e) {
            
 
            // get stack trace as a String to return as document data
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace( new java.io.PrintWriter( stringWriter ) );
            //result.setResponseData( stringWriter.toString() );
            //System.out.println(stringWriter);
            ServiceXml error = new ServiceXml();
            error.addElement("error", stringWriter.toString(), error.returnDoc());
            response = error.returnDoc();
            
            
        }
		
        
        
        return response;
		
		
	}
	
	
	
	
	
	
	public static String sendPostRequest(String urlString,String body,String[] head,boolean dom)
	{
		//String urlString = "http://sles11fc-bp";;
        //String searchFor = context.getParameter( "SEARCHFOR" );
        InputStream instream;
        BufferedReader bfreader = null;
        String response = null;
        
     
       
        
         
        try 
        {
            java.net.URL url = new java.net.URL(urlString);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection)url.openConnection(); // have to cast connection
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setReadTimeout(60000);
            
            
            
            if(head!=null)
            {
	            for(int i=0;i<head.length;i=i+2)
	            {
	            	connection.setRequestProperty(head[i], head[i+1]);
	            	
	            }
            }
            
            String utf8body= new String(body.getBytes("UTF-8"));
            
            //writeFile(utf8body,"01aft");
            //writeFile(body,"01bef");
            
            OutputStream os = connection.getOutputStream();
            
            BufferedWriter osw = new BufferedWriter(new OutputStreamWriter(os));
            if(dom)
            {
	            osw.write(0x0A);
	            osw.write(0x09);
	        }
            osw.write(utf8body);
            osw.flush();
            osw.close();
            
            
            connection.connect();
            instream=connection.getInputStream();
            bfreader=new BufferedReader(new InputStreamReader(instream,"UTF-8"));
            response=bfreader.readLine();
            //writeFile(response,"instr");
            
            connection.disconnect();
            
            
        } 
        
        
        catch (Exception e) {
            
 
            // get stack trace as a String to return as document data
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace( new java.io.PrintWriter( stringWriter ) );
            //result.setResponseData( stringWriter.toString() );
            //System.out.println(stringWriter);
            response = stringWriter.toString();
            
        
        }
		
        
        
        return response;
		
		
	}
	
	
	public static String sendGetRequest(String addr,String[] head)
	{
	//String addr = "http://www.some-url.com";
		String response = null;
	
		try
		{
			URL url = new URL(addr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			//String encoding = new sun.misc.BASE64Encoder().encode("username 	 assword".getBytes());
			//conn.setRequestProperty ("Authorization", "Basic " + encoding);
			if(head!=null)
            {
	            for(int i=0;i<head.length;i=i+2)
	            {
	            	connection.setRequestProperty(head[i], head[i+1]);
	            	
	            }
            }
			connection.setRequestMethod("GET");
		
			connection.connect();
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			connection.disconnect();
			response = reader.readLine();
			//System.out.println(text);
		
			
			}catch(IOException ex)
			{
			ex.printStackTrace();
			System.out.println("made it here");
		}
	return response;
	
	}

	
	
	



}
