package org.aml.socket;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/games/dock")
@WebServlet(name="DockSocket", loadOnStartup=1, description = "This servelt loads the UI for the game. It takes care of the interface.", urlPatterns = { "/DockSocket" })
public class DockSocket extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public static LinkedBlockingDeque<DockSocket> waitList = new LinkedBlockingDeque<>();

	private Session me = null;
	private String myid = "";
	public DockSocket() {

	}

	@OnOpen
	public void onOpen(Session session) {
		synchronized (waitList) {
			try {
				this.me = session;
				myid = session.getRequestParameterMap().get("myid").get(0);
				if (waitList.size() == 0) {
					waitList.add(this);
				} else {
					DockSocket partner = waitList.pollFirst();
					String pmyid = partner.myid;
					partner.me.getBasicRemote().sendText("HomeScreen2.0.html?myid="+pmyid+"&pmyid="+myid);
					this.me.getBasicRemote().sendText("HomeScreen2.0.html?myid="+myid+"&pmyid="+pmyid);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	} 

	@OnMessage
	public void onTextMessage(String message) {

	}

	@OnClose
	public void onClose() {		
		System.out.println("Closing :");
	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		int count = 0;
		Throwable root = t;
		System.out.println("onError: Closing :");		

		while (root.getCause() != null && count < 20) {
			root = root.getCause();
			count ++;
		}
		if (root instanceof EOFException) {
		} else {
			throw t;
		}
	}

}