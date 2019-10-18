package org.aml.views;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class AAA {
	public static void main(String args[]){  
		Scanner in = new Scanner(System.in); 
		Map<String,Integer> map=new HashMap<String,Integer>();  
		System.out.println("Please enter the number of entries: ");

		int n=in.nextInt();
		
		for (int i=0; i<n; i++){
			System.out.println("Please enter name");
			String name = in.next();
			System.out.println("Please enter number");
			int number = in.nextInt();
			map.put(name,number);
		}

		System.out.println("Enter name to get number");
		String nameKey= in.next();
		System.out.println(map.get(nameKey));  
	}
}
