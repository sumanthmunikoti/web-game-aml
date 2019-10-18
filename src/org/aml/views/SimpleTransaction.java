package org.aml.views;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import aml.data.Jconnector2;

@WebServlet("/SimpleTransaction")
public class SimpleTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In doPost() of SimpleTransaction servlet");

		StringWriter writer = new StringWriter();
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String useridJson = writer.toString();

		try {

			
			
			JSONObject jo = new JSONObject(useridJson);
			Jconnector2.insertSimpleTransactions(jo.getString("sender"),jo.getString("valuableQty"),jo.getString("commodity"), jo.getString("receiver"),jo.getString("alerted"),jo.getString("reported"));
			
			JSONObject jsonObject = new JSONObject(Jconnector2.retrieveUserInformation(jo.getString("sender"))) ;
			Jconnector2.updateCmpBonus(jo.getString("sender"), Integer.parseInt(jsonObject.getString("cleanmoney")), Integer.parseInt(jsonObject.getString("dirtymoney")), jsonObject.getInt("preciousMetals"), jsonObject.getInt("property"), jsonObject.getInt("artWorks"));
			
		} catch (JSONException e1) {
			System.out.println(
					"*****************************Stack trace: SimpleTransaction Servlet**************************");
		}
	}
}
