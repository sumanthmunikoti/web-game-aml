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

import aml.data.Jconnector2;

@WebServlet("/RetrieveUserCommQty")
public class RetrieveUserCommQty extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("In doPost of RetrieveUserCommQty servlet");
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String commodity = writer.toString();
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("uname");
			
			String retrieveUserCommQty;
			retrieveUserCommQty = Jconnector2.retrieveUserCommQty(username, commodity);
			out.println(retrieveUserCommQty);


		} finally {
			out.close();
		}

	}
}