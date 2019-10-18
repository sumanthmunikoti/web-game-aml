package aml.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

public class Jconnector2 {
	
	public static String changeStatusOfHasPaid(String money, String username) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();
			Statement stSetLimit2 = conn.createStatement();
			
			String sql2 = "select commodity,quantity,whatprice from aml.userbotinteraction where hasPaid = 'no' and userid = '"+username+"'limit 1;";
			ResultSet rs = stSetLimit2.executeQuery(sql2);
			while(rs.next()) {
				String commodity = rs.getString(1);
				String quantity = rs.getString(2);
				String whatprice = rs.getString(3);
				int total = Integer.parseInt(quantity) * Integer.parseInt(whatprice);
				int kaas = Integer.parseInt(money);
				System.out.println("total: "+total);
				System.out.println("quantity: "+Integer.parseInt(quantity));
				System.out.println("whatprice: "+Integer.parseInt(whatprice));
				System.out.println("kaas: "+kaas);
				
				if(kaas >= total) {
					
					String sql3 = "update aml.simpleuseraccount set "+commodity+" = "+commodity+" + " + quantity + " where userid='"
							+ username + "';";
					PreparedStatement statement3 = conn.prepareStatement(sql3);
					statement3.executeUpdate();
					
					String sql = "update aml.userbotinteraction set hasPaid='yes' where userid='" + username + "';";
					stSetLimit.executeUpdate(sql);
					
					return "done";
				}
			}
			
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

	
//--------------------------------------------------------------------------------------------------------------------
	
	public static String insertUserBotInteraction(String username, String buyorsell, String commodity, String quantity, String whatprice, String hasPaid) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "INSERT INTO aml.userbotinteraction (`userid`,`buyorsell`,`commodity`,`quantity`,`whatprice`,`hasPaid`) VALUES(?,?,?,?,?,?);";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, buyorsell);
			statement.setString(3, commodity);
			statement.setString(4, quantity);
			statement.setString(5, whatprice);
			statement.setString(6, hasPaid);

			int row = statement.executeUpdate();

			if (row > 0) {
				System.out.println("A request was inserted (insertUserBotInteraction worked. Yaay !!!).");
				return "It worked";
				
			} // end if
			conn.close();
		} // end try
		catch (Exception ex) {
			 ex.printStackTrace();
			return "this";
			
		} // end catch
		return "obligation";
	}// end sendMoneyFunc

//---------------------------------------------------------------------------------------------------------
//---------methods to get and set counter value-----------------------------------------------------------------
	
	public static int[] valueArray = {2300,2500,3500,2500,2600,3600,700,2500,3700,310,2300,3800};

	public static int[] fetchDynamicPrices(){
	    return valueArray;
	}

	public static void changeDynamicPrices(){
	    
		Random pmrand = new Random();
		
		//precious metals prices
		valueArray[9] = valueArray[6];
		valueArray[6] = valueArray[3];
		valueArray[3] = valueArray[0]; 
		valueArray[0] = pmrand.nextInt(3400) + 100;//100-3500
	   
		//property prices
		valueArray[10] = valueArray[7];
		valueArray[7] = valueArray[4];
		valueArray[4] = valueArray[1]; 
		valueArray[1] = (pmrand.nextInt(2500)+500);//2500-3000
		
		//artwork prices
		valueArray[11] = valueArray[8];
		valueArray[8] = valueArray[5];
		valueArray[5] = valueArray[2]; 
		valueArray[2] = (pmrand.nextInt(3500) + 500);//3500-4000
	   
	}
	
