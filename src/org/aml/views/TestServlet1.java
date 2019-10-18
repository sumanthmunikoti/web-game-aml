package org.aml.views;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

@WebServlet("/TestServlet1")
public class TestServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public TestServlet1() {
        super();
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//	doGet(request, response);
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String uname = writer.toString();

		
		HttpSession session = request.getSession();
		
		//if (session.isNew()==false){
			session.setAttribute("uname", uname);
		//}
		RequestDispatcher rd = request.getRequestDispatcher("/TestServlet2");
		rd.forward(request, response);
	}

}
