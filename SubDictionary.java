// Assignment 4  
// Part 1
// Written by: Zhang Bo, ID:40108654
// -----------------------------------------------------

/**
 *Zhang Bo, ID:40108654
 *COMP249
 *Assignment 4
 *Due Date 2019 12 02
 * 
 */

package assignment4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//This program will read a user select file to create a subdictionary of words contain in this file.

public class SubDictionary {

	
	// Give the name of file,check if the file is valid.

	/**
	 * @param sc
	 * @param file
	 * @return
	 */
	public static boolean checkFile(Scanner sc, String file) {
		boolean wordsExist = false;
	
		try {
			sc = new Scanner(new FileInputStream((file)));
			if (sc.hasNext()) {
				System.out.println("File is ok, will create SubDictionary");
				wordsExist = true;
								
			}else {
				System.out.println("File is empty, can't create SubDictionary. Program will terminate.");
				sc.close();
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not open input file " + file
					+ "  for reading. Please check if file exists! Program will terminate.");
			sc.close();
			System.exit(0);
		}
		return wordsExist;
	}

	// Format the word get from giving file, to meet the dictionary requirement.
	/**
	 * format the word 
	 * @param word
	 * @return
	 */
	public static String formatWord(String word) {
		String finalWord = null;
		if (word.length() == 1) {
			if (word.equals("i")|| word.equals("a")||word.equals("I")||word.equals("A")) {
				finalWord = word.toUpperCase();
			}

		} else if (!word.matches(".*\\d+.*")) {
			
			if (word.contains("¡¯") ) {
				word = word.substring(0, word.indexOf("¡¯"));
			}
				
			word = word.replaceAll("\\pP", "");
			finalWord = word.toUpperCase();

		}
		return finalWord;
	}

	// Add formatted word in arraylist.
	/**
	 * add words in arraylist
	 * @param word
	 * @param subDictionary
	 */
	public static void addWord(String word, ArrayList<ArrayList<String>> subDictionary) {
		boolean arrayExist = false;
		boolean wordExist = false;
		String firstletter = word.substring(0,1);

		for (ArrayList<String> array:subDictionary) {          
			if (firstletter.equals(array.get(0).substring(0,1))) {
				arrayExist = true;
				for (String x : array) {	
					   if(x.equals(word)) {
						wordExist = true;
						break;
					}
				}
				if (wordExist == false) {
					array.add(word);	

				}			
		    }
		}
			if (arrayExist == false) {
			
				ArrayList<String> array1 = new ArrayList<String>();				 

				array1.add(word);
				subDictionary.add(array1);

			}

	}

	// Built comparator by the first element of arraylist.
      abstract static class arraylistcompare implements Comparator<ArrayList>{
	  public int compare(ArrayList<String> a, ArrayList<String> b) {
	        return a.get(0).compareTo(b.get(0));
	    }
	  }
	
   	
    // Sort arraylist alphabetically.
	/**
	 * sort arraylist
	 * @param subDictionary
	 */
	public static void sortArraylist(ArrayList<ArrayList<String>> subDictionary) {
		
		for (ArrayList<String> array : subDictionary) {
			array.sort(null);
		    array.add(0, array.get(0).substring(0,1));

		    if(array.size()>1) {
		    array.add(1, "==");}
		    else array.add("==");
		}

		Collections.sort(subDictionary, new Comparator<ArrayList<String>>() {
		    @Override
		    public int compare(ArrayList<String> a, ArrayList<String> b) {
		        return a.get(0).compareTo(b.get(0));
		    }
		});
				
	}
	
	// Write arraylist on output file, count total entries.
	
	/**
	 * write arraylist on file
	 * @param subDictionary
	 * @return
	 */
	public static int createSubDictionary(ArrayList<ArrayList<String>> subDictionary) {
		int totalWord = 0;
		for(ArrayList<String> array : subDictionary) {
			totalWord+=array.size();}
		totalWord=totalWord-2*subDictionary.size();
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream("SubDictionary.txt"));
			pw.println("\nThe document produced this sub-dictionary, which includes " + totalWord + " entries.\n");
			for (ArrayList<String> array : subDictionary) {
				for (String x : array) {
					pw.println(x);
				}
				pw.println();
			}
			
			
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return totalWord;
	}

	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Wellcome to Sub-Dictionary Creator, pls input file name: ");
		String file = sc.next();
		if(checkFile(sc, file)) {
			 String word=null;
		     ArrayList<ArrayList<String>> subDictionary = new ArrayList<ArrayList<String>>(20);
		     ArrayList<String> firstletter=new ArrayList<String>();
		     firstletter.add("A");
		     subDictionary.add(firstletter);

		     try {
		     sc = new Scanner(new FileInputStream((file)));
		          while (sc.hasNext()) {
			           word = sc.next();

			           if(formatWord(word)!=null) {
			               addWord(formatWord(word), subDictionary);
		                }
			           
		          }
		     }   catch (FileNotFoundException e) {
					System.out.println("Could not open input file " + file
							+ "  for reading. Please check if file exists! Program will terminate.");
					sc.close();
					System.exit(0);
				}
		          sortArraylist(subDictionary);
		          createSubDictionary(subDictionary);
	     }
		
     }

  }
      


	

