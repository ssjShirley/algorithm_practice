/* Ladder.java
   CSC 225 - Summer 2018
   
   Starter code for Programming Assignment 3

   B. Bird - 06/30/2018
*/


import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Ladder{

	
	public static void showUsage(){
		System.err.printf("Usage: java Ladder <word list file> <start word> <end word>\n");
	}
	

	public static void main(String[] args){
		
		//At least four arguments are needed
		if (args.length < 3){
			showUsage();
			return;
		}
		String wordListFile = args[0];
		String startWord = args[1].trim();
		String endWord = args[2].trim();
		
		
		//Read the contents of the word list file into a LinkedList (requires O(nk) time for
		//a list of n words whose maximum length is k).
		//(Feel free to use a different data structure)
		BufferedReader br = null;
		LinkedList<String> words = new LinkedList<String>();
		
		try{
			br = new BufferedReader(new FileReader(wordListFile));
		}catch( IOException e ){
			System.err.printf("Error: Unable to open file %s\n",wordListFile);
			return;
		}
		
		try{
			for (String nextLine = br.readLine(); nextLine != null; nextLine = br.readLine()){
				nextLine = nextLine.trim();
				if (nextLine.equals(""))
					continue; //Ignore blank lines
				//Verify that the line contains only lowercase letters
				for(int ci = 0; ci < nextLine.length(); ci++){
					//The test for lowercase values below is not really a good idea, but
					//unfortunately the provided Character.isLowerCase() method is not
					//strict enough about what is considered a lowercase letter.
					if ( nextLine.charAt(ci) < 'a' || nextLine.charAt(ci) > 'z' ){
						System.err.printf("Error: Word \"%s\" is invalid.\n", nextLine);
						return;
					}
				}
				words.add(nextLine);
			}
			
		} catch (IOException e){
			System.err.printf("Error reading file\n");
			return;
		}

		/* Find a word ladder between the two specified words. Ensure that the output format matches the assignment exactly. */
		
		//find the index of start and end words
		int start = 0;
		int end = 0;
		for (int i = 0 ; i < words.size(); i++){
			if (startWord.equals(words.get(i))){
				start = i;
			}
			if (endWord.equals(words.get(i))){
				end = i;
			}
		}
		
		int [][] G = new int [words.size()][words.size()];
		LinkedList<Integer> answerList = new LinkedList<Integer>();
		
		G = graphSetup(words);
		answerList = minPath(G, start, end);
		
		for (int i = 0; i < answerList.size(); i++){
			System.out.println(words.get(answerList.get(i)));
		}
		
		
	}
	//find the path
	public static LinkedList<Integer> minPath(int[][] G, int u, int v){
		int [] parent = new int [G[0].length];
		for (int i = 0; i < G[0].length; i++){
			parent[i] = -1;
		}
		Queue<Integer> queue = new LinkedList<Integer>();
		int x;
		parent[v] = v;
		queue.add(v);
		while (queue.peek() != null){
			x = queue.poll();
			for (int y = 0; y < G[0].length; y++){
				if (G[x][y] != 0 && parent[y] == -1){
					parent[y] = x;
					queue.add(y);
				}
			}
		}
		
		LinkedList<Integer> L = new LinkedList<Integer>();
		x = u;
		while (x != v){
			L.add(x);
			x = parent[x];
		}
		L.add(v);
		return L;
	}
	//set up the graph
	public static int[][] graphSetup(LinkedList<String> words){
		int graph[][] = new int [words.size()][words.size()];
		
		for(int i = 0; i < words.size(); i++){
			for(int j = i; j < words.size(); j++){
				if (compareWord(words.get(i), words.get(j)) == true){
			 		graph[i][j] = 1;
			 		graph[j][i] = 1;
				}
			}
		}
		return graph;
	}
	//compare two strings
	public static boolean compareWord(String a, String b){
		if (a.length() != b.length()){
			return false;
		}
		char[] achar = a.toCharArray();
		char[] bchar = b.toCharArray();
		int count = 0;
		for (int i = 0; i < a.length(); i++){
			if (achar[i] != bchar[i]){
				count++;
			}
		}
		if (count == 1){
			return true;
		} else {
			return false;
		}
	}

}