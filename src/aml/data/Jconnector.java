package aml.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Jconnector {
	
	public static void main(String[] args) {
		System.out.println("in main");
	}
	
	public static int fetchTransactionStatus(String userid, String sessionUser){
		System.out.println("In fetchTransactionStatus method now");

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		int counter = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();
			
			String sql = "select status from aml.transactionstatus where receiver = '"+userid+"' and sender = '"+sessionUser+"' and status = 'waiting';";
			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {
				counter++;
			} // end while

			conn.close();
			stSetLimit.close();
			return counter;
		} // end try
		catch (Exception ex) {
			System.out.println("******************Stack trace in fetchTransactionStatus*************************");
			return 5;
		} // end catch

	}


	//-------------------------code to insert comOwnDetails-----------------------------
	public static String logUserDetails(String username,String clientTimeStamp, String serverTimeStamp, String eventName ,String value) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "INSERT INTO userlogdetails (`username`,`clientTimeStamp`,`serverTimeStamp`,`eventName`,`value`) VALUES(?,?,?,?,?);";

//			System.out.println("username: "+username);
//			System.out.println("clientTimeStamp: "+clientTimeStamp);
//			System.out.println("serverTimeStamp: "+serverTimeStamp);
//			System.out.println("eventName: "+eventName);
//			System.out.println("value: "+value);
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, clientTimeStamp);
			statement.setString(3, serverTimeStamp);
			statement.setString(4, eventName);
			statement.setString(5, value);

			int row = statement.executeUpdate();

			if (row > 0) {
				//System.out.println("A request was inserted (logUserDetails worked. Yaay !!!).");
				return "It worked";
			} // end if
			conn.close();
		} // end try
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in userlogdetails()_________**************");
			ex.printStackTrace();
			return "this";
			 
		} // end catch
		return "obligation";
	}// end sendMoneyFunc	

	
	//----------------Code to retrieve money details for NewHomePage.html
	
	public static String retrieveMoneyDetails(String collaboratorId){
		System.out.println("In retrieveMoneyDetails method now");

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();
			
			JSONObject jo = new JSONObject();

			String sql = "SELECT userid,first_name,last_name,address,lob, bank, clean_money, dirty_money, preciousMetals, preciousMetalsPrice, property, propertyPrice, artWorks, artPrice FROM aml.useraccount where userid = '"+collaboratorId+"';";
			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {
//				String cleanMoney = rs.getString(1);
//				String dirtyMoney = rs.getString(2);
//				
//				jo.put("cleanMoney", cleanMoney);
//				jo.put("dirtyMoney", dirtyMoney);
				
				String userid = rs.getString(1);
				String first_name = rs.getString(2);
				String last_name = rs.getString(3);
				String address = rs.getString(4);
				String lob = rs.getString(5);
				String bank = rs.getString(6);
				String clean_money = rs.getString(7);
				String dirty_money = rs.getString(8);
				String preciousMetals = rs.getString(9);
				String preciousMetalsPrice = rs.getString(10);
				String property = rs.getString(11);
				String propertyPrice = rs.getString(12);
				String artWorks = rs.getString(13);
				String artPrice = rs.getString(14);

				jo.put("userid", userid);
				jo.put("first_name", first_name);
				jo.put("last_name", last_name);
				jo.put("address", address);
				jo.put("lob", lob);
				jo.put("bank", bank);
				jo.put("clean_money", clean_money);
				jo.put("dirty_money", dirty_money);
				jo.put("preciousMetals", preciousMetals);
				jo.put("preciousMetalsPrice", preciousMetalsPrice);
				jo.put("property", property);
				jo.put("propertyPrice", propertyPrice);
				jo.put("artWorks", artWorks);
				jo.put("artPrice", artPrice);

			} // end while

			conn.close();
			stSetLimit.close();
			return jo.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}
		
	
//Code to generate transaction report on HomeScreen.html	
//------------------------------------------------------------------------------
	
