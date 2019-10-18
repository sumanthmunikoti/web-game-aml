package aml.data;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.Collection;
import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;

public class DataForAnnotation {
	
	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "Thtb@amlp121";
		HashMap<String, Integer> nameCommodityToCounts = new HashMap<>();
		
		try {
			PrintWriter writer = new PrintWriter("001.txt", "UTF-8");
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stSetLimit = conn.createStatement();
			System.out.println("Connection established");
			
			String sql = "select distinct case when value='' then CONCAT_WS('', username,' ',eventName) else value end as columnName from aml.userlogdetails;";
			
			ResultSet rs = stSetLimit.executeQuery(sql);
			while (rs.next()) {
				
//				Set<String> keys = nameCommodityToCounts.keySet(); 
				String sentence = rs.getString(1);
				String[] arr = sentence.split("[\\s:]");

				if(nameCommodityToCounts.get(arr[0]+" clean_money")==null) {
					nameCommodityToCounts.put(arr[0]+" clean_money", 10000);
				}
				if(nameCommodityToCounts.get(arr[0]+" dirty_money")==null) {
					nameCommodityToCounts.put(arr[0]+" dirty_money", 10000);
				}
				if(nameCommodityToCounts.get(arr[0]+" preciousMetals")==null) {
					nameCommodityToCounts.put(arr[0]+" preciousMetals", 10);
				}
				if(nameCommodityToCounts.get(arr[0]+" property")==null) {
					nameCommodityToCounts.put(arr[0]+" property", 10);
				}
				
				if(arr.length == 7) {
					String sendOfTo = arr[1]+" "+arr[3]+" "+arr[5];
					if(sendOfTo.equals("Send of to")) {
//						System.out.println("arr[0]+\" \"+arr[4]: " +arr[0]+" "+arr[4]);
//						System.out.println("arr[6]+\" \"+arr[4]: " +arr[6]+" "+arr[4]);
						
						Integer valuea = nameCommodityToCounts.get(arr[0]+" "+arr[4]);
						Integer valueb = nameCommodityToCounts.get(arr[6]+" "+arr[4]);
						
						int qty = Integer.parseInt(arr[2]);
//						System.out.println("value: "+valuea);
//						System.out.println("qty: "+qty);
						
						if(valuea==null || valueb ==null) {
							System.out.println("Null");
						}
						
						else {
//						System.out.println("value:"+valuea+ "- qty:" + qty);
						valuea = valuea - qty;
						valueb = valueb + qty;
//						System.out.println(valuea);
						nameCommodityToCounts.put(arr[0]+" "+arr[4], valuea);
						nameCommodityToCounts.put(arr[6]+" "+arr[4], valueb);
						writer.println("Transaction:\t"+sentence);
						}
					}
				}
				
				Integer cm = nameCommodityToCounts.get(arr[0]+" clean_money");
				Integer dm = nameCommodityToCounts.get(arr[0]+" dirty_money");
				Integer pm = nameCommodityToCounts.get(arr[0]+" preciousMetals");
				Integer ppty = nameCommodityToCounts.get(arr[0]+" property");
				
				writer.println("Chat:\t"+sentence+"\t"+cm+"\t"+dm+"\t"+pm+"\t"+ppty);
			}//end while
			writer.close();
		}
		
		catch (Exception ex) {
			System.out.println("******_________An exception has been caught in checkTransactionType()_________**************");
			ex.printStackTrace();
		} // end catch
		
		
		
//        for (String key : keys) {
//            System.out.println(key);
//            System.out.println(multiMap.get(key));
//        }
        
	}
	
}