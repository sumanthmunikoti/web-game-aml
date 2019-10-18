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

@WebServlet("/CollaboratorServlet")
public class CollaboratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CollaboratorServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("In doPost of CollaboratorServlet");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("uname");
			
			String collaboratorsReturnString;
			collaboratorsReturnString = Jconnector.findCollaborators(username);
			String retrieveCollaboratorDetails;
			retrieveCollaboratorDetails = Jconnector.retrieveCollaboratorDetails(collaboratorsReturnString);
			out.println(retrieveCollaboratorDetails);

		} finally {
			out.close();
		}

    
    }

}
