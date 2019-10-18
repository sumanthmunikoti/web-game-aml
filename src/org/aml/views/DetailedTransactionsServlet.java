package org.aml.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
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

@WebServlet("/DetailedTransactionsServlet")
public class DetailedTransactionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In doPost() of DetailedTransactionsServlet servlet");

		StringWriter writer = new StringWriter();
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String useridJson = writer.toString();
		System.out.println("This is the JSon string which has been passed from HomeScreen.html': " + useridJson);

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String myDateString = sdf.format(myDate);
		System.out.println("date: " + myDateString);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println("timestamp: " + timestamp.getTime());
		double time = (double) (timestamp.getTime()%10E5);

		try {

			HttpSession session = request.getSession();
			String receiver = (String) session.getAttribute("uname");

			JSONObject jo = new JSONObject(useridJson);
			jo.put("receiver", receiver);
			jo.put("date", myDateString); // string
			jo.put("transactionid", time); // double

			Jconnector.insertDetailedTransactions(jo.toString());

		} catch (JSONException e1) {
			System.out.println(
					"*****************************Stack trace: DetailedTransactions Servlet**************************");
		}

		finally {
			out.close();
		}

	}
}