public static String generateReport(String sessionUser){
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			List<Object> transactionsInfo = new ArrayList<Object>();

			String sql = "SELECT transactionid, date, commPrice, sender, commodity, receiver, type, whoReported FROM aml.detailedtransactions where sender = '"
					+ sessionUser + "';";
			PreparedStatement pstatement = conn.prepareStatement(sql);
			ResultSet rs = pstatement.executeQuery();

				while (rs.next()) {
					
					System.out.println("I'm in rs.next()");
					
					JSONObject jo = new JSONObject();

					String transactionid = rs.getString(1);
					String date = rs.getString(2);
					String commPrice = rs.getString(3);
					String sender = rs.getString(4);
					String commodity = rs.getString(5);
					String receiver = rs.getString(6);
					String type = rs.getString(7);
					String whoReported = rs.getString(8);
					
					jo.put("transactionid", transactionid);
					jo.put("date", date);
					jo.put("commPrice", commPrice);
					jo.put("sender", sender);
					jo.put("commodity", commodity);
					jo.put("receiver", receiver);
					jo.put("type", type);
					jo.put("whoReported", whoReported);

					transactionsInfo.add(jo);

				} // end while rs.next()


			conn.close();
			System.out.println("This is transactionsInfo.toString(): " + transactionsInfo.toString());
			return transactionsInfo.toString();

		} // end try
		
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in generateReport()_________**************");
			ex.printStackTrace();
			return "this";
		} // end catch
	}


	
	
	//-------------Code to check if the transaction with "tid" is "clean" or "dirty"--------------------------------------
	
	public static String checkTransactionType(String transactionid, String sessionUser){
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);

				String sql = "select commPrice, sender, type from aml.detailedtransactions where transactionid = '"+transactionid+"';";
				PreparedStatement pstatement = conn.prepareStatement(sql);
				ResultSet rs = pstatement.executeQuery();
				JSONObject jo = new JSONObject();
				
				while (rs.next()) {
					
					System.out.println("I'm in rs.next()");
					
					String amount = rs.getString(1); //since commPrice is nothing but the amount
					String sender = rs.getString(2); //is required either to be rewarded/punished
					String type = rs.getString(3);
					
					//We have arrived at this point since the sessionUser pressed the "report" button
					
					if(type.equals("dirty")){
					
						
					String sql2 = "update aml.useraccount set clean_money = clean_money + " + amount + " where userid='"
								+ sessionUser + "';";
					PreparedStatement statement2 = conn.prepareStatement(sql2);
					statement2.executeUpdate();

					
					String sql3 = "update aml.useraccount set clean_money = clean_money - " + amount + " where userid='"
							+ sender + "';";
					PreparedStatement statement3 = conn.prepareStatement(sql3);
					statement3.executeUpdate();
						
					}
					
					if(type.equals("clean")){
 						
						// sessionUser's cleanmoney = cleanmoney - amount
						
						String sql4 = "update aml.useraccount set clean_money = clean_money - " + amount + " where userid='"
								+ sessionUser + "';";
						PreparedStatement statement4 = conn.prepareStatement(sql4);
						statement4.executeUpdate();
						
						// sender's cleanmoney = cleanmoney + amount
						
						String sql5 = "update aml.useraccount set clean_money = clean_money + " + amount + " where userid='"
								+ sender + "';";
						PreparedStatement statement5 = conn.prepareStatement(sql5);
						statement5.executeUpdate();
					}
					
					
					String sql6 = "update aml.detailedtransactions set reported = 'yes'  where transactionid='"+transactionid+"';";
					PreparedStatement statement6 = conn.prepareStatement(sql6);
					statement6.executeUpdate();
					
					String sql7 = "update aml.detailedtransactions set whoReported = '"+sessionUser+"'  where transactionid='"+transactionid+"';";
					PreparedStatement statement7 = conn.prepareStatement(sql7);
					statement7.executeUpdate();
					
					jo.put("amount", amount);
					jo.put("type", type);
					conn.close();
					return jo.toString();

				} // end while rs.next()
				return "Carry on, my wayward son!";
		} // end try
		
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in checkTransactionType()_________**************");
			ex.printStackTrace();
			return "this";
		} // end catch
	}

	//--------------------------------------------------------------------------------------------------------------------
	
	public static String theRealSendMoneyFunc(String receiver, String sender, String commodity, double commPrice, int commQty, 
			String status, String type) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "INSERT INTO transactionstatus (`sender`,`receiver`,`commodity`,`commPrice`,`commQty`,`status`,`type`) VALUES(?,?,?,?,?,?,?);";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, sender);
			statement.setString(2, receiver);
			statement.setString(3, commodity);
			statement.setDouble(4, commPrice);
			statement.setInt(5, commQty);
			statement.setString(6, status);
			statement.setString(7, type);

			int row = statement.executeUpdate();

			if (type.equals("dirty")) {

				String sql4 = "update aml.useraccount set dirty_money = dirty_money - " + commPrice
						+ " where userid = '" + sender + "';";
				PreparedStatement statement4 = conn.prepareStatement(sql4);
				statement4.executeUpdate();
				
				String sql5 = "update aml.useraccount set dirty_money = dirty_money + " + commPrice + " where userid='"
						+ receiver + "';";
				PreparedStatement statement5 = conn.prepareStatement(sql5);
				statement5.executeUpdate();
			}

			if (type.equals("clean")) {

				String sql2 = "update aml.useraccount set clean_money = clean_money + " + commPrice + " where userid='"
						+ receiver + "';";
				PreparedStatement statement2 = conn.prepareStatement(sql2);
				statement2.executeUpdate();

				String sql3 = "update aml.useraccount set clean_money = clean_money - " + commPrice
						+ " where userid = '" + sender + "';";
				PreparedStatement statement3 = conn.prepareStatement(sql3);
				statement3.executeUpdate();
			}

			if (row > 0) {
				System.out.println("A request was inserted (sendMoneyFunc worked. Yaay !!!).");
				return "It worked";
				
			} // end if
			conn.close();
		} // end try
		catch (Exception ex) {
			System.out.println("You have already sent the request to this user");
			return "this";
			// ex.printStackTrace();
		} // end catch
		return "obligation";
	}// end sendMoneyFunc


	
	//--------------------------code to fetch complete transaction details-----------
	
public static String fetchIndividualComOwnDetails(String userid){
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			List<Object> companyInfo = new ArrayList<Object>();

				String sql = "select companyName from aml.comowndetails where userid = '"+userid+"';";
				PreparedStatement pstatement = conn.prepareStatement(sql);
				ResultSet rs = pstatement.executeQuery();

				while (rs.next()) {
					
					System.out.println("I'm in rs.next()");
					
					JSONObject jo = new JSONObject();

					String companyName = rs.getString(1);
					
					jo.put("companyName", companyName);

					companyInfo.add(jo);

				} // end while rs.next()


			conn.close();
			System.out.println("This is companyInfo.toString(): " + companyInfo.toString());
			return companyInfo.toString();

		} // end try
		
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in fetchIndividualComOwnDetails()_________**************");
			ex.printStackTrace();
			return "this";
		} // end catch
	}


	
	//--------------------------code to fetch complete transaction details-----------
	
