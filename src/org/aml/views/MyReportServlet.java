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

@WebServlet("/MyReportServlet")
public class MyReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In doPost of MyReportServlet");

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			HttpSession session = request.getSession();
			String sessionUser = (String) session.getAttribute("uname");
			
			String myReportStr = Jconnector.generateReport(sessionUser);
			out.println(myReportStr);
		}  

		finally{
		out.close();
		}
	}
}