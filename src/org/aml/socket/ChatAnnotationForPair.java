package org.aml.socket;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
//import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import aml.data.Jconnector2;

@ServerEndpoint(value = "/aml/chat")

public class ChatAnnotationForPair  {
	
        private static final String GUEST_PREFIX = "Guest";
        public static int counter = 0;
        String playerID;
        
        private static final Set<ChatAnnotationForPair> playerConnections =
	            new CopyOnWriteArraySet<>();
        
//	    private final String nickname;
		private Session session;

	    public ChatAnnotationForPair() {
//	        nickname = GUEST_PREFIX; //+ connectionIds.getAndIncrement();
	    }
	    
	    @OnOpen
	    public void start(Session session) {
	        this.session = session;
	        this.playerID = session.getRequestParameterMap().get("myid").get(0);
	        playerConnections.add(this);
//	        String partnerID = session.getRequestParameterMap().get("pmyid").get(0);
//	        String message = String.format("* %s %s", nickname, "has joined.");
//	        broadcast(message);
	    }

	    @OnClose
	    public void end() {
	        playerConnections.remove(this);
//	        String message = String.format("* %s %s",
//	        		nickname, "has disconnected.");
//	        broadcast(message);
	    }

	    @OnMessage
	    public void incoming(String message) {
//	    	System.out.println("The message you're looking for: "+message);
	    	
	        // Never trust the client
	    try {
	    	JSONObject jo = new JSONObject(message);
	    	String realMessage = jo.getString("chat");
	    	System.out.println("chat part of the JSON: "+realMessage);
	    	
	    	JSONObject joMessage = new JSONObject(realMessage);
	    	String stringMessage = joMessage.getString("message");
	    	System.out.println("The message part of message: "+stringMessage);
	    	
	    	String sessionId = joMessage.getString("sessionId").trim();
	    	System.out.println("sessionId: "+sessionId);
	    	
	        String filteredMessage = ""+sessionId+": "+ stringMessage;
	        broadcastToPairs(filteredMessage, session);
       		
	        System.out.println("counter: "+counter);
       		counter++;
       		System.out.println("counter: "+counter);
       		
       		if(counter % 10==0) {
       		Jconnector2.changeDynamicPrices();
       		}
	    }

	    	catch (Exception ex) {
			System.out.println("______________Stack trace in ChatAnnotation________________");
			ex.printStackTrace();
	    	} 
	    }
	    

	    @OnError
	    public void onError(Throwable t) throws Throwable {
	        //log.error("Chat Error: " + t.toString(), t);
	    	System.out.println("Chat Error: " + t.toString() + " " + t);
	    }
	    
	    	    
	    //---------------------------------------Ramu's function-------------------------------------------------
	    private static void broadcastToPairs(String msg, Session session) {
	    	System.out.println("Inside broadcastToPairs function");
			for (ChatAnnotationForPair client : playerConnections) {
				try {
					synchronized (client) {
//						System.out.println("client.playerID: "+client.playerID);
//						System.out.println("client.session: "+client.session);
//						System.out.println("session.getRequestParameterMap().get(\"myid\").get(0): "+session.getRequestParameterMap().get("myid").get(0));
//						System.out.println("session.getRequestParameterMap().get(\"pmyid\").get(0): "+session.getRequestParameterMap().get("pmyid").get(0));
						
						if (client.playerID.equals(session.getRequestParameterMap().get("myid").get(0)) ||
								client.playerID.equals(session.getRequestParameterMap().get("pmyid").get(0)))
							client.session.getBasicRemote().sendText(msg);
					}
				} catch (IOException e) {
					System.out.println("Chat Error: Failed to send message to client" + " " + e);
					playerConnections.remove(client);
					try {
						session.close();
					} catch (IOException e1) {
						// Ignore
					}

				}
			}
		}
	    //----------------------------------------------------------------------------------------------------------------
	    
}