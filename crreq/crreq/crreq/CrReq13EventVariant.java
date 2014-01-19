package crreq;





import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import xml.PostGetRequests;
import xml.ServiceXml;

public class CrReq13EventVariant extends AbstractJavaSamplerClient
{
	
	
	ServiceXml buildXml(JavaSamplerContext context)
	{
		
		
		ServiceXml req=new ServiceXml();
    	Element elMain = req.addElement("invokeDocflowReq", req.returnDoc());
    	req.addAtributeWithPrefix("xmlns","xsi","http://www.w3.org/2001/XMLSchema-instance",elMain);
    	req.addAtributeWithPrefix("xmlns","xs","http://www.w3.org/2001/XMLSchema",elMain);
    	req.addElement("flow", context.getParameter("Flow"), elMain);
    	req.addElement("state", "SelectVariant", elMain);
    	req.addElement("event", "select", elMain);
    	Element paramsWs = req.addElement("params", context.getParameter("Workstation"), elMain);
    		req.addAtributeWithPrefix("xsi","type","xs"+':'+"string",paramsWs);
    	req.addElementFromNode(selectedVariant (context), elMain);
    	Element paramsApp = req.addElement("params", elMain);
    		req.addAtributeWithPrefix("xsi","type","creditApplication",paramsApp);
    		req.addAtributeWithPrefix("xsi","nil","true",paramsApp);
    	
    	
		
		return req;
		
    	
    	
    	
    	
    
	}
	
	
	Node selectedVariant (JavaSamplerContext context)
	{
		ServiceXml selectedVariant = new ServiceXml();
		selectedVariant.stringToDoc(context.getParameter("DocsStatusRespXml"));
		try {
			PostGetRequests.writeFile(selectedVariant.docToString(false, false), "db13f2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Node variant = selectedVariant.returnDoc().getElementsByTagName("variants").item(0);
		selectedVariant = new ServiceXml();
		selectedVariant.addElementFromNode(variant, selectedVariant.returnDoc());
		Element variantNode = (Element) selectedVariant.returnDoc().getFirstChild();
		selectedVariant.renameNode(variantNode,"params");
		selectedVariant.addAtributeWithPrefix("xsi", "type", "creditVariant", variantNode);
		
		
		return variantNode;
		
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
        
        defaultParameters.addArgument("DocsStatusRespXml","applicationFormXmlStr");
        defaultParameters.addArgument("Flow","varFlow");
        defaultParameters.addArgument("Workstation","ws");
        
        
        
        
        
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
        
        ServiceXml reqServiceXml = buildXml(context);
        
     
        SampleResult result = new SampleResult();
        try {
			result.setRequestHeaders(reqServiceXml.docToString(false, false));
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
        					,"Content-Length", String.valueOf(reqServiceXml.docToString(false, false).length())
        					,"Authorization",context.getParameter( "Authorization" )
        					
        					
        	
        					};
        	
        	
        	
        	
        	Document req=PostGetRequests.sendPostRequest(urlString, reqServiceXml.returnDoc(), params,false);
        	ServiceXml resp = new ServiceXml();
        	resp.addDoc(req);
        	
        	
        	
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
