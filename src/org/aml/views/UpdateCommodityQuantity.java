package org.aml.views;

import java.io.IOException;
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

import aml.data.Jconnector2;

@WebServlet("/UpdateCommodityQuantity")
public class UpdateCommodityQuantity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("In  doPost of UpdateCommodityQuantity");
		StringWriter writer = new StringWriter();
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String useridJson = writer.toString();
		
	try {

			HttpSession session = request.getSession();
			String sessionUser = (String) session.getAttribute("uname");
			JSONObject jo = new JSONObject(useridJson);
			jo.put("sessionUser", sessionUser);
			
			Jconnector2.updateCommodityQuantity(jo.toString());
	}
	
	catch (JSONException e1) {
		System.out.println(
				"*****************************Stack trace: UpdateCommodityQuantity Servlet**************************");
		}
	}
}