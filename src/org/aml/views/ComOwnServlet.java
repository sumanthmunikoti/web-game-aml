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

@WebServlet("/ComOwnServlet")
public class ComOwnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In doPost() of ComOwnServlet");
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String theString = writer.toString();
		System.out.println(theString); 
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("uname");
			JSONObject jo = new JSONObject(theString);
			jo.put("userid", username);
			//Now the Json object contains companyName, legit/shell, userid
			
			Jconnector.insertComOwnDetails(jo.getString("companyName"), jo.getString("entityType"),jo.getString("userid"));
			
		} catch (JSONException e1) {
			System.out.println("********Printed instead of stack trace in doPost of ComOwnServlet***************");
			//e1.printStackTrace();
		}   
		
		finally {
			out.close();
		}
    
    }

}
