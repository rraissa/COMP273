import java.util.*;
import java.io.*;



public class Scrabble{

    static HashSet<String> myDictionary; // this is where the words of the dictionary are stored

    // DO NOT CHANGE THIS METHOD
    // Reads dictionary from file
    public static void readDictionaryFromFile(String fileName) throws Exception {
        myDictionary=new HashSet<String>();

        BufferedReader myFileReader = new BufferedReader(new FileReader(fileName) );

        String word;
        while ((word=myFileReader.readLine())!=null) myDictionary.add(word);

	myFileReader.close();
    }



    /* Arguments: 
        char availableLetters[] : array of characters containing the letters that remain available
        String prefix : Word assembled to date
        Returns: String corresponding to the longest English word starting with prefix, completed with zero or more letters from availableLetters. 
	         If no such word exists, it returns the String ""
     */
     public static String longestWord(char availableLetters[], String prefix) {
	 /* WRITE YOUR CODE HERE */
 
    	 String longest = "";
    	
    
    	if (myDictionary.contains(prefix)){	// check if the word we're assembling is in the dictionary every time 
    		longest += prefix;
    	}
    
    	
    	for(int i = 0; i < availableLetters.length; i++){
    		String tmp = prefix + availableLetters[i];	// assembling the words
    		char newArray[]	= new char[availableLetters.length-1];
    		
    		for(int j = 0; j < i; j++){	// add to new array the letters of availableLetters from 0 to i
    			newArray[j] = availableLetters[j];
    		}
    		for(int j = i + 1; j < availableLetters.length; j++){	// add to new array letters of availableLetters from i+1 to the end availableLetters
    			newArray[j-1] = availableLetters[j];
    		}
  
    		tmp = longestWord(newArray, tmp); // recursive call with new array of letters and tmp
    		if(tmp.length() > longest.length()){ // returning the longest word
    			longest = tmp;
    			}
    	}
    	
    	
	 return longest;

    }

    
    
    /* main method
        You should not need to change anything here.
     */
    public static void main (String args[]) throws Exception {
       
	// First, read the dictionary
	try {
	    readDictionaryFromFile("englishDictionary.txt");
        }
        catch(Exception e) {
            System.out.println("Error reading the dictionary: "+e);
        }
        
        
        // Ask user to type in letters
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in) );
        char letters[]; 
        do {
            System.out.println("Enter your letters (no spaces or commas):");
            
            letters = keyboard.readLine().toCharArray();

	    // now, enumerate the words that can be formed
            String longest = longestWord(letters, "");
	    System.out.println("The longest word is "+longest);
        } while (letters.length!=0);

        keyboard.close();
        
    }
}