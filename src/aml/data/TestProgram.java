package aml.data;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class TestProgram {
	
	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/aml";
		String user = "hsr";
		String password = "root";
		int counter = 0;
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("extractedData.txt", "UTF-8");
			writer.println("The first line");
			writer.println("The second line");
			writer.close();
			System.out.println("Program run");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
}