//-----------------------------------------------------------------------public static String updateCmpBonus(String transactionid){}
	
	public static String updateCmpBonus(String username, int cleanmoney, int dirtymoney, int preciousMetals, int property, int artWorks){

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			
			double cmbonus = (cleanmoney - 10000)/5000;
			double dmbonus = (dirtymoney - 10000)/20000; 
			double pmbonus = (preciousMetals * valueArray[0])/1000;
			double pptybonus = (property * valueArray[1])/1000;
			double awbonus = (artWorks * valueArray[2])/1000;
			
			if (cmbonus < 0){
				cmbonus = 0;
			}
		
			if (dmbonus < 0){
				dmbonus = 0;
			}
			
			double bonus = cmbonus + dmbonus + pmbonus + pptybonus + awbonus;

			String sql3 = "update aml.simpleuseraccount set bonus = ?, cmppm = ?, cmpppty = ?, cmpar = ? where userid= ?;";
					PreparedStatement statement3 = conn.prepareStatement(sql3);
					statement3.setString(1, bonus+"");
					statement3.setInt(2, valueArray[0]);
					statement3.setInt(3, valueArray[1]);
					statement3.setInt(4, valueArray[2]);
					statement3.setString(5, username);
					statement3.executeUpdate();
					
				return "Carry on, my wayward son!";
		} // end try
		
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in checkTransactionType()_________**************");
			ex.printStackTrace();
			return "this";
		} // end catch
	}
		
		
	//------------function to retrieve user names which are then filled in the select option box
	//---------------------------------------------------------------------------------------------------------------
	
	public static String retrieveUserInformation(String uname) {
		System.out.println("In retrieveUserInformation method now");
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			System.out.println("In the try block of retrieveUserInformation");
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();
			System.out.println("Connection established");

			String sql = "SELECT slnum, clean_money, dirty_money, preciousMetals, property, artWorks FROM aml.simpleuseraccount where userid = '"
					+ uname + "';";

			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {

				String slnum = rs.getString(1);
				String cleanmoney = rs.getString(2);
				String dirtymoney = rs.getString(3);
				int preciousMetals = rs.getInt(4);
				int property = rs.getInt(5);
//				int artWorks = rs.getInt(6);

				JSONObject jo = new JSONObject();
				
				jo.put("slnum", slnum);
				jo.put("cleanmoney", cleanmoney);
				jo.put("dirtymoney", dirtymoney);
				jo.put("preciousMetals", preciousMetals);
				jo.put("property", property);
//				jo.put("artWorks", artWorks);

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

	
	// Code to check userid--------------------------------------------------------------------------------------------  
	
	public static String checkSimpleUserId(String useridNoPassword) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			JSONObject jo = new JSONObject(useridNoPassword);

			String userid = jo.getString("uname").trim();

			String sql = "SELECT COUNT(*) FROM aml.simpleuseraccount WHERE userid IN ('" + userid + "')";
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
			ex.printStackTrace();
			System.out.println("______________Stack trace in Jconnector2.checkSimpleUserId________________");
			return "Stack Trace";
		} // end catch

	}
