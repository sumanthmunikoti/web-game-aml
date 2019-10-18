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

@WebServlet("/SendMoneyServlet")
public class SendMoneyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("In doPost() of SendMoneyServlet");
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
			jo.put("sender", username);
			
			//String senderReceiverMoney = jo.toString();
			//jo.getString("type") should be passed to the function in the below statement
			String sendMoneyConfirmString = Jconnector.theRealSendMoneyFunc(jo.getString("receiver"), jo.getString("sender"),jo.getString("commodity"),jo.getDouble("commPrice"),jo.getInt("commQty"),jo.getString("status"),jo.getString("type"));
			//responseIsrri = Jconnector.insertSenderReceiverRequestInfo(jo.getString("receiver"), jo.getString("sender"),jo.getString("status"));
			
			out.println(sendMoneyConfirmString);
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		}   
		
		finally {
			out.close();
		}
    
    }

}
