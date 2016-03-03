package main;

import java.util.ArrayList;
import java.util.Random;



/**
 * Class used to create the Markov Method used to generate names
 * @author K.Field
 *
 */
public class Markov {
	
	ArrayList<String> knownNames;
	int numNamesGenerate;
	int[] nameLenMinMax = new int[2];
	double[][][] prob; // probabilities for the letters
	

	
	ArrayList<Character> englishChars = new ArrayList<Character>();
	
	
	
	
	Markov(ArrayList<String> inNames, int numberOfNewNames, int minLength, int maxLength)
	{
		knownNames = new ArrayList<String>();
		
		for(String name: inNames)
			knownNames.add(name.toLowerCase());
		//knownNames.addAll(inNames); // all the names provided are known names
		numNamesGenerate = numberOfNewNames;
		nameLenMinMax[0] = minLength;
		nameLenMinMax[1] = maxLength;

		char temp[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_'};
	
		
		
		// for every letter in the alphabet, add it to the list of possible english characters
		for(char tmp: temp)
		{
			englishChars.add(tmp);
			
		}
//		for(char tmp: englishChars)
//			System.out.print(temp);
		
		// initialize probabilities
		prob = new double[temp.length+1][temp.length+1][temp.length+1];
		for(int x = 0; x < temp.length; x++)
			for(int y = 0; y < temp.length; y++)
				for(int z = 0; z < temp.length; z++)
					prob[x][y][z] = 0;
		
		
		AnalyzeNames(inNames);
		
		
		// Test comments - it's bound to be messy like this.
//		for(int x = 0; x < 27; x++)
//			for(int y = 0; y < 27; y++)
//				for(int z = 0; z < 27; z++)
//					System.out.println("[" + temp[x] + temp[y] + temp[z] + "]" + "| " + prob[x][y][z]);
		
//		double tempMath = 0;
//		for(int x = 0; x < 26; x++)
//		{
//			
//			System.out.println("[" + temp[26] + temp[26] + temp[x] + "]" + "| " + prob[26][26][x]);		
//			tempMath+=  prob[26][26][x];
//		}
//		System.out.println("Total is: " + tempMath);
//		tempMath = 0;
//		for(int x = 0; x < 26; x++)
//		{
//			
//			System.out.println("[" + temp[x] + temp[26] + temp[26] + "]" + "| " + prob[x][26][26]);		
//			tempMath+=  prob[26][26][x];
//		}
//		System.out.println("Total is: " + tempMath);
		
		//System.out.println("[" + temp[4] + temp[5] + temp[5] + "]" + "| " + prob[4][5][5]);

	}
	
	
	public ArrayList<String> runGenerator()
	{
		ArrayList<String> returnList = new ArrayList<String>();
		StringBuilder newName;
		
		
		Random rand = new Random();
		
		// set up first character

		
		//System.out.println(newName.toString());
				
		//Math.random();
		
		
		// while loop to keep making names until the returnList is full
		while(returnList.size() < numNamesGenerate)
		{
			// Add __ to the front
			newName = new StringBuilder();
			newName.append("__");
			
			
			// set up first character
			newName.append(Character.toString((char) (rand.nextInt(25) + 97)));
//			System.out.println(newName.toString());
			int x = 26;
			int y = 26;
			boolean breakFromLetterLoop;
//			System.out.println("Char at end is " + newName.charAt(newName.length()-1));
//			System.out.println("Char at end is " + newName.charAt(newName.length()-2));
			
			
			while(!((newName.charAt(newName.length()-1) == '_') && (newName.charAt(newName.length()-2) == '_')))
			{
				double randNum = rand.nextDouble();
				
				// if this name
				String tempName = newName.toString();

				
				
				for(int z = (rand.nextInt(27)); z > 0; z--)
				{
//					System.out.println("Trying| " +newName.toString() + englishChars.get(z));
					if(randNum < prob[x][y][z] && prob[x][y][z] != 0)
					{
						newName.append(englishChars.get(z));
//						System.out.println("Name: " + newName.toString());
						x = y;
						y = z;
						z = 0;
						break;
					}
					
				}
				
				// is equal to the name it is now...
				// break and try something new
//				if(tempName.equals(newName.toString()))
//				{
//					break;
//				}
				
				if(newName.length() > nameLenMinMax[1]+4)
					break;
//				System.out.println("New Name: " + newName.toString());
//				System.out.println("Does this blend?");
			}
			//System.out.println("Does this blend?");
			
//			System.out.println("Name: " + newName.toString());
//			System.out.println("Name Length:" + newName.length());
//			System.out.println("Min length:" + nameLenMinMax[0]);
//			System.out.println("Max length:" + nameLenMinMax[1]);
			
			// if this is a new name that fits the parameters...
			if(newName.length()-4 <= nameLenMinMax[1] && newName.length()-4 >= nameLenMinMax[0])
				if(!knownNames.contains(newName.toString()))
				{
					System.out.println("Name: " + newName.toString());
					returnList.add(newName.toString());
					knownNames.add(newName.toString());
				}
				
		}
		
		System.out.println("Number of new names: " + returnList.size());
		
		
//		return null;
		return returnList;
	}
	
	
	void AnalyzeNames(ArrayList<String> inNames)
	{
		String partOne;
		String partTwo;
		String partThree;
		
		// For every name provided...
		
		for(String name: inNames)
		{
			int nameLengthLeft = name.length(); // Have to remove 4 due to the two _'s
			
			// Count up the number of times each combo is used
			for(int i = 0; i < nameLengthLeft - 2; i++)
			{
				 //get the character at the first location, second location, and third
				partOne = Character.toString(name.charAt(i));
				partTwo = Character.toString(name.charAt(i+1));
				partThree = Character.toString(name.charAt(i+2));
				// insert info into the data
				inputData(partOne, partTwo, partThree);
			}
			
			
		}
		
		
		// turn all variables into percentages by dividing by inNames.length
		for(int x = 0; x < 27; x++)
			for(int y = 0; y < 27; y++)
				for(int z = 0; z < 27; z++)
				{
					if(prob[x][y][z] > 0)
					{
						prob[x][y][z] = prob[x][y][z]/ inNames.size();
					}
				}
	}
	
	
	void inputData(String firstPart, String secondPart, String thirdPart)
	{

		if(!firstPart.equals("_"))
			firstPart = firstPart.toLowerCase();
		if(!secondPart.equals("_"))
			secondPart = secondPart.toLowerCase();
		if(!thirdPart.equals("_"))
			thirdPart = thirdPart.toLowerCase();
//		System.out.println(firstPart);
//		System.out.println(secondPart);
//		System.out.println(thirdPart);
		
		int pos1 = englishChars.indexOf(firstPart.toCharArray()[0]);
		int pos2 = englishChars.indexOf(secondPart.toCharArray()[0]) ;
		int pos3 = englishChars.indexOf(thirdPart.toCharArray()[0]);
//		System.out.println("First part: " + pos1);
//		System.out.println("Second part: " + pos2);
//		System.out.println("Third part: " + pos3);
		
		prob[pos1][pos2][pos3]++; // increase it's count
		
		
	}

}