//-------------------------------------------------------------------------------------------------------
	
	public static void insertSimpleUserInformation(String userid,double cleanmoney, double dirtymoney, int preciousMetals, int property,int artWorks) {
		
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

			String sql = "INSERT INTO simpleuseraccount (`userid`,"
					+ " `clean_money`,`dirty_money`,`preciousMetals`,`property`,`artWorks`,`bonus`,`cmppm`,`cmpppty`,`cmpar`) VALUES"
					+ " (?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement statement = conn.prepareStatement(sql);
			// The PreparedStatement interface accepts input parameters at
			// runtime.

			double cmbonus = (cleanmoney - 10000)/5000;
			double dmbonus = (dirtymoney - 10000)/20000; 
			double pmbonus = (preciousMetals * valueArray[0])/1000;
			double pptybonus = (property * valueArray[1])/1000;
			double awbonus = (artWorks * valueArray[2])/1000;
			
			if (cmbonus < 0){
				cmbonus = 0;
			}
		
			if (dmbonus < 0){
				dmbonus = 0;
			}
			
			double bonus = cmbonus + dmbonus + pmbonus + pptybonus + awbonus;
			
			statement.setString(1, userid);
			statement.setDouble(2, cleanmoney);
			statement.setDouble(3, dirtymoney);
			statement.setInt(4, preciousMetals);
			statement.setInt(5, property);
			statement.setInt(6, artWorks);
			statement.setString(7, bonus+"");
			statement.setInt(8, valueArray[0]);
			statement.setInt(9, valueArray[1]);
			statement.setInt(10, valueArray[2]);
			

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
	//-------------------------code to fetch "Simple" transaction details----------------------
	
		public static String fetchSimpleTransactionDetails(){
			
			//pass sessionUser to this page
			
			String url = "jdbc:mysql://localhost:3306/aml";
			String user = "hsr";
			String password = "root";
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(url, user, password);
				List<Object> junketsInfo = new ArrayList<Object>();
				int numOfTransactions = 0;

					String sql = "select distinct sender from aml.simpletransactions union select distinct receiver from aml.simpletransactions;";
					//sender and receiver can be sessionUser
					PreparedStatement pstatement = conn.prepareStatement(sql);
					ResultSet rs = pstatement.executeQuery();

					while (rs.next()) {
						
						JSONObject jo = new JSONObject();
						String userid = rs.getString(1);
						
							String sql2 = "select count(*) from aml.simpletransactions where sender = '"+userid+"' or receiver = '"+userid+"';";
							
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
			
	//-------------Code to check if the transaction with "tid" is "clean" or "dirty"--------------------------------------
	
		public static String checkSimpleTransaction(String transactionid){
			
			String url = "jdbc:mysql://localhost:3306/aml";
			String user = "hsr";
			String password = "root";
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(url, user, password);

					String sql = "select sender,valuableQty,commodity from aml.simpletransactions where transactionid = '"+transactionid+"';";
					PreparedStatement pstatement = conn.prepareStatement(sql);
					ResultSet rs = pstatement.executeQuery();
					JSONObject jo = new JSONObject();
					
					while (rs.next()) {
						
						String sender = rs.getString(1);
						String amount = rs.getString(2);
						String commodity = rs.getString(3); 
						
						if(commodity.equals("dirty_money")){
							
						String sql3 = "update aml.simpleuseraccount set clean_money = clean_money - " + amount + " where userid='"
								+ sender + "';";
						PreparedStatement statement3 = conn.prepareStatement(sql3);
						statement3.executeUpdate();
							
						}
						
						if(commodity.equals("clean_money")){
	 						
							String sql5 = "update aml.simpleuseraccount set clean_money = clean_money + " + amount + " where userid='"
									+ sender + "';";
							PreparedStatement statement5 = conn.prepareStatement(sql5);
							statement5.executeUpdate();
						}
						
						
						String sql6 = "update aml.simpletransactions set reported = 'yes'  where transactionid='"+transactionid+"';";
						PreparedStatement statement6 = conn.prepareStatement(sql6);
						statement6.executeUpdate();
						
						jo.put("amount", amount);
						jo.put("commodity", commodity);
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
	//--------the following piece of code is for the private cop page-------------------
	
public static String fetchPrivateTransactionDetails(String userid){
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			List<Object> transactionsInfo = new ArrayList<Object>();

				String sql = "SELECT * FROM aml.simpletransactions where sender = '"+userid+"'  or receiver ='"+userid+"';";
				PreparedStatement pstatement = conn.prepareStatement(sql);
				ResultSet rs = pstatement.executeQuery();

				while (rs.next()) {
					
					JSONObject jo = new JSONObject();

					String transactionid = rs.getString(1);
					String sender = rs.getString(2);
					String valuableQty = rs.getString(3);
					String commodity = rs.getString(4);
					String receiver = rs.getString(5);
					String reported = rs.getString(7);
					
					jo.put("transactionid", transactionid);
					jo.put("sender", sender);
					jo.put("valuableQty", valuableQty);
					jo.put("commodity", commodity);
					jo.put("receiver", receiver);
					jo.put("reported", reported);

					transactionsInfo.add(jo);

				} // end while rs.next()

			conn.close();
			System.out.println("This is transactionsInfo.toString(): " + transactionsInfo.toString());
			return transactionsInfo.toString();

		} // end try
		
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in fetchPrivateTransactionDetails()_________**************");
			ex.printStackTrace();
			return "this";
		} // end catch
	}

	//Code to generate transaction report on HomeScreen.html---------------------------------------------------
		
	public static String generateSimpleReport(String sessionUser){
			
			System.out.println("generateSimpleReport(String"+sessionUser+")");
		
			String url = "jdbc:mysql://localhost:3306/aml";
			String user = "hsr";
			String password = "root";
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(url, user, password);
				List<Object> transactionsInfo = new ArrayList<Object>();

				String sql = "SELECT * FROM aml.simpletransactions where sender = '"+sessionUser+"' or receiver = '"+sessionUser+"'";
//				String sql = "SELECT * FROM aml.simpletransactions where sender = 'Rashmi' or receiver = 'Rashmi'";
				PreparedStatement pstatement = conn.prepareStatement(sql);
				ResultSet rs = pstatement.executeQuery();

					while (rs.next()) {
						
						JSONObject jo = new JSONObject();

						String transactionid = rs.getString(1);
						String sender = rs.getString(2);
						String valuableQty = rs.getString(3);
						String commodity = rs.getString(4);
						String receiver = rs.getString(5);
						//String alerted = rs.getString(6);
						String reported = rs.getString(7);
						
						jo.put("transactionid", transactionid);
						jo.put("sender", sender);
						jo.put("valuableQty", valuableQty);
						jo.put("commodity", commodity);
						jo.put("receiver", receiver);
						//jo.put("alerted", alerted);
						jo.put("reported", reported);

						transactionsInfo.add(jo);

					} // end while rs.next()


				conn.close();
				return transactionsInfo.toString();

			} // end try
			
			catch (Exception ex) {
				System.out.println("******_________An exception has been caught in generateReport()_________**************");
				ex.printStackTrace();
				return "this";
			} // end catch
		}

	//---------------------------------------------------------------------------------------
	
	public static void updateAlerted(String transactionid) {
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		
		//transactionid = transactionid.trim();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);

			String querySetLimit = "SET GLOBAL max_allowed_packet=104857600;"; // 10

			Statement stSetLimit = conn.createStatement();

			stSetLimit.execute(querySetLimit);
			
			String sql = "UPDATE aml.simpletransactions SET alerted='yes' WHERE transactionid='"+transactionid+"';";
			
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.executeUpdate();
			conn.close();
			
		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
		} // end catch
	}// end PriceUpdate
	
	//------------retrieveAlertNotification----------------------------------------------------------------------------------
	
	public static String retrieveAlertNotification(String uname) {
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			String sql = "select transactionid,sender,valuableQty,commodity from aml.simpletransactions where receiver='" + uname
					+ "' and alerted='no';";
			ResultSet rs = stSetLimit.executeQuery(sql);
			List<Object> userNotifications = new ArrayList<Object>();
			
			while (rs.next()) {

				JSONObject jo = new JSONObject();

				String transactionid = rs.getString(1);
				String sender = rs.getString(2);
				String valuableQty = rs.getString(3);
				String commodity = rs.getString(4);
				
				jo.put("transactionid", transactionid);
				jo.put("sender", sender);
				jo.put("valuableQty", valuableQty);
				jo.put("commodity", commodity);
				

				userNotifications.add(jo);
				
			} // end while

			conn.close();
			stSetLimit.close();
			return userNotifications.toString();

		} // end try
		catch (Exception ex) {
			//ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	//------------------------------------------------------------------------------------------------------------------------
	
	public static String insertSimpleTransactions(String sender, String valuableQty, String commodity, String receiver, String alerted, String reported) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "INSERT INTO simpletransactions (`sender`,`valuableQty`,`commodity`,`receiver`,`alerted`,`reported`) VALUES (?,?,?,?,?,?);";

			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, sender);
			statement.setString(2, valuableQty);
			statement.setString(3, commodity);
			statement.setString(4, receiver);
			statement.setString(5, alerted);
			statement.setString(6, reported);

			int row = statement.executeUpdate();

			if (row > 0) {
				System.out.println("A request was inserted (insertSimpleTransactions worked. Yaay !!!).");
				return "It worked";
			} // end if
			conn.close();
		} // end try
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in simpletransactions()_________**************");
			ex.printStackTrace();
			return "this";
			// ex.printStackTrace();
		} // end catch
		return "obligation";
	}// end sendMoneyFunc	
	
