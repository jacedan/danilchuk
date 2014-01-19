package crreq;

import java.io.File;





import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xml.PostGetRequests;
import xml.ServiceXml;

public class CrReq14zExecute extends AbstractJavaSamplerClient
{
	 
	
	ServiceXml buildXml(JavaSamplerContext context)
	{
		ServiceXml req=new ServiceXml();
		String filename = context.getParameter("AppFormFilename");
		try 
		{
			File currDir = new File(CrReq14zExecute.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			
			req.getDocumentFromFile(currDir.getParent()+File.separator+filename);
		} 
		catch (Exception e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document reqDoc=req.returnDoc();
		
		
		
		Node flow = reqDoc.getElementsByTagName("flow").item(0);
		flow.setTextContent(context.getParameter("Flow"));
		
		
		
		try
		{
			Node openDate = reqDoc.getElementsByTagName("openDate").item(0);
			openDate.setTextContent(ServiceXml.getCurrentTimeStamp());
		}
		catch(NullPointerException e)
		{
		}
		
		try
		{
			Node templateRef = reqDoc.getElementsByTagName("templateRef").item(0);
			Node revision = ServiceXml.getChildNodeByName(templateRef, "revision");
			revision.setTextContent(context.getParameter("Revision"));
			Node templateId = ServiceXml.getChildNodeByName(templateRef, "id");
			templateId.setTextContent(context.getParameter("templateId"));
		}
		catch(NullPointerException e)
		{
		}	
		
		
		Node clientId = reqDoc.getElementsByTagName("clientId").item(0);
		clientId.setTextContent(context.getParameter("ClientId"));
		
		try
		{
			NodeList paramsList = reqDoc.getElementsByTagName("params");
			//PostGetRequests.writeFile(String.valueOf(paramsList.getLength()), "r14d4");
			
			for(int i = 0;i<paramsList.getLength();i++)
			{
				Node p=paramsList.item(i);
				//PostGetRequests.writeFile(p.getAttributes().item(0).getTextContent(),"r14dt"+String.valueOf(i));
				if(p.getAttributes().item(0).getTextContent().equals("xs:string"))
				{
					p.setTextContent(context.getParameter("Workstation"));
					break;
				}
			}
			
		}
		catch(NullPointerException e)
		{
		}
		
		
		//ws.setTextContent(context.getParameter("Workstation"));
		
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
        
        
        
        defaultParameters.addArgument("ClientId","varClientId");
        defaultParameters.addArgument("Revision","varRevision");
        defaultParameters.addArgument("templateId","vartemplateId");
        defaultParameters.addArgument("Flow","varFlow");
        defaultParameters.addArgument("Workstation","ws");
        defaultParameters.addArgument("AppFormFilename","AppFormFilename");
        
        
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
