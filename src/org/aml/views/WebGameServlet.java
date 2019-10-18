package org.aml.views;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import aml.data.ValidateAndPair;
import aml.data.User;

/**
 * Servlet implementation class WebGameServelet
 */
@WebServlet(name="WebGameServlet", loadOnStartup=1, description = "This servlet loads the UI for the game. It takes care of the interface.", urlPatterns = { "/WebGameServlet" })
public class WebGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	public static final String dbSchema = "rdgmap";
//	public static final String dbPass = "corefmysql.backend";
//	public static final String dbUsername = "coref";
//	public static final String dbURL = "jdbc:mysql://localhost:3306/rdgmap";
	//public static final String dbURL = "jdbc:mysql://coast.ict.usc.edu:3306/coref2";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("In doPost() of WebGameServlet");
		ServletContext ctx = request.getServletContext();
		// Fetch the map containing the pair ID maps to the users.		
		
		StringWriter writer = new StringWriter();  
		IOUtils.copy(request.getInputStream(), writer, "UTF-8");
		String userID = writer.toString();
		System.out.println("This is the string unamePasswordStr which has been passed from loginform.html': "+ userID); 

//		String userID = request.getParameter("uname");
		//'uname' is got from SimpleLoginForm.html
		System.out.println("userID: "+userID);
		
		HttpSession session = request.getSession();
		session.setAttribute("user", userID);
		Cookie userName = new Cookie("user", userID);
		Cookie startTime = new Cookie("begin_time", String.valueOf(System.currentTimeMillis()));
		
		response.addCookie(userName);
		response.addCookie(startTime);
		
		User user = new User(userID, "", "");
		
		ArrayList<User> usersTillNow = (ArrayList<User>)ctx.getAttribute("users_connected_till_now");
		if (usersTillNow == null) {
			usersTillNow = new ArrayList<>();			
		}
		usersTillNow.add(user);
		ctx.setAttribute("users_connected_till_now", usersTillNow);
		
		User userInBucket = (User)ctx.getAttribute("USER_BUCKET");
		
//		if (ValidateAndPair.hasPlayerPlayed(WebGameServlet.dbURL, WebGameServlet.dbUsername, WebGameServlet.dbPass, userID)); {
//			userID = userID + String.valueOf(System.currentTimeMillis()).substring(8);
		}
		
	private void addUser(ConcurrentLinkedQueue<User> usersOnlineAndWaiting, String userID){
		if(usersOnlineAndWaiting == null) {
			usersOnlineAndWaiting = new ConcurrentLinkedQueue<>();
			User newUser = new User();
			newUser.setUserID(userID);
			usersOnlineAndWaiting.add(newUser);
		} else {
			boolean presentAlready = false;
			for(User u: usersOnlineAndWaiting) {
				if(u.getUserID().equals(userID)) {
					presentAlready = true;
				}
			}
			if(!presentAlready) {
				User newUser = new User();
				newUser.setUserID(userID);
				usersOnlineAndWaiting.add(newUser);
			}
		}
	}


	public static String getClientIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }
	
}