//------------------------------------------------------------------------------------------------------------------
public static String updateCommodityQuantity(String jsonString){
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);

			JSONObject jo = new JSONObject(jsonString);

			String valuableQty = jo.getString("valuableQty");// the amount the sessionUser wants to transfer
			String receiver = jo.getString("receiver").trim();// the amount is transferred to this guy
			String commodity = jo.getString("commodity");//What kind of amount is that ? Cm, dm, pm, ppty, aw??!!
			String sessionUser = jo.getString("sessionUser");
			
//			System.out.println("valuableQty "+valuableQty);
//			System.out.println("receiver "+receiver);
//			System.out.println("commodity "+commodity);
//			System.out.println("sessionUser "+sessionUser);
			
			// increase receiverCommodity of  by valuableQty
			String sql2 = "update aml.simpleuseraccount set "+commodity+" = "+commodity+" + "+valuableQty+" where userid='"
								+ receiver + "';";
			System.out.println("sql2: "+sql2);
			PreparedStatement statement2 = conn.prepareStatement(sql2);
			statement2.executeUpdate();

			// reduce commodity of sessionUser by valuableQty
			String sql3 = "update aml.simpleuseraccount set "+commodity+" = "+commodity+" - "+valuableQty+" where userid='"
						+ sessionUser + "';";
			System.out.println("sql3: "+sql3);
			PreparedStatement statement3 = conn.prepareStatement(sql3);
			statement3.executeUpdate();
						
			return "Carry on, my wayward son!";
		} // end try
		
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in checkTransactionType()_________**************");
			ex.printStackTrace();
			return "this";
		} // end catch
	}

