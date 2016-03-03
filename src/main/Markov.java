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
	

	/**
	 * Establishes the Markov-method name generator
	 * @param inNames
	 * @param numberOfNewNames
	 * @param minLength
	 * @param maxLength
	 */
	Markov(ArrayList<String> inNames, int numberOfNewNames, int minLength, int maxLength)
	{
		knownNames = new ArrayList<String>();
		
		for(String name: inNames)
			knownNames.add(name.toLowerCase());
		//knownNames.addAll(inNames); // all the names provided are known names
		numNamesGenerate = numberOfNewNames;
		nameLenMinMax[0] = minLength;
		nameLenMinMax[1] = maxLength;

		char temp[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
				'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_'};

		// for every letter in the alphabet, add it to the list of possible english characters
		for(char tmp: temp)
			englishChars.add(tmp);

		
		// initialize probabilities
		prob = new double[temp.length+1][temp.length+1][temp.length+1];
		for(int x = 0; x < temp.length; x++)
			for(int y = 0; y < temp.length; y++)
				for(int z = 0; z < temp.length; z++)
					prob[x][y][z] = 0;
		
		AnalyzeNames(inNames);

	}
	
	/**
	 * Generates the number of names the Markov was built to generate.
	 * @return
	 */
	public ArrayList<String> runGenerator()
	{
		ArrayList<String> returnList = new ArrayList<String>();
		StringBuilder newName;
		Random rand = new Random();
		
		// while loop to keep making names until the returnList is full
		while(returnList.size() < numNamesGenerate)
		{
			// Add __ to the front
			newName = new StringBuilder();
			newName.append("__");
			
			
			// set up first character
			newName.append(Character.toString((char) (rand.nextInt(25) + 97)));
			int x = 26;
			int y = 26;
			
			//While the name is not ended...
			while(!((newName.charAt(newName.length()-1) == '_') && (newName.charAt(newName.length()-2) == '_')))
			{
				double randNum = rand.nextDouble();
				
				// Try all 26 letters until something seems to follow the pattern!
				for(int z = (rand.nextInt(27)); z > 0; z--)
				{
					if(randNum < prob[x][y][z] && prob[x][y][z] != 0)
					{
						newName.append(englishChars.get(z));

						x = y;
						y = z;
						z = 0;
						break;
					}
				}
				// If the word is much longer than the allowed length, scrap it.
				if(newName.length() > nameLenMinMax[1]+4)
					break;
			}
			// if this is a new name that fits the parameters...
			if(newName.length()-4 <= nameLenMinMax[1] && newName.length()-4 >= nameLenMinMax[0])
				if(!knownNames.contains(newName.toString()))
				{
					System.out.println("Name: " + newName.toString());
					returnList.add(newName.toString());
					knownNames.add(newName.toString());
				}	
		}
		return returnList;
	}
	
	
	/**
	 * Analyzes the Arraylist of names provided and creates a probability matrix for all the
	 * 3 letter combinations found.
	 * @param inNames
	 */
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
	
	/**
	 * Increments the probability accordingly.
	 * @param firstPart
	 * @param secondPart
	 * @param thirdPart
	 */
	void inputData(String firstPart, String secondPart, String thirdPart)
	{

		if(!firstPart.equals("_"))
			firstPart = firstPart.toLowerCase();
		if(!secondPart.equals("_"))
			secondPart = secondPart.toLowerCase();
		if(!thirdPart.equals("_"))
			thirdPart = thirdPart.toLowerCase();
		
		int pos1 = englishChars.indexOf(firstPart.toCharArray()[0]);
		int pos2 = englishChars.indexOf(secondPart.toCharArray()[0]);
		int pos3 = englishChars.indexOf(thirdPart.toCharArray()[0]);
		prob[pos1][pos2][pos3]++; // increase it's count
	}

}
