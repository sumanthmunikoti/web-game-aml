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

import aml.data.Jconnector;


@WebServlet("/ChangeStatus")
public class ChangeStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public ChangeStatus() {
        super();
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In doPost() of ChangeStatus servlet");

		
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String unameFromLoadOptionsPage = writer.toString();
		System.out.println("This is the string 'uname which has been passed from loadoptionspage.html': "+ unameFromLoadOptionsPage); 
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("uname");
//			session id = receiver of the collaboration request who will accept it now
// 			unameFromLoadOptionsPage is the person whose collaboration request is now being accepted
			Jconnector.changeStatus(unameFromLoadOptionsPage,username);

		} finally {
			out.close();
		}
		
	}


}
