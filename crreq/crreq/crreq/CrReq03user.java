package crreq;


//import java.io.Serializable;

import javax.xml.transform.TransformerException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.w3c.dom.Element;

import xml.PostGetRequests;
import xml.ServiceXml;

public class CrReq03user extends AbstractJavaSamplerClient //implements Serializable
{

	
	
	
	//private static final long serialVersionUID = 1L;


	public String buildXml(JavaSamplerContext context)
    {
    	String reqStr=null;
    	ServiceXml req=new ServiceXml();
    	Element elMain = req.addElement("getUserResp", req.returnDoc());
    		req.addElement("void",elMain);
    		
    	
    	try {
    		reqStr=req.docToString(false,true);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	return reqStr;
    	
    	
    	
    }
	
	
	@Override
    public Arguments getDefaultParameters() {
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument("URL", "http://sles11fc-bp");
        defaultParameters.addArgument("Port", "8080");
        
        defaultParameters.addArgument("URLReqLine", "/offp/fiscred/info/user");
        
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
        					,"Content-Length", String.valueOf(buildXml(context).length())
        					,"Authorization",context.getParameter( "Authorization" )
        					
        					
        	
        					};
        	
        	
        	
        	String req=PostGetRequests.sendPostRequest(urlString, buildXml(context), params,true);
        	
        	result.sampleEnd(); // stop stopwatch
            result.setDataEncoding("UTF-8");
        	result.setResponseData(req);
            if(ServiceXml.checkResponse(req))
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
