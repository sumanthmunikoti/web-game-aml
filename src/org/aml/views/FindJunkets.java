package org.aml.views;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import aml.data.Jconnector;

@WebServlet("/findJunkets")

public class FindJunkets extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("In doPost of findJunkets");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("uname");
			
			String junketReturnString;
			String retrieveNetwork;
			retrieveNetwork = Jconnector.retrieveNetwork(username);
			junketReturnString = Jconnector.findJunkets(retrieveNetwork,username.trim());
			out.println(junketReturnString);


		} finally {
			out.close();
		}

	}
}




////----------------------------------Function to load non collabs----------------------------------------------------------------
//
////function loadJunket() {
////console.log("clicked button");
//	
//			$.ajax({
//
//				url : "findJunkets",
//				type : 'POST',
//				contentType : 'application/json',
//				//data: mystr, -----> this is the type of data you send
//				dataType : 'text',
//				success : function(response) {
//					console.log("success");
//					var type = typeof (response);
//					console.log(response);
//					console.log(type);
//					response = JSON.parse(response);
//					
//					var j = 0;
//					
//					for (i = 0; i < response.length; i++) {
//
//						console.log(i);
//						var jsoni = response[i];
//						console.log(jsoni);
//						var userid = jsoni.userid;
//						var inNetwork = jsoni.inNetwork;
//						var serialNumber = i+1;
//
//						var type = typeof (jsoni);
//						
//						console.log(i);
//						console.log(userid);
//						console.log(inNetwork);
//						
//							//if(inNetwork.trim()!=("This one's your buddy!")){
//									//console.log("The j: "+j);
//										//sendMoneyFunction(serialNumber +","+ userid +","+ inNetwork+","+i+","+j);
//										//j++;
//							//}
//							
//							//else{
//								var x = document.getElementById('junkets-list').insertRow(i+1);
//								
//								var c1 = x.insertCell(0);
//								var c2 = x.insertCell(1);
//								var c3 = x.insertCell(2);
//
//								c1.innerHTML = "<td>"+serialNumber+"</td>";
//								c2.innerHTML = "<td>"+userid+"</td>";
//								c3.innerHTML = '<td><button style="width:100%" onclick="sendRequest(\'' + userid + '\')">'+ inNetwork + '</button></td>';
//							//}
//
//					}//end for 
//				},
//
//				error : function(xhr, ajaxOptions, thrownError) {
//					//On error do this
//					if (xhr.status == 200) {
//						console.log("error junk if");
//						console.log(xhr);
//						//alert(ajaxOptions);
//					} else {
//						console.log("error junk else");
//						console.log(xhr);
//					}
//				}
//			});

