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

@WebServlet("/ViewPrivateTransactions")
public class ViewPrivateTransactions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In doPost of ViewPrivateTransactions");
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String theString = writer.toString();

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String transactionDetails = Jconnector2.fetchPrivateTransactionDetails(theString);
		out.println(transactionDetails);
	}
 }
