package crreq;

import javax.xml.transform.TransformerException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import xml.PostGetRequests;
import xml.ServiceXml;

public class CrReq10GetStatus extends AbstractJavaSamplerClient 
{

	public ServiceXml buildXml(JavaSamplerContext context,String ifModifiedSince)
    {
    	
    	String docRefs = context.getParameter("docRefs");
    	
    	
    	//PostGetRequests.writeFile(docRefs, "r10d1");
    	
    	ServiceXml req=new ServiceXml();
    	Element elMain = req.addElement("getDocsStatusReq", req.returnDoc());
    	Element eldocRefs = null;
    	
    	if((docRefs!=null)&&(docRefs.length()>0))
    	{
    		String[]docRefsArray = docRefs.split(";");
    		
    		//PostGetRequests.writeFile(String.valueOf(docRefsArray.length), "r10d2");
    		if(docRefsArray.length>1)
    		{
    			for(int i=0;i<docRefsArray.length;i=i+2)
    			{
    				eldocRefs = req.addElement("docRefs", elMain);
    				req.addElement("type",docRefsArray[i] , eldocRefs);
    				req.addElement("id",docRefsArray[i+1] , eldocRefs);
    			
    			}
    		}
    		
    	
    	
    	}
    	
    	if((ifModifiedSince!=null)&&(!ifModifiedSince.equals("-1")))
    	{
    		req.addElement("ifModifiedSince", ifModifiedSince, elMain);
    	}
    
    	return req;
    
    }
	
	
	
	
	@Override
    public Arguments getDefaultParameters() 
	{
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument("URL", "http://sles11fc-bp");
        defaultParameters.addArgument("Port", "8080");
        
        defaultParameters.addArgument("URLReqLine", "/offp/fiscred/config/global");
        
        defaultParameters.addArgument("Host", "sles11fc-bp:8080");
        defaultParameters.addArgument("X-clientVersion","13.5.108.20131010-1539");
        defaultParameters.addArgument("X-ouId","100");
        defaultParameters.addArgument("Content-Type","text/xml;charset=UTF-8");
        defaultParameters.addArgument("User-Agent","Jakarta Commons-HttpClient/3.1");
        defaultParameters.addArgument("Authorization","Basic b3A6b3A=");
        
        defaultParameters.addArgument("docRefs","format type;id");
        defaultParameters.addArgument("ifModifiedSince","-1");
        defaultParameters.addArgument("Cycle count","20");
        defaultParameters.addArgument("Pause(ms)","1000");
        defaultParameters.addArgument("checkElementName","availability");
        defaultParameters.addArgument("checkElementValue","AVAILABLE");
        
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
        
        String ifModifiedSince = context.getParameter("ifModifiedSince");
        String checkElementName = context.getParameter("checkElementName");
        String checkElementValue = context.getParameter("checkElementValue");
        
        
        SampleResult result = new SampleResult();
        try {
			result.setRequestHeaders(buildXml(context, ifModifiedSince).docToString(false, false));
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        result.sampleStart(); // start stopwatch
        
         
        try {
            
        	
        	
        	
        	Boolean available=false;
        	String availability;
        	ServiceXml resp = new ServiceXml();
        	int count = Integer.parseInt(context.getParameter("Cycle count"));
        	int i=0;
        	
        	
        	while(i<count)//&&(!available))//(!availability.equals("AVAILABLE")))        		
        	{
        		String params[]={"Host",context.getParameter( "Host" ) 
    					,"X-ouId",context.getParameter( "X-ouId" )
    					,"X-clientVersion",context.getParameter( "X-clientVersion" )
    					,"Content-Type",context.getParameter( "Content-Type" )
    					,"User-Agent",context.getParameter( "User-Agent" )
    					,"Content-Length", String.valueOf(buildXml(context,ifModifiedSince).docToString(false, false).length())
    					,"Authorization",context.getParameter( "Authorization" )
    					
    					
    	
    					};
        		
        		Document req=PostGetRequests.sendPostRequest(urlString, buildXml(context,ifModifiedSince).returnDoc(), params,true);
	        	
	        	resp.addDoc(req);
	        	try
	        	{
	        		
	        		availability = resp.returnDoc().getElementsByTagName(checkElementName).item(0).getTextContent();
	        		if(availability.equals(checkElementValue)) available = true;
	        	
	        	}
	        	catch(NullPointerException e)
	        	{
	        		available=false;
	        	}
	        	
	        	try
	        	{
	        		ifModifiedSince = resp.returnDoc().getElementsByTagName("lastModified").item(0).getNodeValue();
	        	}
	        	catch(Exception e)
	        	{
	        		ifModifiedSince=null;
	        	}
	        	
	        	if(available) break;
	        	
	        	Thread.sleep(Integer.parseInt(context.getParameter("Pause(ms)")));
	        	i++;
        	}
        	
        	
        	
        	result.sampleEnd(); // stop stopwatch
            result.setDataEncoding("UTF-8");
            if(available && ServiceXml.checkResponse(resp.docToString(false, false)))
            {
	        	result.setSuccessful( true );
	            result.setResponseCodeOK();
            }// 200 code
            else
            {
            	result.setSuccessful( false );
            }
            result.setResponseMessage( "count="+String.valueOf(i));
            result.setResponseData(resp.docToString(false, false));
        } catch (Exception e) {
            result.sampleEnd(); // stop stopwatch
            result.setSuccessful( false );
            result.setResponseMessage( "Exception: " + e+ "URLStr:"+urlString );
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            e.printStackTrace( new java.io.PrintWriter( stringWriter ) );
            
            result.setDataType( org.apache.jmeter.samplers.SampleResult.TEXT );
            result.setResponseCode( "500" );
        }
 
        return result;
    }
	



}
