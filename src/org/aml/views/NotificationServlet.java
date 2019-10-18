package org.aml.views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aml.data.Jconnector;

@WebServlet("/NotificationServlet")
public class NotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NotificationServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("In doPost() of NotificationServlet");
		response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	    try {
	    	HttpSession session = request.getSession();
			String username = (String) session.getAttribute("uname");
			
	    	String notificationString;
	    	notificationString = Jconnector.retrieveNotificationInformation(username);
	    	out.println(notificationString);
	    	
	    } finally {
	        out.close();
	    }
	
	}

}
