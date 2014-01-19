package crreq;





import java.io.Serializable;



import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xml.PostGetRequests;
import xml.ServiceXml;
 
public class CrReq01Search extends AbstractJavaSamplerClient implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // set up default arguments for the JMeter GUI
    
    public ServiceXml buildXml(JavaSamplerContext context)
    {
    	//String reqStr=null;
    	ServiceXml req=new ServiceXml();
    	Element elMain = req.addElement("searchClientReq", req.returnDoc());
    	Element current=req.addElement("current", elMain);
    		req.addElement("maxClientCount", "3", current);
    		req.addElement("lastName", context.getParameter( "SEARCHLASTNAME" ), current);
    		req.addElement("wsId", context.getParameter( "Workstation" ), current);
    	
    	/*try {
    		reqStr=req.docToString(false,false);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	//writeFile(req.returnDoc());
    	return req;
    	
    	
    	
    }
    
    
    /*public void writeFile(String body) 
	{
    	try {
            //String str = "SomeMoreTextIsHere";
            File newTextFile = new File("C:\\111\\file2.xml");

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(body);
            fw.close();

        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
		//StreamResult result = new StreamResult(new File("C:\\111\\file2.xml"));
 
		
		
	}*/
    
    
    
    
    
    @Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument("URL", "http://sles11fc-bp");
        defaultParameters.addArgument("Port", "8080");
        defaultParameters.addArgument("Workstation", "ws11");
        
        defaultParameters.addArgument("SEARCHLASTNAME", "ÈÂÀÍÎÂ");
        
        defaultParameters.addArgument("URLReqLine", "/offp/fiscred/client/search");
        
        defaultParameters.addArgument("Host", "sles11fc-bp:8080");
        defaultParameters.addArgument("X-clientVersion","13.5.108.20131010-1539");
        defaultParameters.addArgument("X-ouId","100");
        defaultParameters.addArgument("Content-Type","text/xml;charset=UTF-8");
        defaultParameters.addArgument("User-Agent","Jakarta Commons-HttpClient/3.1");
        defaultParameters.addArgument("Authorization","Basic b3A6b3A=");
        
        
        return defaultParameters;
    }
 
    @SuppressWarnings("deprecation")
	@Override
    public SampleResult runTest(JavaSamplerContext context) {
        // pull parameters
        String urlString = context.getParameter( "URL" );
        urlString=urlString.concat(":");
        urlString=urlString.concat(context.getParameter("Port"));
        urlString=urlString.concat(context.getParameter("URLReqLine"));
        //String searchFor = context.getParameter( "SEARCHFOR" );
        //InputStream instream;
        //BufferedReader bfreader;
        
        
        SampleResult result = new SampleResult();
        result.sampleStart(); // start stopwatch
         
        try {
            /*java.net.URL url = new java.net.URL(urlString + "?q=" + searchFor);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection)url.openConnection(); // have to cast connection
            connection.setRequestMethod("GET");
            connection.connect();
            instream=connection.getInputStream();
            bfreader=new BufferedReader(new InputStreamReader(instream));*/
        	String params[]={"Host",context.getParameter( "Host" ) 
        					,"X-ouId",context.getParameter( "X-ouId" )
        					,"X-clientVersion",context.getParameter( "X-clientVersion" )
        					,"Content-Type",context.getParameter( "Content-Type" )
        					,"User-Agent",context.getParameter( "User-Agent" )
        					,"Content-Length", String.valueOf(buildXml(context).docToString(false, false))
        					,"Authorization",context.getParameter( "Authorization" )
        					
        					
        	
        					};
        	
    		result.setRequestHeaders(buildXml(context).docToString(false, false));
    		
        	
        	
        	Document req=PostGetRequests.sendPostRequest(urlString, buildXml(context).returnDoc(), params,false);
        	ServiceXml resp = new ServiceXml();
        	resp.addDoc(req);
        	
        	result.sampleEnd(); // stop stopwatch
        	result.setDataEncoding("UTF-8");
            
            
            result.setResponseData(resp.docToString(false, false));
             // 200 code
            if(!resp.returnDoc().getFirstChild().getNodeName().equals("error"))
            {
            	result.setResponseMessage( "Successfully performed action!");
            	result.setSuccessful( true );
            	result.setResponseCodeOK();
            }
            else
            {
            	result.setSuccessful( false );
            	result.setResponseCode( "Server error!!!" );
            }
            
            
        } catch (Exception e) {
            result.sampleEnd(); // stop stopwatch
            result.setSuccessful( false );
            
            result.setResponseMessage( "Exception: " + e+ "URLStr:"+urlString );
 
            // get stack trace as a String to return as document data
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace( new java.io.PrintWriter( stringWriter ) );
            //result.setResponseData( stringWriter.toString() );
            result.setDataType( org.apache.jmeter.samplers.SampleResult.TEXT );
            result.setResponseCode( "500" );
        }
 
        return result;
    }
}