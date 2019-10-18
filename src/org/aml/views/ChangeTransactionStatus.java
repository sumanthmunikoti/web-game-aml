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

@WebServlet("/ChangeTransactionStatus")

public class ChangeTransactionStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("In doPost() of ChangeTransactionStatus servlet");

		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String useridJson = writer.toString();
		//System.out.println("This is the JSon string which has been passed from loadoptionspage.html': "+ useridJson); 
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			
			HttpSession session = request.getSession();
			String receiver = (String) session.getAttribute("uname");
			
			JSONObject jo = new JSONObject(useridJson);
			jo.put("receiver", receiver);
			
			Jconnector.changeTransactionStatus(jo.toString());

		}catch (JSONException e1) {
			System.out.println("*******************************Stack trace: ChangeTransactionStatus servlet**************************");
		}  
		
		finally {
			out.close();
		}

	
	}

}
