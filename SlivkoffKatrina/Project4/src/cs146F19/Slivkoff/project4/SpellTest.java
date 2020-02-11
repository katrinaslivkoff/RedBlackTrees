package cs146F19.Slivkoff.project4;

import java.io.BufferedReader;
import java.io.FileReader;

public class SpellTest
{
	static final String myPoem = "C:\\Users\\katsl\\Documents\\CS 146\\Project 4\\poem.txt";						
	static final String myDict = "C:\\Users\\katsl\\Documents\\CS 146\\Project 4\\dictionary.txt";
	
	private static void dictionaryToRBT(RedBlackTree rbt)
	{
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(myDict));
			String word = "";
			while ((word = input.readLine()) != null)
			{
				rbt.insert(word);
			}
			input.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	private static int spellCheck(RedBlackTree rbt)
	{
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(myPoem));

			String poemLine = "";
			int numOfMisspelledWords = 0;
			
			System.out.println("Mispelled words: ");
			while ((poemLine = input.readLine()) != null)
			{
				String[] allWords = poemLine.split(" ");
				
				for (int i = 0; i < allWords.length; i++)
				{
					String word = allWords[i].toLowerCase();
					if (rbt.lookup(word) == null)
					{
						System.out.println(word);
						numOfMisspelledWords++;
					}
				}
			}
			input.close();
			return numOfMisspelledWords;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
	public static void main (String [] Args)
	{
		try
		{
			RedBlackTree<String> dict = new RedBlackTree<String>();
			
			long before = System.currentTimeMillis();
			dictionaryToRBT(dict);
			long after = System.currentTimeMillis();
			
			long beforeCheck = System.currentTimeMillis();
			int count = spellCheck(dict);
			long afterCheck = System.currentTimeMillis();
			
			System.out.println("Time to insert: " + (after - before));
			System.out.println("Time to check poem: " + (afterCheck - beforeCheck));
			System.out.println("# of mispelled words: " + count);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
}