//--------------------------------------------------------------------------------------------------------------------
//------------function to retrieve sessionUser CommQty value----------------------------------------------------------
	
	public static String retrieveUserCommQty(String username, String commodity) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			String sql = "SELECT "+commodity+" from aml.simpleuseraccount where userid = '"+username+"';";
			ResultSet rs = stSetLimit.executeQuery(sql);
			List<Object> networkInfo = new ArrayList<Object>();
			JSONObject jo = new JSONObject();
			
			while (rs.next()) {
				
				String commQty = rs.getString(1);
				jo.put("commQty", commQty);
				networkInfo.add(jo);
				System.out.println("networkInfo.toString: "+networkInfo.toString());
			} // end while

			conn.close();
			stSetLimit.close();
			return networkInfo.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

	
	//------------function to retrieve user names which are then filled in the select option box
	public static String retrieveUsernames(String username) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();

			String sql = "SELECT userid from aml.simpleuseraccount where userid not like '"+username+"';";
			ResultSet rs = stSetLimit.executeQuery(sql);
			List<Object> networkInfo = new ArrayList<Object>();
			
			while (rs.next()) {
				JSONObject jo = new JSONObject();
				String userid = rs.getString(1);
				jo.put("userid", userid);
				networkInfo.add(jo);
			} // end while

			conn.close();
			stSetLimit.close();
			return networkInfo.toString();

		} // end try
		catch (Exception ex) {
			ex.printStackTrace();
			return "Stack Trace";
		} // end catch

	}

}