public static String fetchCompleteTransactionDetails(String userid, String sessionUser){
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			List<Object> transactionsInfo = new ArrayList<Object>();

				String sql = "SELECT * FROM aml.detailedtransactions where sender = '"+userid+"'  or receiver ='"+userid+"';";
				PreparedStatement pstatement = conn.prepareStatement(sql);
				ResultSet rs = pstatement.executeQuery();

				while (rs.next()) {
					
					System.out.println("I'm in rs.next()");
					
					JSONObject jo = new JSONObject();

					String transactionid = rs.getString(1);
					String date = rs.getString(2);
					String commPrice = rs.getString(3);
					String sender = rs.getString(4);
					String senderAddress = rs.getString(5);
					String senderLob = rs.getString(6);
					String commodity = rs.getString(7);
					String receiver = rs.getString(8);
					String receiverAddress = rs.getString(9);
					String receiverLob = rs.getString(10);
					String reported = rs.getString(12);
					
					
					jo.put("transactionid", transactionid);
					jo.put("date", date);
					jo.put("commPrice", commPrice);
					jo.put("sender", sender);
					jo.put("senderAddress", senderAddress);
					jo.put("senderLob", senderLob);
					jo.put("commodity", commodity);
					jo.put("receiver", receiver);
					jo.put("receiverAddress", receiverAddress);
					jo.put("receiverLob", receiverLob);
					jo.put("sessionUser", sessionUser);
					jo.put("reported", reported);

					transactionsInfo.add(jo);

				} // end while rs.next()


			conn.close();
			System.out.println("This is transactionsInfo.toString(): " + transactionsInfo.toString());
			return transactionsInfo.toString();

		} // end try
		
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in fetchCompleteTransactionDetails()_________**************");
			ex.printStackTrace();
			return "this";
		} // end catch
	}

		
	//-------------------------code to fetch transaction details----------------------
	
	public static String fetchTransactionDetails(String sessionUser){
		
		//pass sessionUser to this page
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			List<Object> junketsInfo = new ArrayList<Object>();
			int numOfTransactions = 0;

				String sql = "select distinct sender from aml.detailedtransactions union select distinct receiver from aml.detailedtransactions;";
				//sender and receiver can be sessionUser
				PreparedStatement pstatement = conn.prepareStatement(sql);
				ResultSet rs = pstatement.executeQuery();

				while (rs.next()) {
					
					System.out.println("I'm in rs.next()");
					
					JSONObject jo = new JSONObject();

					String userid = rs.getString(1);
					
					//if the above got userid is the same as the session user, do not display the transactions.
					if(!(userid.equals(sessionUser))){
						
						String sql2 = "select count(*) from aml.detailedtransactions where sender = '"+userid+"' or receiver = '"+userid+"';";
						System.out.println("This is the query: "+sql2);
						
						PreparedStatement pstatement2 = conn.prepareStatement(sql2);
						ResultSet rs2 = pstatement2.executeQuery();
						
						System.out.println("pstatement2: "+pstatement2);
	
						while(rs2.next()){
							System.out.println("I'm in rs2.next()");
							numOfTransactions = rs2.getInt(1);
						}
						
						System.out.println("userid: "+userid);
						System.out.println("numOfTransactions: "+numOfTransactions);
						
						jo.put("userid", userid);
						jo.put("numOfTransactions", numOfTransactions);
	
						junketsInfo.add(jo);
					}// end if(!(userid.equals(sessionUser)))
				} // end while rs.next()


			conn.close();
			System.out.println("This is junketsInfo.toString(): " + junketsInfo.toString());
			return junketsInfo.toString();

		} // end try
		
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in fetchTransactionDetails()_________**************");
			ex.printStackTrace();
			return "this";
			// ex.printStackTrace();
		} // end catch
	}// 
		
		
	//-------------------------code to insert comOwnDetails-----------------------------
	public static String insertComOwnDetails(String companyName, String entityType, String userid) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "INSERT INTO comowndetails (`companyName`,`entityType`,`userid`) VALUES(?,?,?);";

			System.out.println("companyName: "+companyName);
			System.out.println("entityType: "+entityType);
			System.out.println("userid: "+userid);
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, companyName);
			statement.setString(2, entityType);
			statement.setString(3, userid);

			int row = statement.executeUpdate();

			if (row > 0) {
				System.out.println("A request was inserted (insertComOwnDetails worked. Yaay !!!).");
				return "It worked";
			} // end if
			conn.close();
		} // end try
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in insertCommDetails()_________**************");
			return "this";
			// ex.printStackTrace();
		} // end catch
		return "obligation";
	}// end sendMoneyFunc

	//----------------------------Code to insert values into detailedtransactions table-----------------------------------------------------------------------
	
	public static String insertDetailedTransactions(String jsonString) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		
		try {
			JSONObject jo = new JSONObject(jsonString);

			String sender = jo.getString("sender");
			String commodity = jo.getString("commodity").trim();
			double commPrice = jo.getDouble("commPrice");
			String receiver = jo.getString("receiver");
			String date = jo.getString("date");
			double transactionid = jo.getDouble("transactionid");
			String type = jo.getString("type");
			String reported = jo.getString("reported");
			
			System.out.println("sender: "+sender);
			System.out.println("commodity: "+commodity);
			System.out.println("commPrice: "+commPrice);
			System.out.println("receiver: "+receiver);
			System.out.println("date: "+date);
			System.out.println("transactionid: "+transactionid);
			System.out.println("type: "+type);
			System.out.println("reported: "+reported);
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			
			//-----------------fetching senderAddress----------------------
			String sql = "select address,lob from aml.useraccount where userid='"+sender+"';";
			
			System.out.println("This is the Sql query: "+ sql);
			PreparedStatement pstatement = conn.prepareStatement(sql);
			ResultSet rs = pstatement.executeQuery();
			
			System.out.println("pstatement: "+pstatement);

			  String senderAddress = "";
			  String senderLob = "";
				while (rs.next()) {
					senderAddress = rs.getString(1);
					senderLob = rs.getString(2);
					
					System.out.println("senderAddress: "+senderAddress);
					System.out.println("senderAddress: "+senderLob);
				}
			
 			//-----------------fetching receiverAddress----------------------
 			String sql2 = "select address,lob from aml.useraccount where userid='"+receiver+"';";
 			
 			System.out.println("This is the Sql query: "+ sql2);
 			PreparedStatement pstatement2 = conn.prepareStatement(sql2);
 			ResultSet rs2 = pstatement2.executeQuery();
 			
 			System.out.println("pstatement2: "+pstatement2);

 			  String receiverAddress = "";
 			  String receiverLob = "";
 				while (rs2.next()) {
 					receiverAddress = rs2.getString(1);
 					receiverLob = rs2.getString(2);
 					System.out.println("receiverAddress: "+receiverAddress);
 					System.out.println("receiverAddress: "+receiverLob);
 				}
 			
 			//------------------------------------------------------------------
     		
     		String insertSql = "INSERT INTO detailedtransactions (`transactionid`,`date`,`commPrice`,`sender`,`senderAddress`,`senderLob`,`commodity`,`receiver`,`receiverAddress`,`receiverLob`,`type`,`reported`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";

			PreparedStatement insertStatement = conn.prepareStatement(insertSql);
			System.out.println("insertStatement: "+insertStatement);
			
			insertStatement.setDouble(1, transactionid);
			insertStatement.setString(2, date);
			insertStatement.setDouble(3, commPrice);
			insertStatement.setString(4, sender);
			insertStatement.setString(5, senderAddress);
			insertStatement.setString(6, senderLob);
			insertStatement.setString(7, commodity);
			insertStatement.setString(8, receiver);
			insertStatement.setString(9, receiverAddress);
			insertStatement.setString(10, receiverLob);
			insertStatement.setString(11, type);
			insertStatement.setString(12, reported);

			int row = insertStatement.executeUpdate();
			System.out.println("value of row!: "+row);

			if (row > 0) {
				System.out.println("The details were inserted (detailedtransactions worked. Yaay !!!).");
				conn.close();
				return "It worked again";
			}
		} // end try
		catch (Exception ex) {
			//ex.printStackTrace();
			return "Stack Trace in Jconnector.insertDetailedTransactions function";
		} // end catch
		return "Oye oye balle balle!";
	}
	
	
	//--------------------------This code checks if a username is available----------
	
	public static int checkUsernameAvailability(String uname) {
		System.out.println("In checkUsernameAvailability method now");

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		int count = 0;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

//			String sql = "select count(userid) from aml.useraccount where userid='"+uname+"'";
			String sql = "select count(userid) from aml.simpleuseraccount where userid='"+uname+"'";
			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {
				count = rs.getInt(1);
			} // end while
			
			System.out.println("Count in checkUsernameAvailability() method: " + count);
			conn.close();
			stSetLimit.close();
			return count;

		} // end try
		
		catch (Exception ex) {
			ex.printStackTrace();
			return count;
		} // end catch

	}

	
	
	//--------------------------Price Update Servlet---------------------------------
	
	public static void updatePriceInformation(String username, double preciousMetalsPrice, double propertyPrice, double artPrice) {
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);

			String querySetLimit = "SET GLOBAL max_allowed_packet=104857600;"; // 10

			Statement stSetLimit = conn.createStatement();

			stSetLimit.execute(querySetLimit);
			
			String sql = "UPDATE `aml`.`useraccount` SET `preciousMetalsPrice`="+preciousMetalsPrice+", `propertyPrice`="+propertyPrice+", `artPrice`="+artPrice+" WHERE `userid`='"+username+"';";

			PreparedStatement statement = conn.prepareStatement(sql);
			// The PreparedStatement interface accepts input parameters at// runtime.

			int row = statement.executeUpdate();

			if (row > 0) {
				System.out.println("Prices were updated (PriceUpdateServlet worked. Yaay !!!).");
			} // end if
			
			conn.close();
			
		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
		} // end catch
	}// end PriceUpdate

	
	// ----------------------Code to change Transaction Status---------------------------------------------------------------------------------

	public static String changeTransactionStatus(String jsonString) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		
		System.out.println("*************************************\n");
		System.out.println("\n");
		System.out.println("In Jconnector.changeTransactionStatus() method now");
		System.out.println("\n");
		System.out.println("*************************************\n");
		
		try {
			JSONObject jo = new JSONObject(jsonString);

			String sender = jo.getString("sender");
			String commodity = jo.getString("commodity").trim();
			double commPrice = jo.getDouble("commPrice");
			String receiver = jo.getString("receiver");
			String type = jo.getString("type");
			
			
			System.out.println("sender: "+sender);
			System.out.println("commodity: "+commodity);
			System.out.println("commPrice: "+commPrice);
			System.out.println("receiver: "+receiver);
			System.out.println("type: "+type);
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();
			
			String sql = "update aml.transactionstatus set status='confirmed' where sender='" +sender+ "' and receiver='" + receiver + "' and status='waiting';";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.executeUpdate();
			
//			//----------------a small experiment begins here---------
			
			   
			System.out.println("You might not see anything good after this line");
			
			String selectCommPriceSql = "Select commQty from aml.transactionstatus where receiver = '"+receiver+"' and sender = '"+sender+"';";
			System.out.println("This is the Sql query: "+ selectCommPriceSql);
			PreparedStatement pstatement = conn.prepareStatement(selectCommPriceSql);
			ResultSet rs = pstatement.executeQuery();
			
			System.out.println("pstatement: "+pstatement);

			System.out.println("*************************************\n");
			System.out.println("\n");
			System.out.println("\n");
			System.out.println("*************************************\n");
			  
			  
			  int numPieces = 0;
			  //we already have the variable commPrice
				while (rs.next()) {
					numPieces = rs.getInt(1);
				//	System.out.println("numPieces: "+numPieces);
				}
			
    		 //System.out.println("numPieces must not be 0: "+numPieces);
				
//			//----------------the small experiment ends here---------
			
			String sql3 = "update aml.useraccount set " + commodity + "=" + commodity + "+" +numPieces+ " where userid='" + sender+ "';";
			PreparedStatement statement3 = conn.prepareStatement(sql3);
			System.out.println("pstatement3: "+statement3);
			statement3.executeUpdate();

			System.out.println("*************************************\n");
			System.out.println("\n");
			System.out.println("\n");
			System.out.println("*************************************\n");
			
			String sql4 = "update aml.useraccount set " + commodity + "=" + commodity + "-" +numPieces+" where userid='" + receiver+ "';";
			PreparedStatement statement4 = conn.prepareStatement(sql4);
			System.out.println("pstatement4: "+statement4);
			statement4.executeUpdate();
			
			System.out.println("*************************************\n");
			System.out.println("\n");
			System.out.println("\n");
			System.out.println("*************************************\n");
			
			// This is the seller of the commodity
			String sql5 = "update aml.useraccount set clean_money = clean_money + " + commPrice + " where userid='"+ receiver + "';";
			PreparedStatement statement5 = conn.prepareStatement(sql5);
			System.out.println("pstatement5: "+statement5);
			statement5.executeUpdate();
			
			//This is the buyer of the commodity
			if (type.equals("dirty")){
				
				System.out.println("************************************************************************");
				System.out.println("type: "+type);
				System.out.println("************************************************************************");
				
			String sql6 = "update aml.useraccount set dirty_money = dirty_money - " + commPrice + " where userid = '"+ sender + "';";
			PreparedStatement statement6 = conn.prepareStatement(sql6);
			statement6.executeUpdate();
			}
			
			if (type.equals("clean")){
				
				System.out.println("************************************************************************");
				System.out.println("type: "+type);
				System.out.println("************************************************************************");
				
			String sql6 = "update aml.useraccount set clean_money = clean_money - " + commPrice + " where userid = '"+ sender + "';";
			PreparedStatement statement6 = conn.prepareStatement(sql6);
			statement6.executeUpdate();
			}
			
			conn.close();
			stSetLimit.close();
			System.out.println("Transaction status has been changed");
			return "Status has been changed";
			
		} // end try
		catch (Exception ex) {
			//ex.printStackTrace();
			return "Stack Trace in multiple SQL Queries function";
		} // end catch

	}
	// --------------------------Code to change 15-Oranges to 14-Oranges and
	// everything like that!!-------------------------

	public static String deductCommodity(String comm) {
		System.out.println("I'm called");
		String[] parts = comm.split("-");
		System.out.println(parts[0]);
		System.out.println(parts[1]);
		System.out.println(parts[0].getClass().getName());
		int converted = Integer.parseInt(parts[0]);
		converted--;
		System.out.println("Integer bro: " + converted);
		return converted + "-" + parts[1];
	}

	// --------------------Code to retrieve transaction requests for users who
	// have received requests----------------------------------------
	
	public static String retrieveTransactionNotification(String uname) {
		System.out.println("In retrieveTransactionNotification method now");

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			String sql = "select sender,commodity,commPrice,type from aml.transactionstatus where receiver='" + uname
					+ "' and status='waiting';";
			ResultSet rs = stSetLimit.executeQuery(sql);
			List<Object> userNotifications = new ArrayList<Object>();
			while (rs.next()) {
				JSONObject jo = new JSONObject();
				// changed requestNotificationUser to transactionNotification
				String transactionNotification = rs.getString(1);
				String commodity = rs.getString(2);
				int commPrice = rs.getInt(3);
				String type = rs.getString(4);
				jo.put("transactionNotification", transactionNotification);
				jo.put("commodity", commodity);
				jo.put("commPrice", commPrice);
				jo.put("type", type);
				userNotifications.add(jo);
			} // end while

			conn.close();
			stSetLimit.close();
			//System.out.println("Jo.toString() in retrieveTransactionNotification() method: " + userNotifications.toString());
			return userNotifications.toString();

		} // end try
		catch (Exception ex) {
			//ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	// --------------Code to transfer money from sender to
	// receiver-----------------------------------------------------------------------------------

	public static String sendMoneyFunc(String receiver, String sender, String commodity, double commPrice, int commQty, 
			String status, String type) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "INSERT INTO transactionstatus (`sender`,`receiver`,`commodity`,`commPrice`,`commQty`,`status`,`type`) VALUES(?,?,?,?,?,?,?);";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, sender);
			statement.setString(2, receiver);
			statement.setString(3, commodity);
			statement.setDouble(4, commPrice);
			statement.setInt(5, commQty);
			statement.setString(6, status);
			statement.setString(7, type);

			int row = statement.executeUpdate();

			if (row > 0) {
				System.out.println("A request was inserted (sendMoneyFunc worked. Yaay !!!).");
				return "It worked";
			} // end if
			conn.close();
		} // end try
		catch (Exception ex) {
			System.out.println("You have already sent the request to this user");
			return "this";
			// ex.printStackTrace();
		} // end catch
		return "obligation";
	}// end sendMoneyFunc

	// ------Code to retrieve Collaborator
	// details-----------------------------------------------------------------------------------------------------------

	public static String retrieveCollaboratorDetails(String collaboratorList) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();
			List<Object> junketsInfo = new ArrayList<Object>();
			JSONArray jsonArray = new JSONArray(collaboratorList);

			for (int j = 0; j < jsonArray.length(); j++) {
				String s1 = (String) jsonArray.getJSONObject(j).get("username");
				// we need commodityPrice to be retrieved from the database
				String sql = "SELECT userid,first_name,last_name,address,lob, bank, clean_money, dirty_money, preciousMetals, preciousMetalsPrice, property, propertyPrice, artWorks, artPrice FROM aml.useraccount where userid = '"
						+ s1 + "';";
				ResultSet rs = stSetLimit.executeQuery(sql);
				// choose professions in registration page

				while (rs.next()) {

					JSONObject jo = new JSONObject();

					String userid = rs.getString(1);
					String first_name = rs.getString(2);
					String last_name = rs.getString(3);
					String address = rs.getString(4);
					String lob = rs.getString(5);
					String bank = rs.getString(6);
					String clean_money = rs.getString(7);
					String dirty_money = rs.getString(8);
					String preciousMetals = rs.getString(9);
					String preciousMetalsPrice = rs.getString(10);
					String property = rs.getString(11);
					String propertyPrice = rs.getString(12);
					String artWorks = rs.getString(13);
					String artPrice = rs.getString(14);

					jo.put("userid", userid);
					jo.put("first_name", first_name);
					jo.put("last_name", last_name);
					jo.put("address", address);
					jo.put("lob", lob);
					jo.put("bank", bank);
					jo.put("clean_money", clean_money);
					jo.put("dirty_money", dirty_money);
					jo.put("preciousMetals", preciousMetals);
					jo.put("preciousMetalsPrice", preciousMetalsPrice);
					jo.put("property", property);
					jo.put("propertyPrice", propertyPrice);
					jo.put("artWorks", artWorks);
					jo.put("artPrice", artPrice);

					junketsInfo.add(jo);

				} // end while

			} // end for

			conn.close();
			stSetLimit.close();
			System.out.println("You are awesome if this works: " + junketsInfo.toString());
			return junketsInfo.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	// ---------------Code to retrieve userids of Collaborators from the
	// friendrequesttable------------------------------------------

	public static String findCollaborators(String uname) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			String sql = "Select distinct receiver from aml.friendrequesttable where sender='" +uname+ "' and status='confirmed' UNION select distinct sender from aml.friendrequesttable where receiver='" + uname + "' and status='confirmed';";
			ResultSet rs = stSetLimit.executeQuery(sql);
			List<Object> collaborators = new ArrayList<Object>();

			while (rs.next()) {
				JSONObject jo = new JSONObject();
				String userid = rs.getString(1);
				jo.put("username", userid);
				collaborators.add(jo);
			} // end while

			conn.close();
			stSetLimit.close();
			//System.out.println("Jo.toString() in findCollaborators() method: " + collaborators.toString());
			return collaborators.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	// --------------Code to copy 'confirmed' rows from friendrequesttable to
	// network table---------------------------------

	public static String copyRowsBetweenTables() {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			String sql = "insert into aml.network select distinct sender, receiver from aml.friendrequesttable where status='confirmed';";
			stSetLimit.executeUpdate(sql);

			conn.close();
			stSetLimit.close();
			System.out.println("Rows have been copied");
			return "Rows have been copied";

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	// --------------Code to run query which changes status to 'confirmed' from
	// waiting-----------------------------------------

	public static String changeStatus(String sender, String receiver) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			String sql = "update aml.friendrequesttable set status='confirmed' where sender='" + sender
					+ "' and receiver='" + receiver + "' and status='waiting';";
			stSetLimit.executeUpdate(sql);

			conn.close();
			stSetLimit.close();
			System.out.println("Status has been changed");
			return "Status has been changed";

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	// --------------------Code to retrieve users from whom requests have been
	// received----------------------------------------
	public static String retrieveNotificationInformation(String uname) {
		System.out.println("In retrieveNotificationInformation method now");

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			String sql = "select distinct sender from aml.friendrequesttable where receiver='" + uname
					+ "' and status='waiting';";
			ResultSet rs = stSetLimit.executeQuery(sql);
			List<Object> userNotifications = new ArrayList<Object>();
			while (rs.next()) {
				JSONObject jo = new JSONObject();
				String requestNotificationUser = rs.getString(1);
				jo.put("requestNotificationUser", requestNotificationUser);
				userNotifications.add(jo);
			} // end while

			conn.close();
			stSetLimit.close();
			System.out.println(
					"Jo.toString() in retrieveNotificationInformation() method: " + userNotifications.toString());
			return userNotifications.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}
	// ------------------------------------------------------------------------------------------------------

	public static void insertChatData(String id, String userid, String time, String message) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// dynamically load the driver's class file into memory, which
			// automatically registers it
			Connection conn = DriverManager.getConnection(url, user, password);
			// call to the DriverManager object's getConnection( ) method to
			// establish actual database connection.

			String querySetLimit = "SET GLOBAL max_allowed_packet=104857600;"; // 10MB

			Statement stSetLimit = conn.createStatement();
			// You need a Connection object to create a Statement object
			// Use the createStatement() method to create the statement object
			// stSetLimit holds the reference to this object

			stSetLimit.execute(querySetLimit);
			// execute anything that comes in.....

			String sql = " INSERT INTO `aml`.`userchat`" + "(`id`,`userid`,`time`,`message`)" + "VALUES(?,?,?,?)";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, "undefined");
			statement.setString(2, "undefined");
			statement.setString(3, "undefined");
			statement.setString(4, message);

			int row = statement.executeUpdate();
			if (row > 0) {
				System.out.println("A row was inserted.");
			} // end if
			conn.close();
		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
		} // end catch
	}// end insertChatData

	// -------------------------------------------------------------------------------------------------------------------------------------

	public static void insertUserInformation(String userid, String firstName, String lastName, String playerPassword, String gender,
			String entityValue, String address, String lob, String bank, double cleanmoney, double dirtymoney,
			int preciousMetals, double preciousMetalsPrice, int property, double propertyPrice, int artWorks, double artPrice) {
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// dynamically load the driver's class file into memory, which
			// automatically registers it
			Connection conn = DriverManager.getConnection(url, user, password);
			// call to the DriverManager object's getConnection( ) method to
			// establish actual database connection.

			String querySetLimit = "SET GLOBAL max_allowed_packet=104857600;"; // 10

			Statement stSetLimit = conn.createStatement();
			// You need a Connection object to create a Statement object
			// Use the createStatement() method to create the statement object
			// stSetLimit holds the reference to this object

			stSetLimit.execute(querySetLimit);
			// execute anything that comes in.....

			String sql = "INSERT INTO useraccount (`userid`,`first_name`,"
					+ " `last_name`,`password`,`gender`,`entity_value`,`address`,`lob`,`bank`,"
					+ " `clean_money`,`dirty_money`,`preciousMetals`,`preciousMetalsPrice`,`property`,`propertyPrice`,`artWorks`,`artPrice`) VALUES"
					+ " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement statement = conn.prepareStatement(sql);
			// The PreparedStatement interface accepts input parameters at
			// runtime.

			statement.setString(1, userid);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.setString(4, playerPassword);
			statement.setString(5, gender);
			statement.setString(6, entityValue);
			statement.setString(7, address);
			statement.setString(8, lob);
			statement.setString(9, bank);
			statement.setDouble(10, cleanmoney);
			statement.setDouble(11, dirtymoney);
			statement.setInt(12, preciousMetals);
			statement.setDouble(13, preciousMetalsPrice);
			statement.setInt(14, property);
			statement.setDouble(15, propertyPrice);
			statement.setInt(16, artWorks);
			statement.setDouble(17, artPrice);

			// 1-9 are the column numbers
			// firstName, lastName,....etc are variables from the welcome.html
			// page

			int row = statement.executeUpdate();

			if (row > 0) {
				System.out.println("A row was inserted (insertUserInformation worked. Yaay !!!).");
			} // end if
			conn.close();
		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
		} // end catch
	}// end insertUserInformation

	// ------------------------------------------------------------

	public static String insertSenderReceiverRequestInfo(String sender, String receiver, String status) {

		System.out.println("InsSenRecReqInfo() being called....");
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			String querySetLimit = "SET GLOBAL max_allowed_packet=104857600;"; // 10
																				// MB
			Statement stSetLimit = conn.createStatement();
			stSetLimit.execute(querySetLimit);

			String sql = "INSERT INTO friendrequesttable (`receiver`,`sender`,`status`) VALUES(?,?,?)";

			PreparedStatement statement = conn.prepareStatement(sql);
			// The PreparedStatement interface accepts input parameters at
			// runtime.

			statement.setString(1, sender);
			statement.setString(2, receiver);
			statement.setString(3, status);

			int row = statement.executeUpdate();

			if (row > 0) {
				System.out.println("A request was inserted (insertSenderReceiverRequestInfo worked. Yaay !!!).");
				return "It worked";
			} // end if
			conn.close();
		} // end try
		catch (Exception ex) {
			System.out.println("You have already sent the request to this user");
			return "this";
			// ex.printStackTrace();
		} // end catch
		return "obligation";
	}// end insertUserInformation

	// -------------------------------------------------------------
	// a uname has to be passed to this method from AccountView Servlet
	public static String retrieveUserInformation(String uname) {
		System.out.println("In retrieveUserInformation method now");
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			System.out.println("In the try block of retrieveUserInformation");
			Class.forName("com.mysql.jdbc.Driver");
			// dynamically load the driver's class file into memory, which
			// automatically registers it
			Connection conn = DriverManager.getConnection(url, user, password);
			// call to the DriverManager object's getConnection( ) method to
			// establish actual database connection.
			Statement stSetLimit = conn.createStatement();
			// You need a Connection object to create a Statement object
			// Use the createStatement() method to create the statement object
			// stSetLimit holds the reference to this object
			System.out.println("Connection established");

			String sql = "SELECT clean_money, dirty_money, preciousMetals, preciousMetalsPrice, property, propertyPrice, artWorks, artPrice FROM aml.useraccount where userid = '"
					+ uname + "';";

			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {
				String cleanmoney = rs.getString(1);
				String dirtymoney = rs.getString(2);
				int preciousMetals = rs.getInt(3);
				double preciousMetalsPrice = rs.getDouble(4);
				int property = rs.getInt(5);
				double propertyPrice = rs.getDouble(6);
				int artWorks = rs.getInt(7);
				double artPrice = rs.getDouble(8);

				System.out.println("The values being returned are:");
				System.out.println(cleanmoney + "," + dirtymoney + "," + preciousMetals + "," + preciousMetalsPrice + "," + property
						+ "," + propertyPrice + artWorks + "," + artPrice);

				JSONObject jo = new JSONObject();
				jo.put("cleanmoney", cleanmoney);
				jo.put("dirtymoney", dirtymoney);
				jo.put("preciousMetals", preciousMetals);
				jo.put("preciousMetalsPrice", preciousMetalsPrice);
				jo.put("property", property);
				jo.put("propertyPrice", propertyPrice);
				jo.put("artWorks", artWorks);
				jo.put("artPrice", artPrice);

				conn.close();
				stSetLimit.close();
				return jo.toString();
			}

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch
		return password;

	}
	// -----Code to retrieve people on your
	// network--------------------------------------------------------------

	public static String retrieveNetwork(String username) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			// String sql = "Select distinct userid2 from aml.network where
			// userid1='"+username+"' UNION select distinct userid1 from
			// aml.network where userid2='"+username+"';";
			
			String sql = "Select distinct receiver from aml.friendrequesttable where sender='" + username
					+ "' and status='confirmed' UNION select distinct sender from aml.friendrequesttable where receiver='" + username
					+ "' and status='confirmed';";
			ResultSet rs = stSetLimit.executeQuery(sql);
			List<Object> networkInfo = new ArrayList<Object>();
			while (rs.next()) {
				JSONObject jo = new JSONObject();
				String userid = rs.getString(1);
				jo.put("username", userid);
				networkInfo.add(jo);
			} // end while

			conn.close();
			stSetLimit.close();
			System.out.println("Jo.toString() in retrieveNetwork() method: " + networkInfo.toString());
			return networkInfo.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	// -----------------------------------------------------------------------------------------------------------------
	public static String findJunkets(String uname, String userInSession) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			String sql = "SELECT userid,first_name,last_name FROM aml.useraccount";

			ResultSet rs = stSetLimit.executeQuery(sql);
			List<Object> junketsInfo = new ArrayList<Object>();
			JSONArray jsonArray2 = new JSONArray(uname);

			while (rs.next()) {
				
				int counter = 0;
				JSONObject jo = new JSONObject();
				String userid = rs.getString(1).trim();
				
				for (int j = 0; j < jsonArray2.length(); j++) {
					String s1 = (String) jsonArray2.getJSONObject(j).get("username");
					String s2 = userid;

					
							
						if (s2.trim().equals(s1.trim())) {
							counter++;
							System.out.println("The counter value is now: " + counter);
						}
				} // end for
				
				if(!(userid.equals(userInSession))){
						if (counter >= 1) {
							String first_name = rs.getString(2);
							String last_name = rs.getString(3);
							String inNetwork = "This one's your buddy!";
		
							jo.put("userid", userid);
							jo.put("first_name", first_name);
							jo.put("last_name", last_name);
							jo.put("inNetwork", inNetwork);
							//junketsInfo.add(jo);
						}
						
						if (counter == 0) {
							
							String first_name = rs.getString(2);
							String last_name = rs.getString(3);
							String inNetwork = "Make this person your accomplice";
		
							jo.put("userid", userid);
							jo.put("first_name", first_name);
							jo.put("last_name", last_name);
							jo.put("inNetwork", inNetwork);
							junketsInfo.add(jo);
						}
				}// end if(!(userid.equals(userInSession)))

			} // end while

			conn.close();
			stSetLimit.close();
			//System.out.println("You are awesome if this works: " + junketsInfo.toString());
			return junketsInfo.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	// ------------------------------------------------------------------------------------------------------------

	public static String findSmurfs() {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			JSONObject jo = new JSONObject();

			String sql = "SELECT userid,first_name,last_name FROM aml.useraccount where lob='Second hand car dealers'";
			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {
				String userid = rs.getString(1);
				String first_name = rs.getString(2);
				String last_name = rs.getString(3);

				System.out.println(userid + "," + first_name + "," + last_name);

				jo.put("userid", userid);
				jo.put("first_name", first_name);
				jo.put("last_name", last_name);
				// .Jconnector.class.;

			} // end while

			conn.close();
			stSetLimit.close();
			System.out.println(jo.toString());
			return jo.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	public static String findInsAgents() {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			// new changes made are here
			JSONObject jo = new JSONObject();
			// new changes made are here

			String sql = "SELECT userid,first_name,last_name FROM aml.useraccount where lob='Bank and Financial services'";
			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {
				String userid = rs.getString(1);
				String first_name = rs.getString(2);
				String last_name = rs.getString(3);

				System.out.println(userid + "," + first_name + "," + last_name);

				jo.put("userid", userid);
				jo.put("first_name", first_name);
				jo.put("last_name", last_name);
				// .Jconnector.class.;

			} // end while

			conn.close();
			stSetLimit.close();
			System.out.println(jo.toString());
			return jo.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	public static String findArtDealers() {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			JSONObject jo = new JSONObject();

			String sql = "SELECT userid,first_name,last_name FROM aml.useraccount where lob='Travel Agency'";
			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {
				String userid = rs.getString(1);
				String first_name = rs.getString(2);
				String last_name = rs.getString(3);

				System.out.println(userid + "," + first_name + "," + last_name);

				jo.put("userid", userid);
				jo.put("first_name", first_name);
				jo.put("last_name", last_name);

			} // end while

			conn.close();
			stSetLimit.close();
			System.out.println(jo.toString());
			return jo.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	public static String findShops() {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			JSONObject jo = new JSONObject();

			String sql = "SELECT userid,first_name,last_name FROM aml.useraccount where lob='Travel Agency'";
			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {
				String userid = rs.getString(1);
				String first_name = rs.getString(2);
				String last_name = rs.getString(3);

				System.out.println(userid + "," + first_name + "," + last_name);

				jo.put("userid", userid);
				jo.put("first_name", first_name);
				jo.put("last_name", last_name);

			} // end while

			conn.close();
			stSetLimit.close();
			System.out.println(jo.toString());
			return jo.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	// Code to check userid and password 
	
	public static String checkUserId(String useridPassword) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			JSONObject jo = new JSONObject(useridPassword);

			String userid = jo.getString("uname").trim();
			String playerPassword = jo.getString("password").trim();

			String sql = "SELECT COUNT(*) FROM aml.useraccount WHERE userid IN ('" + userid + "') and password IN ('" + playerPassword + "');";
			//SELECT COUNT(*) FROM aml.useraccount WHERE userid IN ('" + userid + "');
			
			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {
				String rsToString = rs.getString(1);
				System.out.println("This is the rsToString value you're looking for: " + rsToString);
				return rsToString;
			}

			conn.close();
			stSetLimit.close();
			return "Connection Closed";

		} // end try
		catch (Exception ex) {
			//ex.printStackTrace();
			System.out.println("______________Stack trace in Jconnector.checkUserId________________");
			return "Stack Trace";
		} // end catch

	}
}
