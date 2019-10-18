package org.aml.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import aml.data.Jconnector;

@WebServlet("/AddSenderReceiver")
public class AddSenderReceiver extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddSenderReceiver() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String responseIsrri="Meh";
		System.out.println("In doPost() of AddSenderReceiver");
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String theString = writer.toString();
		System.out.println(theString); 
		
		try {
			JSONObject jo = new JSONObject(theString);
			responseIsrri = Jconnector.insertSenderReceiverRequestInfo(jo.getString("receiver"), jo.getString("sender"),jo.getString("status"));
			String responseString;
			System.out.println("responseIsrri: "+responseIsrri); 
			
			response.setContentType("text/html;charset=UTF-8");
		    PrintWriter out = response.getWriter();
	    	
		    if (responseIsrri.equals("this")){
	    	responseString = "this";
		    }
	    	
	    	else
	    	{
	    		responseString = "Meh";	
	    	}
	    	out.println(responseString);
	    	out.close();
	    	    	
		} 
		    catch (JSONException e) {
			e.printStackTrace();
		}
		
		if(theString != ""){
			
		    
		    try {
		    	
		    	}
		    finally {
		        
		    }
		}
		
	}

}
