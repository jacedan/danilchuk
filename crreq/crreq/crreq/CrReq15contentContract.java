package crreq;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import xml.PostGetRequests;
import xml.ServiceXml;

public class CrReq15contentContract extends AbstractJavaSamplerClient
{

	ServiceXml req, statusResponse;
		
	
	String getContentId(String contentName)
	{
		NodeList contentsList = statusResponse.returnDoc().getElementsByTagName("contents");
		String currentName;
		String contentId;
		for(int i=0;i<contentsList.getLength();i++)
		{
			currentName = ServiceXml.getChildNodeByName(contentsList.item(i), "name").getTextContent();
			if(currentName.equals(contentName))
			{
				contentId = ServiceXml.getChildNodeByName(contentsList.item(i), "id").getTextContent();
				return contentId;
			}
		}
		return null;
		
	}
	
	void buildXml(JavaSamplerContext context)
	{
		statusResponse=new ServiceXml();
		statusResponse.stringToDoc(context.getParameter("StatusResponseXml"));
		String contentId = getContentId(context.getParameter("ContentName"));
		
		req=new ServiceXml();
		Element getContentReq = req.addElement("getContentReq", req.returnDoc());
		req.addElement("id", contentId, getContentReq);
		req.addElement("type", "contract", getContentReq);
	
	}
	
	public Arguments getDefaultParameters() 
	{
        Arguments defaultParameters = new Arguments();
        defaultParameters.addArgument("URL", "http://sles11fc-bp");
        defaultParameters.addArgument("Port", "8080");
        
        defaultParameters.addArgument("URLReqLine", "/offp/fiscred/docflow/content");
        
        defaultParameters.addArgument("Host", "sles11fc-bp:8080");
        defaultParameters.addArgument("X-clientVersion","13.5.108.20131010-1539");
        defaultParameters.addArgument("X-ouId","100");
        defaultParameters.addArgument("Content-Type","text/xml;charset=UTF-8");
        defaultParameters.addArgument("User-Agent","Jakarta Commons-HttpClient/3.1");
        defaultParameters.addArgument("Authorization","Basic b3A6b3A=");
        
        
        
        defaultParameters.addArgument("StatusResponseXml","varStatusResponseXml");
        defaultParameters.addArgument("ContentName","ContentName");
        
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
        
        buildXml(context);
        
     
        SampleResult result = new SampleResult();
        try {
			result.setRequestHeaders(req.docToString(false, false));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
        result.sampleStart(); // start stopwatch
        
         
        try {
            
        	String params[]={"Host",context.getParameter( "Host" ) 
        					,"X-ouId",context.getParameter( "X-ouId" )
        					,"X-clientVersion",context.getParameter( "X-clientVersion" )
        					,"Content-Type",context.getParameter( "Content-Type" )
        					,"User-Agent",context.getParameter( "User-Agent" )
        					,"Content-Length", String.valueOf(req.docToString(false, false).length())
        					,"Authorization",context.getParameter( "Authorization" )
        					
        					
        	
        					};
        	
        	
        	
        	
        	Document reqDoc=PostGetRequests.sendPostRequest(urlString, req.returnDoc(), params,false);
        	ServiceXml resp = new ServiceXml();
        	resp.addDoc(reqDoc);
        	
        	
        	
        	result.sampleEnd(); // stop stopwatch
            result.setDataEncoding("UTF-8");
        	result.setResponseData(resp.docToString(false, false));
            if(ServiceXml.checkResponse(resp.docToString(false, false)))
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
            PostGetRequests.writeFile(stringWriter.toString(), "err");
            result.setDataType( org.apache.jmeter.samplers.SampleResult.TEXT );
            result.setResponseCode( "500" );
        }
 
        return result;
    }
	
	
	
	


}
