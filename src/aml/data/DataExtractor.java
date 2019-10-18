package aml.data;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.io.PrintWriter;

public class DataExtractor {
	
	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "Thtb@amlp121";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();
			Statement stSetLimit2 = conn.createStatement();
			System.out.println("Connection established");
			
			PrintWriter writer;
			
			String sql = "select userid from aml.simpleuseraccount;";

			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {
					
					String userid = rs.getString(1);
					writer = new PrintWriter(userid+".txt", "UTF-8");
					writer.println("//--------------------------------------------------------------------------------------------------------------------------//");
					
					writer.println("The User: "+userid);
					
					int cleanMoney = 10000;
					int dirtyMoney = 10000;
					int preciousMetals = 10;
					int property = 10;
					
					String sql3 = "select count(distinct value) from aml.userlogdetails where username = '"+userid+"'";
					Statement stSetLimit3 = conn.createStatement();
					ResultSet rs3 = stSetLimit3.executeQuery(sql3);
					while(rs3.next()) {
						String count = rs3.getString(1);
						writer.println("Total transactions and conversations: "+count);
						writer.println("Clean: "+cleanMoney+ "   Dirty: "+dirtyMoney+"    Metals: "+preciousMetals+"    Property: "+property);
					}
					
					//---------------Query within query--------------------------
					
				String sql2 = "select distinct case when value='' then CONCAT_WS('', username,' ',eventName) else value end as junkvaluethis from aml.userlogdetails where (username='"+userid+"' or eventName like '%"+userid+"%');";
				ResultSet rs2 = stSetLimit2.executeQuery(sql2);

				while (rs2.next()) {
					String value = rs2.getString(1);
					writer.println(value);
					String[] arr = value.split(" ");
					if(arr.length==7) {
					String sendOfTo = arr[1]+" "+arr[3]+" "+arr[5];
					if(sendOfTo.equals("Send of to")) {
						//System.out.println(sendOfTo);
						//	System.out.println(arr[0]+" "+arr[2]+" "+arr[4]+" "+arr[6]);
							if (arr[0].equals(userid)) {
//									System.out.println(userid + " is the sender. Deduct");
//									System.out.println("How much did he send: "+ arr[2]);
//									System.out.println("What did he send: "+ arr[4]);
									int howMuch = Integer.parseInt(arr[2]);
									
									if(arr[4].equals("clean_money")) { 
										cleanMoney = cleanMoney-howMuch; 
										writer.println(' ');
										writer.println("Clean: "+cleanMoney+ "   Dirty: "+dirtyMoney+"    Metals: "+preciousMetals+"    Property: "+property);
										writer.println(' ');
									}
									if(arr[4].equals("dirty_money")) { 
										dirtyMoney = dirtyMoney-howMuch;
										writer.println(' ');
										writer.println("Clean: "+cleanMoney+ "   Dirty: "+dirtyMoney+"    Metals: "+preciousMetals+"    Property: "+property);
										writer.println(' ');
									}
									if(arr[4].equals("preciousMetals")) { 
										preciousMetals = preciousMetals-howMuch;
										writer.println(' ');
										writer.println("Clean: "+cleanMoney+ "   Dirty: "+dirtyMoney+"    Metals: "+preciousMetals+"    Property: "+property);
										writer.println(' ');
									}
									if(arr[4].equals("property")) { 
										property = property-howMuch;
										writer.println(' ');
										writer.println("Clean: "+cleanMoney+ "   Dirty: "+dirtyMoney+"    Metals: "+preciousMetals+"    Property: "+property);
										writer.println(' ');
										}
								}
							else if (arr[6].equals(userid)) {
//								System.out.println(userid + " is the receiver. Add");
//								System.out.println("How much did he receive: "+ arr[2]);
//								System.out.println("What did he receive: "+ arr[4]);
								int howMuch = Integer.parseInt(arr[2]);
								
								if(arr[4].equals("clean_money")) { 
									cleanMoney = cleanMoney+howMuch; 
									writer.println(' ');
									writer.println("Clean: "+cleanMoney+ "   Dirty: "+dirtyMoney+"    Metals: "+preciousMetals+"    Property: "+property);
									writer.println(' ');
									}
								if(arr[4].equals("dirty_money")) { 
									dirtyMoney = dirtyMoney+howMuch;
									writer.println(' ');
									writer.println("Clean: "+cleanMoney+ "   Dirty: "+dirtyMoney+"    Metals: "+preciousMetals+"    Property: "+property);
									writer.println(' ');
								}
								if(arr[4].equals("preciousMetals")) { 
									preciousMetals = preciousMetals+howMuch;
									writer.println(' ');
									writer.println("Clean: "+cleanMoney+ "   Dirty: "+dirtyMoney+"    Metals: "+preciousMetals+"    Property: "+property);
									writer.println(' ');
								}
								if(arr[4].equals("property")) { 
									property = property+howMuch;
									writer.println(' ');
									writer.println("Clean: "+cleanMoney+ "   Dirty: "+dirtyMoney+"    Metals: "+preciousMetals+"    Property: "+property);
									writer.println(' ');
								}
								}
							}
						}
					}
					//---------------Query within query--------------------------
				writer.close();
			}
			
		}
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in checkTransactionType()_________**************");
			ex.printStackTrace();
		} // end catch
	}

		}
