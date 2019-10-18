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

import aml.data.Jconnector2;

@WebServlet("/SimpleCheckUserId")
public class SimpleCheckUserId extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String unameFromLoginForm = writer.toString();
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String stringFromJconnector = Jconnector2.checkSimpleUserId(unameFromLoginForm);
		int stringToInt = Integer.parseInt(stringFromJconnector);
		System.out.println("This should be 0 or 1: "+stringFromJconnector);
		//out.println(unameFromLoginForm);
		
		//The below output(1 or 0) is sent to the loginform.html
		if (stringToInt == 0){
			//do something
			out.println(0);
		}
		else{
			out.println(1);
			
		}
	}	
}

