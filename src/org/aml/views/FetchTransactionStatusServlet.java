package org.aml.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import aml.data.Jconnector;

@WebServlet("/FetchTransactionStatusServlet")
public class FetchTransactionStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("In doPost() of FetchTransactionStatusServlet servlet");

		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String userid = writer.toString();
		System.out.println("This is the userid passed from HomeScreen.html': "+ userid); 
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession();
			String sessionUser = (String) session.getAttribute("uname");
			int counter = Jconnector.fetchTransactionStatus(userid, sessionUser);
			if (counter == 1) {
				out.println("waiting");
			}
			if (counter == 0 ) {
				out.println("no waiting");
			}
	
	}

}
