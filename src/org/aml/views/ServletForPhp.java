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

import aml.data.Jconnector;

@WebServlet("/ServletForPhp")
public class ServletForPhp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("In doPost() of ServletForPhp");
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String theString = writer.toString();
		System.out.println(theString); 
		
		response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	    int isUsernameAvailable = Jconnector.checkUsernameAvailability(theString);
	    System.out.println("The value of isUsernameAvailable is: "+isUsernameAvailable);
	    
	    if (isUsernameAvailable==0){
	    out.println("OK");
	    }
	    else if (isUsernameAvailable==1){
		    out.println("This username is taken");
		    } 
	    	
	}

}
