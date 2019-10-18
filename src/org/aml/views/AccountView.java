package org.aml.views;

import java.io.FileNotFoundException;
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

@WebServlet("/accountView")
public class AccountView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String uid;

    public AccountView() throws FileNotFoundException {
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String theString = writer.toString();
		System.out.println(theString); 

		try {
			JSONObject ca = new JSONObject(theString);
			uid = ca.getString("userid");
		} catch (JSONException e1) {
			//e1.printStackTrace();
			System.out.println("***************************Stack trace in doPost() of AccountView servlet*************************************");
		}   
		
		
		try {
			JSONObject jo = new JSONObject(theString);
			
			Jconnector.insertUserInformation(jo.getString("userid"),
					jo.getString("firstname"), jo.getString("lastname"),
					jo.getString("password"),jo.getString("gender"),  
					jo.getString("entity"), jo.getString("address"), 
					jo.getString("lineofbusiness"), jo.getString("bankname"), 
					jo.getDouble("cleanmoney"),jo.getDouble("dirtymoney"),	
					jo.getInt("preciousMetals"), jo.getDouble("preciousMetalsPrice"),
					jo.getInt("property"), jo.getDouble("propertyPrice"),
					jo.getInt("artWorks"), jo.getDouble("artPrice"));
		} catch (JSONException e) {
			System.out.println("******************Stack trace in AccountView servlet***************");
			//e.printStackTrace();
		}
		
		if(theString != ""){
			response.setContentType("text/html;charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    
		    try {

				HttpSession session = request.getSession();
				String username = (String) session.getAttribute("uname");
		    	
		    	String retrievedString;
		    	retrievedString = Jconnector.retrieveUserInformation(username);
		    	out.println(retrievedString);
		    	
		    } finally {
		        out.close();
		    }
		}
	}
}    

