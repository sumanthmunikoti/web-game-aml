package org.aml.views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import aml.data.Jconnector2;

@WebServlet("/Fetchdynamicprices")
public class Fetchdynamicprices extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			
			int[] retrievePrices = Jconnector2.fetchDynamicPrices();
			JSONObject jo = new JSONObject();
			
			jo.put("p1", retrievePrices[0]);
			jo.put("p2", retrievePrices[1]);
			jo.put("p3", retrievePrices[2]);
			
			jo.put("p4", retrievePrices[3]);
			jo.put("p5", retrievePrices[4]);
			jo.put("p6", retrievePrices[5]);
			
			jo.put("p7", retrievePrices[6]);
			jo.put("p8", retrievePrices[7]);
			jo.put("p9", retrievePrices[8]);
			
			jo.put("p10", retrievePrices[9]);
			jo.put("p11", retrievePrices[10]);
			jo.put("p12", retrievePrices[11]);
			
			out.println(jo.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

	}
}
