package org.aml.views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aml.data.Jconnector2;

@WebServlet("/RetrieveAssetValues")
public class RetrieveAssetValues extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			response.setContentType("text/html;charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    
		    try {

				HttpSession session = request.getSession();
				String username = (String) session.getAttribute("uname");
		    	
		    	String retrievedString;
		    	retrievedString = Jconnector2.retrieveUserInformation(username);
		    	out.println(retrievedString);
		    	
		    } finally {
		        out.close();
		    }
		}
	}

