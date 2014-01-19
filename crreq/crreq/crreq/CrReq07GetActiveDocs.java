package crreq;

import javax.xml.transform.TransformerException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
//import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xml.PostGetRequests;
import xml.ServiceXml;

public class CrReq07GetActiveDocs extends AbstractJavaSamplerClient
{

	String getClientId(String searchLastName, String searchClientResponseStrXml)
	{
		Node currClient = null;
		Node currClientPerson=null;
		String currClientLastname;
		int count;
		
		ServiceXml searchClientResp = new ServiceXml();
		searchClientResp.stringToDoc(searchClientResponseStrXml);
		Document searchClientRespDoc=searchClientResp.returnDoc();
		
		//DEBUG
		//PostGetRequests.writeFile(searchClientResponseStrXml, "r7d1");
		
		NodeList clients = searchClientRespDoc.getElementsByTagName("clients");
		count=clients.getLength();
		//DEBUG
		//PostGetRequests.writeFile(String.valueOf(count), "r7d2");
		if(count>0)
		{
			for(int i=0 ; i<count; i++)
			{
				currClient = clients.item(i);
				currClientPerson=ServiceXml.getChildNodeByName(currClient, "person");
				currClientLastname=ServiceXml.getChildNodeByName(currClientPerson, "lastName").getTextContent();
				
				//DEBUG
				//PostGetRequests.writeFile(currClientLastname, "r7d3");
				
				
				
				
				if(currClientLastname.equals(searchLastName))
				{
				
					return ServiceXml.getChildNodeByName(currClient, "id").getTextContent();
				
				}
			
			}
		}
		
		return null;
	
	}
	
	
	public ServiceXml buildXml(JavaSamplerContext context)
    {
    	//String reqStr=null;
    	ServiceXml req=new ServiceXml();
    	Element elMain = req.addElement("getClientDocsReq", req.returnDoc());
    	
    	//PostGetRequests.writeFile(context.getParameter("ActiveDocsXml"), "r4d2");
    	
    	
    	
    	req.addElement("clientId",getClientId(context.getParameter("SearchLastname"),context.getParameter("SearchClientXml")),elMain);
    	req.addElement("wsId",context.getParameter("WorkStation"),elMain);
    	
    		
    		
    	
    	/*try {
    		reqStr=req.docToString(false,true);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	//PostGetRequests.writeFile(reqStr, "r7d4");
    	//return reqStr;
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
        defaultParameters.addArgument("SearchLastname","SearchLastnamevariable");
        defaultParameters.addArgument("SearchClientXml","SearchClientXmlvariable");
        defaultParameters.addArgument("WorkStation","ws");
        
        
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
        try {
			result.setRequestHeaders(buildXml(context).docToString(false, false));
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
        					,"Content-Length", String.valueOf(buildXml(context).docToString(false, false).length())
        					,"Authorization",context.getParameter( "Authorization" )
        					
        					
        	
        					};
        	
        	
        	
        	
        	Document req=PostGetRequests.sendPostRequest(urlString, buildXml(context).returnDoc(), params,false);
        	ServiceXml resp = new ServiceXml();
        	resp.addDoc(req);
        	
        	result.sampleEnd(); // stop stopwatch
            result.setDataEncoding("UTF-8");
        	result.setResponseData(resp.docToString(false, false));
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
