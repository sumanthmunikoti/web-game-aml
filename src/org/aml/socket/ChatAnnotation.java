package org.aml.socket;

import java.io.IOException;
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

//@ServerEndpoint(value = "/aml/chat")

public class ChatAnnotation {
	
		public static int counter = 0;
		boolean pmFlag = false;
   		boolean pptyFlag = false;
   		boolean askIfBuyOrSellPpty = false;
   		boolean checkForQty = false;
   		boolean howManyPptyBuy = false;
   		boolean howManyPptySell = false;
        
        private static final String GUEST_PREFIX = "Guest";

        private static final Set<ChatAnnotation> connections =
	            new CopyOnWriteArraySet<>();

	    private final String nickname;
		private Session session;

	    public ChatAnnotation() {
	        nickname = GUEST_PREFIX; //+ connectionIds.getAndIncrement();
	    }
	    
	    @OnOpen
	    public void start(Session session) {
	        this.session = session;
	        connections.add(this);
//	        String message = String.format("* %s %s", nickname, "has joined.");
//	        broadcast(message);
	    }

	    @OnClose
	    public void end() {
	        connections.remove(this);
//	        String message = String.format("* %s %s",
//	        		nickname, "has disconnected.");
//	        broadcast(message);
	    }


	    @OnMessage
	    public void incoming(String message) {
	        // Never trust the client
	    try {
	    	JSONObject jo = new JSONObject(message);
	    	String realMessage = jo.getString("message");
	    	
	    	String sessionId = jo.getString("sessionId").trim();
	        
	        String filteredMessage = ""+sessionId+": "+ realMessage;
	        
	        System.out.println("filteredMessage: "+filteredMessage);
	        		
       		broadcast(filteredMessage);
       		
       		boolean greet = stringsComparator(realMessage, "Hi");
       		boolean greet2 = stringsComparator(realMessage, "Hello");
       		boolean propertyBuy = stringsComparator(realMessage, "property") && stringsComparator(realMessage, "buy");
       		boolean property = stringsComparator(realMessage, "property");
       		boolean preciousMetalsBuy = stringsComparator(realMessage, "precious metals") && stringsComparator(realMessage, "buy");
       		boolean preciousMetals = stringsComparator(realMessage, "precious metals");
       		
       		
       		
       		System.out.println("pptyFlag: "+pptyFlag);
       		System.out.println("askIfBuyOrSellPpty: "+askIfBuyOrSellPpty);
       				
       		if(greet) {
       			broadcast("Bot: Yo");
       		}
       		
       		if(greet2) {
       			broadcast("Bot: Yo");
       		}
  //-----------------------preciousMetals-----------------------------------------------------preciousMetals-----------------------------     		
       		if(preciousMetalsBuy) {
       			if(realMessage.matches(".*\\d+.*")){
       				StringBuilder sb = new StringBuilder();
       				
       				boolean found = false;
       			    
       				for(char c : realMessage.toCharArray()){
       			        if(Character.isDigit(c)){
       			            sb.append(c);
       			            found = true;
       			        } 
       			        else if(found){
       			            // If we already found a digit before and this char is not a digit, stop looping
       			            break;                
       			        }
       			}
       				broadcast("Bot: Are you willing to buy "+sb.toString()+" precious metals?");
       				pmFlag = true;
       			}
       			
       			
       			else {
    			broadcast("Bot: Are you willing to buy precious metals. How many?");
       			}
       	}
       		else if (preciousMetals) {
       			broadcast("Bot: Do you want to buy or sell precious metals?");
       		}

 //-----------------------preciousMetals-----------------------------------------------------preciousMetals-----------------------------
 //-----------------------property-----------------------------------------------------property-----------------    		
       		if(propertyBuy) {
       			if(realMessage.matches(".*\\d+.*")){
       				StringBuilder sb = new StringBuilder();
       				
       				boolean found = false;
       			    
       				for(char c : realMessage.toCharArray()){
       			        if(Character.isDigit(c)){
       			            sb.append(c);
       			            found = true;
       			        } 
       			        else if(found){
       			            // If we already found a digit before and this char is not a digit, stop looping
       			            break;                
       			        }
       			}
       				broadcast("Bot: Are you willing to buy "+sb.toString()+" property?");
       				pptyFlag = true;
       			}
       			else {
    			broadcast("Bot: Are you willing to buy property. How many?");
       			}
       	}
       		else if (property) {
       			broadcast("Bot: Did you just say property? What do you want to do? Buy or Sell?");
       			//start watching if the player wants to buy or sell
       			askIfBuyOrSellPpty = true;
       			
       			
       		}

 //-----------------------property-----------------------------------------------------property----------------------
 //------------------------bot is watching. flag is now true-----------------------------------------------------------       		
   if (pptyFlag) {
	   boolean checkForYes = stringsComparator(realMessage, "Yes");
	   		if(checkForYes) {
	   			broadcast("Bot: Thats good. At what price do you want to buy?");
	   			pptyFlag = false;
//	   			checkForQty = true; //now, you'd want to check for the qty
	   		}
   }     		
      		

   if (askIfBuyOrSellPpty) {
	   boolean checkIfBuy = stringsComparator(realMessage, "buy");
	   boolean checkIfSell = stringsComparator(realMessage, "sell");
  		if(checkIfBuy) {
  			broadcast("Bot: Thats good. How many do you want to buy?");
  			askIfBuyOrSellPpty = false;
  			howManyPptyBuy = true;
  			
  		}
  	
   if(checkIfSell) {
  			broadcast("Bot: Thats good. How many do you want to sell?");
  			askIfBuyOrSellPpty = false;
  			howManyPptySell = true;
  		}
   }
      
   if(howManyPptyBuy) {
	   //check for a number. Take the number and store it in a table
	   //howManyPptyBuy = false;
	   //the table would contain the user's name, the qty he wants to buy, howMany, whatPrice
	   //whatPrice = true;
   }
    
   if(howManyPptySell) {
	   //check for a number. Take the number and store it in a table
	   //howManyPptySell = false;
	   //the table would contain the user's name, the qty he wants to sell, howMany, whatPrice
	   //whatPrice = true;
   }
       		
       		
       		
//-------------------------bot is watching. flag is now true-----------------------------------------------------------
 
       		counter++;
       		
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
	    

	    private static void broadcast(String msg) {
	        //**********you can replace connections with 'specific userid'**************
	    	for (ChatAnnotation client : connections) {
	            try {
	                synchronized (client) {
	                    client.session.getBasicRemote().sendText(msg);
	                }
	            } catch (IOException e) {
	          //      log.debug("Chat Error: Failed to send message to client", e);
	            	System.out.println("Chat Error: Failed to send message to client" + " " + e);
	                connections.remove(client);
	                try {
	                    client.session.close();
	                } catch (IOException e1) {
	                    // Ignore
	                }
	                
	                String message = String.format("* %s %s",
	                        client.nickname, "has been disconnected.");
	                broadcast(message);
	            }
	        }
	    }
	    
	    public static boolean stringsComparator(String src, String what) {
	        final int length = what.length();
	        if (length == 0)
	            return true; // Empty string is contained

	        final char firstLo = Character.toLowerCase(what.charAt(0));
	        final char firstUp = Character.toUpperCase(what.charAt(0));

	        for (int i = src.length() - length; i >= 0; i--) {
	            // Quick check before calling the more expensive regionMatches() method:
	            final char ch = src.charAt(i);
	            if (ch != firstLo && ch != firstUp)
	                continue;

	            if (src.regionMatches(true, i, what, 0, length))
	                return true;
	        }

	        return false;
	    }
}


