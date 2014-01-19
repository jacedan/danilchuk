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

public class CrReq02activeDocs extends AbstractJavaSamplerClient implements Serializable
{
	





	
	 
	
	    private static final long serialVersionUID = 1L;
	    
	    // set up default arguments for the JMeter GUI
	    
	    public ServiceXml buildXml(JavaSamplerContext context)
	    {
	    	
	    	ServiceXml req=new ServiceXml();
	    	Element elMain = req.addElement("getClientDocsReq", req.returnDoc());
	    		req.addElement("void",elMain);
	    		
	    	
	    	
	    	
	    	//writeFile(req.returnDoc());
	    	return req;
	    	
	    	
	    	
	    }
	    
	    
	    
	    @Override
	    public Arguments getDefaultParameters() {
	        Arguments defaultParameters = new Arguments();
	        defaultParameters.addArgument("URL", "http://sles11fc-bp");
	        defaultParameters.addArgument("Port", "8080");
	        //defaultParameters.addArgument("Workstation", "ws11");
	        
	        //defaultParameters.addArgument("SEARCHLASTNAME", "ÈÂÀÍÎÂ");
	        
	        defaultParameters.addArgument("URLReqLine", "/offp/fiscred/docflow/active-docs");
	        
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
	        
	        
	     
	        SampleResult result = new SampleResult();
	        result.sampleStart(); // start stopwatch
	         
	        try {
	            
	        	String params[]={"Host",context.getParameter( "Host" ) 
	        					,"X-ouId",context.getParameter( "X-ouId" )
	        					,"X-clientVersion",context.getParameter( "X-clientVersion" )
	        					,"Content-Type",context.getParameter( "Content-Type" )
	        					,"User-Agent",context.getParameter( "User-Agent" )
	        					,"Content-Length", String.valueOf(buildXml(context).docToString(false, false).length())
	        					,"Authorization",context.getParameter( "Authorization" )
	        					
	        					
	        	
	        					};
	        	
	        	
	        	
	        	Document req=PostGetRequests.sendPostRequest(urlString, buildXml(context).returnDoc(), params,false);
	        	ServiceXml resp = new ServiceXml();
	        	resp.addDoc(req);
	            
	        	result.sampleEnd(); // stop stopwatch
	            result.setDataEncoding("UTF-8");
	        	result.setResponseData(resp.docToString(false, false));;
	        	if(!resp.returnDoc().getFirstChild().getNodeName().equals("error"))
	            {
	            	result.setSuccessful( true );
	            	result.setResponseCodeOK();
	            	result.setResponseMessage( "Successfully performed action!");
	            }
	            else
	            {
	            	result.setSuccessful( false );
	            	result.setResponseCode( "Server error!!!" );
	            }
	            
	        } catch (Exception e) {
	            result.sampleEnd(); // stop stopwatch
	            result.setSuccessful( false );
	            //result.
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
