/* Aggregate.java
   CSC 225 - Summer 2018

   Some starter code for programming assignment 1, showing
   the command line argument parsing and the basics of opening
   and reading lines from the CSV file.

   B. Bird - 04/30/2018
*/


import java.io.*;

public class Aggregate{

	
	public static void showUsage(){
		System.err.printf("Usage: java Aggregate <function> <aggregation column> <csv file> <group column 1> <group column 2> ...\n");
		System.err.printf("Where <function> is one of \"count\", \"count_distinct\", \"sum\", \"avg\"\n");	
	}
	
	//calculate the row number
	public static int lineNum(String[] line){
		int n = 0;
		while(line[n] != null){
			n++;
		}
		return n;
	}
	
	// use quick sort, sort both array lines and 2D array items
	public static void sort(String[][] item, String[] line, int k){
		int n = lineNum(line);
		if (n == 0 || n ==1){
			return;
		}
		String[] same = new String[n];
		String[] leftsame = new String[n];
		String[] rightsame = new String[n];
		String[][] same_items = new String[n][item[0].length];
		String[][] leftsame_items = new String[n][item[0].length];
		String[][] rightsame_items = new String[n][item[0].length];
		int a = 0;
		int b = 0;
		int c = 0;
		String p = item[0][k];
		for (int i = 0; i < n; i++){
			if (p.compareTo(item[i][k]) > 0){
				leftsame[b] = line[i];
				leftsame_items[b] = item[i];
				b++;
			} else if (p.compareTo(item[i][k]) < 0){
				rightsame[c] = line[i];
				rightsame_items[c] = item[i];
				c++;
			} else {
				same[a] = line[i];
				same_items[a] = item[i];
				a++;
			}
			
		}
		sort(leftsame_items, leftsame, k);
		sort(rightsame_items, rightsame, k);
		
		int i,j,h;
		for(i = 0; i < b ; i++){
			item[i] = leftsame_items[i];
			line[i] = leftsame[i];
		}
		for(j = 0; j < a; j++){
			item[i+j] = same_items[j];
			line[i+j] = same[j];
		}	
		for(h = 0; h < c; h++){
			item[i+j+h] = rightsame_items[h];
			line[i+j+h] = rightsame[h];
		}
	}	
	
	//count the number of different row of group column
	public static double[] count(String[][] item, String[] line){
		int n = lineNum(line);
		double[] count_num = new double[n];
		count_num[0] = 1; 
		for (int i = 1; i < n; i++){
			if(!item[i-1][0].equals(item[i][0])){
				count_num[i] = 1;
			} else {
				count_num[i] = count_num[i-1]+1;
				for (int j = 1; j < item[0].length - 1; j++){
					if (!item[i-1][j].equals(item[i][j])){
						count_num[i] = 1;
					} else {
						count_num[i] = count_num[i-1] + 1;
					}
				}
			}
		}
		return count_num;
	}
	
	//sum the agg column with different rows
	public static double[] count_sum(String[][] item, String[] aline){
		int n = lineNum(aline);
		double[] sum = new double[n];
		int k = item[0].length - 1;
		sum[0] = Integer.valueOf(item[0][k]);
		for (int i = 1; i < n; i++){
			if(!item[i-1][0].equals(item[i][0])){
				sum[i] = Integer.valueOf(item[i][k]);
			} else {
				sum[i] = sum[i-1] + Integer.valueOf(item[i][k]);
				for (int j = 1; j < item[0].length - 1; j++){
					if (!item[i-1][j].equals(item[i][j])){
						sum[i] = Integer.valueOf(item[i][k]);
					} else {
						sum[i] = sum[i-1] + Integer.valueOf(item[i][k]);
					}
				}
			}
		}
		return sum;
	}
	
	//count the distinct part of the agg column
	public static double[] count_distinct(String[][] item, String[] line){
		int n = lineNum(line);
		double[] count_num = count(item, line);
		double[] distinct_num = new double[n];

		for (int i = 1; i < n; i++){
			if (count_num[i-1] == 1){
				distinct_num[i-1] = 1;
			}
			if(!item[i-1][0].equals(item[i][0])){
				distinct_num[i] = distinct_num[i-1]+1; 
			} else {
				distinct_num[i] = distinct_num[i-1];
				for (int j = 1; j < item[0].length; j++){
					if (!item[i-1][j].equals(item[i][j])){
						distinct_num[i] = distinct_num[i-1]+1; 
					} else {
						distinct_num[i] = distinct_num[i-1];
					}
				}
				
			}
		}
		return distinct_num;
	}
	
	//delete the repete group column before output
	public static void delete_repete(double[] count_num, String[] aline){
		int n = lineNum(aline);
		for (int i = 0; i < n-1; i++){
			if (count_num[i] < count_num[i+1]){
				aline[i] = null;
			}
		}
	}
	
