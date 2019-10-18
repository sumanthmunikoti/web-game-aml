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
import org.json.JSONException;
import org.json.JSONObject;

import aml.data.Jconnector;

@WebServlet("/TransactionDetailsServlet")
public class TransactionDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In doPost of TransactionDetailsServlet");
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String theString = writer.toString();
		System.out.println("This is the userid in TransactionDetailsServlet which has been passed from TheCopPage.html: "+theString); 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			HttpSession session = request.getSession();
			String sessionUser = (String) session.getAttribute("uname");
			
			String transactionDetails = Jconnector.fetchCompleteTransactionDetails(theString, sessionUser);
			out.println(transactionDetails);
		}  

		finally{
		out.close();
		}
	}
}
