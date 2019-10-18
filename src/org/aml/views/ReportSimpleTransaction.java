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

@WebServlet("/ReportSimpleTransaction")
public class ReportSimpleTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("In doPost of ReportSimpleTransaction");
		
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String transactionid = writer.toString();
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
			
			String transactionTypeWithAmount = Jconnector2.checkSimpleTransaction(transactionid);
			out.println(transactionTypeWithAmount);
    
    }

}