	public static void main(String[] args){
		//At least four arguments are needed
		if (args.length < 4){
			showUsage();
			return;
		}
		String agg_function = args[0];
		String agg_column = args[1];
		String csv_filename = args[2];
		String[] group_columns = new String[args.length - 3];
		for(int i = 3; i < args.length; i++)
			group_columns[i-3] = args[i];
		
		if (!agg_function.equals("count") && !agg_function.equals("count_distinct") && !agg_function.equals("sum") && !agg_function.equals("avg")){
			showUsage();
			return;
		}
		
		BufferedReader br = null;
		
		try{
			br = new BufferedReader(new FileReader(csv_filename));
		}catch( IOException e ){
			System.err.printf("Error: Unable to open file %s\n",csv_filename);
			return;
		}
		
		String header_line;
		try{
			header_line = br.readLine(); //The readLine method returns either the next line of the file or null (if the end of the file has been reached)
		} catch (IOException e){
			System.err.printf("Error reading file\n", csv_filename);
			return;
		}
		if (header_line == null){
			System.err.printf("Error: CSV file %s has no header row\n", csv_filename);
			return;
		}
		
		//Split the header_line string into an array of string values using a comma
		//as the separator.
		String[] column_names = header_line.split(",");
		
		//As a diagnostic, print out all of the argument data and the column names from the CSV file
		//(for testing only: delete this from your final version)
	
		//... Your code here ...
		try{
			//print the header line of output 
			//includes group_columns and agg_function with agg_column
			header_line = group_columns[0];
			for (int i = 1; i < group_columns.length; i++){
				header_line = header_line.concat("," + group_columns[i]);
			}
			header_line = header_line.concat("," + agg_function + "(" + agg_column + ")");
			System.out.println(header_line);
			
			//match header line items(group columns) position with input items position			
			int[] column_num = new int[group_columns.length + 1];
			int k = 0;
			for (int i = 0; i < group_columns.length; i++){
				for (int j = 0; j < column_names.length; j++){
					if (column_names[j].equals(group_columns[i])){
						column_num[k] = j;
						k++;
					}
				}
			}
			
			//match agg column items position with input items position
			for (int i = 0; i < column_names.length ; i++){
				if (column_names[i].equals(agg_column)){
					column_num[k] = i;
				}
			}
			
			//copy required items from csv. only copy group column and agg column
			//put group column line items into string array called aline[]
			//put group column items and agg columns items into 2D-array called item[][]
			String[][] item = new String[100001][column_num.length]; 
			String[] aline = new String[100001];
			String line;
			int aline_num = 0;	
			while((aline[aline_num] = br.readLine()) != null){
				String[] newline = aline[aline_num].split(",");
				aline[aline_num] = newline[column_num[0]];
				item[aline_num][0] = newline[column_num[0]];
				for (k = 1; k< column_num.length - 1; k++){	
					item[aline_num][k] = newline[column_num[k]];
					aline[aline_num] = aline[aline_num].concat("," + item[aline_num][k]);		
				}
				item[aline_num][k] = newline[column_num[k]];
				aline[aline_num] = aline[aline_num].concat(",");
				aline_num++;
			}
			
			//sort the group columns
			for (k = group_columns.length; k >=0; k--){
				sort(item, aline, k);
			}
		
			int n = lineNum(aline);
			double[] count_num = count(item, aline);
			
			if (agg_function.equals("count")){
				delete_repete(count_num, aline);
				for (int i = 0; i < n; i++){
					if (aline[i] != null){
						System.out.println(aline[i] + count_num[i]);			
					}
				}
			}
			
			if (agg_function.equals("sum")){
				double[] sum = count_sum(item, aline);
				delete_repete(count_num, aline);
				for (int i = 0; i < n; i++){
					if (aline[i] != null){
						System.out.println(aline[i] + sum[i]);			
					}
				}
			}
			
			if (agg_function.equals("avg")){
				double[] sum = count_sum(item, aline);
				delete_repete(count_num, aline);
				for (int i = 0; i < n; i++){
					if (aline[i] != null){
						System.out.println(aline[i] + sum[i]/count_num[i]);			
					}
				}
			}
			if (agg_function.equals("count_distinct")){
				double[] distinct_num = count_distinct(item, aline);
				delete_repete(count_num, aline);
				for (int i = 0; i < n; i++){
					if (aline[i] != null){
						System.out.println(aline[i] + distinct_num[i]);			
					}
				}
			}
			
		} catch (IOException e){
			System.err.printf("Error reading file\n", csv_filename);
			return;
		}
	
	}

}


