package crreq;



import javax.xml.transform.TransformerException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xml.PostGetRequests;
import xml.ServiceXml;

public class CrReq12execute extends AbstractJavaSamplerClient  
{

	
	
	
	ServiceXml buildXml(JavaSamplerContext context)
	{
		ServiceXml req=new ServiceXml();
    	Element elMain =   	req.addElementWithPrefix("rpc","batch", req.returnDoc());
    		req.addAtributeWithPrefix("xmlns", "rpc", "http://www.fisgroup.ru/offp/rpc", elMain);
    	
    	Element rpcCall = req.addElementWithPrefix("rpc", "call", elMain);
    		req.addAtribute("method", "/docflow/event", rpcCall);
    	
    	Element invokeDocflowReq = req.addElement("invokeDocflowReq", rpcCall);
	    	req.addAtributeWithPrefix("xmlns","xsi","http://www.w3.org/2001/XMLSchema-instance",invokeDocflowReq);
	    	req.addAtributeWithPrefix("xmlns","xs","http://www.w3.org/2001/XMLSchema",invokeDocflowReq);
    	
    	
    	req.addElement("flow", context.getParameter("Flow"), invokeDocflowReq);
    	req.addElement("state", "FillInCreditApplicationForm", invokeDocflowReq);
    	req.addElement("event", "submit", invokeDocflowReq);
    	req.addElementFromNode(buildParams(context), invokeDocflowReq);
    	
    	Element rpcCallGetClient = req.addElementWithPrefix("rpc", "call", elMain);
			req.addAtribute("method", "/client/get", rpcCallGetClient);
		Element getClientReq = req.addElement("getClientReq", rpcCallGetClient);
		req.addElement("clientId",context.getParameter("ClientId") ,getClientReq);
			
    	
    	
			return req;
	}
	
	
	Node buildParams(JavaSamplerContext context)
	{
		ServiceXml getLatestAppFormRespServiceXml = new ServiceXml();
		getLatestAppFormRespServiceXml.stringToDoc(context.getParameter("ApplicationFormXml"));
		
		Node appFormResp = getLatestAppFormRespServiceXml.returnDoc().getFirstChild().getFirstChild();
		//Debug
		//PostGetRequests.writeFile(appFormResp.getNodeName(), "r12d01");
		
		ServiceXml paramsServiceXml = new ServiceXml();
		Element params = paramsServiceXml.addElement("params", paramsServiceXml.returnDoc()); // main
			paramsServiceXml.addAtributeWithPrefix("xmlns","xsi","http://www.w3.org/2001/XMLSchema-instance", params);
			paramsServiceXml.addAtributeWithPrefix("xsi","type","creditApplicationForm", params);
			
		
		paramsServiceXml.addElement("openDate",ServiceXml.getCurrentTimeStamp() ,params);
		
		
		
		Node personNodeFromGetLatestAppFormResp = ServiceXml.getChildNodeByName(appFormResp, "person");
		paramsServiceXml.addElementFromNode(personNodeFromGetLatestAppFormResp, params);
		
		try
		{
			NodeList identityCardsFromGetLatestAppFormResp = ServiceXml.getChildNodesByName(appFormResp, "identityCards");
			for(int i=0 ; i < identityCardsFromGetLatestAppFormResp.getLength();i++)
			{
				ServiceXml.removeElementByNameFromNode(identityCardsFromGetLatestAppFormResp.item(i), "id");
				paramsServiceXml.addElementFromNode(identityCardsFromGetLatestAppFormResp.item(i), params);
				
			}
			
			NodeList attributesFromGetLatestAppFormResp = ServiceXml.getChildNodesByName(appFormResp, "attributes");
			Node valueNode;
			for(int i=0 ; i < attributesFromGetLatestAppFormResp.getLength();i++)
			{
				valueNode=ServiceXml.getChildNodeByName(attributesFromGetLatestAppFormResp.item(i), "value");
				ServiceXml.removeElementByNameFromNode(valueNode, "creator");
				ServiceXml.removeElementByNameFromNode(valueNode, "editor");
				ServiceXml.removeElementByNameFromNodeRecursive(attributesFromGetLatestAppFormResp.item(i), "id");
				
				Element attributes = paramsServiceXml.addElementFromNode(attributesFromGetLatestAppFormResp.item(i), params);
					paramsServiceXml.addAtributeWithPrefix("xmlns","xsi","http://www.w3.org/2001/XMLSchema-instance", attributes);
			}
		}
		
		
		catch(NullPointerException e)
		{
			
		}
		
		Node templateRefNodeFromGetLatestAppFormResp = getLatestAppFormRespServiceXml.returnDoc().getElementsByTagName("templateRef").item(0);
		paramsServiceXml.addElementFromNode(templateRefNodeFromGetLatestAppFormResp, params);
		
		
		
		
		return paramsServiceXml.returnDoc().getFirstChild();
		
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
        
        defaultParameters.addArgument("ApplicationFormXml","applicationFormXmlStr");
        defaultParameters.addArgument("Flow","varFlow");
        defaultParameters.addArgument("ClientId","varClientId");
        
        
        
        
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
		} catch (TransformerException e1) {
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
            result.setDataType( org.apache.jmeter.samplers.SampleResult.TEXT );
            result.setResponseCode( "500" );
        }
 
        return result;
    }


}
