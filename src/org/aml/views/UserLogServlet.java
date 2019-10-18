package org.aml.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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

@WebServlet("/UserLogServlet")
public class UserLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(dateFormat.format(date)); 
		String dateTime = "" + dateFormat.format(date);
		
		
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String theString = writer.toString();
//		System.out.println(theString); 

		try {
			JSONObject jo = new JSONObject(theString);
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("uname");
			Jconnector.logUserDetails(username,jo.getString("clientTimeStamp"),dateTime, jo.getString("eventName"),jo.getString("value"));
			//implement session here
			
		} catch (JSONException e) {
		
			e.printStackTrace();
		}
		
		if(theString != ""){
			response.setContentType("text/html;charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    
		    try {
		    	out.println("Nada");
		    } finally {
		        out.close();
		    }
		}
	}


}

