package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	// Version number, in case I update this more.
	static String versionNumber = "V. 1.0";

	/**
	 * Main function - where all good things start
	 * @param args
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws IOException {
		
		boolean debugging = false;
		ArrayList<String> names = new ArrayList<String>(); // for holding the names
		System.out.println("Starting Name Generator");
		System.out.println(versionNumber);
		
		String inLine;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Debugging? Y/N");
		inLine = in.nextLine().toLowerCase(); // gets input and lowercases
		
		if((inLine.equalsIgnoreCase("y")))
			debugging = true;
		
		if(inLine.equalsIgnoreCase("T"))
		{
			StringBuilder tempNameList = new StringBuilder();
			ArrayList<String> boyNames = fileReader("C:\\Users\\K.Field\\Downloads\\namesBoys.txt");
			names.addAll(boyNames); // gives all the boy names to names
			ArrayList<String> girlNames = fileReader("C:\\Users\\K.Field\\Downloads\\namesGirls.txt");
			names.addAll(girlNames);
			Markov nameGenerator = new Markov(names, 10, 3, 10);
			nameGenerator.runGenerator();
			in.close();
			System.exit(0);
		}
		
		
		// Menu for choosing the gender of name

		System.out.println("Choose the gender of name you would like to generate.");
		System.out.println("1. Male");
		System.out.println("2. Female");
		System.out.println("3. Why_not_both?.jpg");
		inLine = in.nextLine().toLowerCase(); // gets input and lowercases
		
		
		// Immediately compiles the list of names
		// Gets the Names and reads them directly into a MASSIVE array
		if((inLine.equalsIgnoreCase("1")) || (inLine.equalsIgnoreCase("3")))
		{
			
			StringBuilder tempNameList = new StringBuilder();
			System.out.println("Type in the file directory for the male names .txt file:");
			String fileLocation = in.nextLine();
			ArrayList<String> boyNames = fileReader(fileLocation);
			
			if(debugging)
				for(String name: boyNames)
					System.out.println(name);
			
			names.addAll(boyNames); // gives all the boy names to names
			
		}
		if((inLine.equalsIgnoreCase("2")) || (inLine.equalsIgnoreCase("3")))
		{
			StringBuilder tempNameList = new StringBuilder();
			System.out.println("Type in the file directory for the female names .txt file:");
			String fileLocation = in.nextLine();
			ArrayList<String> girlNames = fileReader(fileLocation);
			
			if(debugging)
				for(String name: girlNames)
					System.out.println(name);
			
			names.addAll(girlNames); // gives all the boy names to names
		}
		if(Integer.parseInt(inLine) > 3 || Integer.parseInt(inLine) <= 0)
		{
			System.out.println("\" I'm sorry. My responses are limited. You must ask the right questions.\" - Dr. Alfred Lanning, iRobot, 2004");
			in.close();
			System.out.println("Program terminated.");
		}
		
		if(debugging)
			for(String name: names)
				System.out.println(name);
		
		
		int[] nameLength = new int[2]; // min is 0, max is 1
		System.out.println("Please enter the minimum name length you would like as a number.");
		nameLength[0]= in.nextInt();
		 
		System.out.println("Please enter the maximum name length you would like as a number.");
		nameLength[1]= in.nextInt();
		
		
		int numberOfNames;
		System.out.println("Finally, how many names would you like to generate?");
		numberOfNames = in.nextInt();
		// end of acquiring data from user
		
		
		// start the markov model
		Markov nameGenerator = new Markov(names, numberOfNames, nameLength[0], nameLength[1]);
		nameGenerator.runGenerator();
		
		
		
		
		
		// program cleanup and termination
		in.close();
		System.out.println("Program terminated.");
	}
	
	
	/**
	 * Reads the file from the fileLocation given line by line, 
	 * storing each contained word in the returned arraylist.
	 * @param fileLocation
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<String> fileReader(String fileLocation) throws IOException
	{
		ArrayList<String> temp = new ArrayList<String>();
		try(BufferedReader br = new BufferedReader(new FileReader(fileLocation)))
		{
			String line;
			while((line = br.readLine()) != null) // while it has a line...
			{
				String tempLine = "__" + line + "__";
				temp.add(tempLine);
			}
		}
		return temp;
	}
	
	
	
}
