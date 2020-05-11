/**
 *
 * @author Rahnuma Islam Nishat
 * September 19, 2018
 * CSC 226 - Fall 2018
 */
import java.util.*;
import java.io.*;

public class QuickSelect {
    public static int QuickSelect(int[] A, int k) {
    	if (k > A.length | k < 0){
    		return -1;
    	}
    	ArrayList<Integer> a = new ArrayList<Integer>();
    	for(int i = 0; i < A.length; i++){
    		a.add(A[i]);
    	}
    	int m = Partition(a);
    	a = QuickSort(a, m);
        return a.get(k-1);
    }
    public static ArrayList<Integer> QuickSort(ArrayList<Integer> A, int median){
    	int n = A.size();
    	if (n < 7){
    		Collections.sort(A);
    		return A;
    	}
    	ArrayList<Integer> left = new ArrayList<Integer>();
    	ArrayList<Integer> right = new ArrayList<Integer>();
    	ArrayList<Integer> e = new ArrayList<Integer>();
    	for(int i = 0; i < n; i++){
    		if (A.get(i) < median){
    			left.add(A.get(i));
    		} else if (A.get(i) > median){
    			right.add(A.get(i));
    		} else {
    			e.add(A.get(i));
    		}
    	}
    	QuickSort(left, Partition(left));
    	QuickSort(right, Partition(right));
    	left.addAll(e);
    	left.addAll(right);
    	return left;		
    }
    
    //partition A into n/7 subsets, sort each subsets and find each median of the subset
    //find the median of the whole array
    public static int Partition(ArrayList<Integer> A){
    	int n = A.size();
    	if(n < 7){
    		return A.get(0);
    	}
    	int m = n/7;
        int curr = 0;
        Integer[] median = new Integer[m];
        for (int i = 0; i < m; i++) {
        	Integer[] a = new Integer[7];
        	for (int j = 0; j < 7; j++) {
        		if (curr == n) {
        			//System.out.println("yes");
        			break;
        		}
        		a[j] = A.get(curr++);
        	}
			Arrays.sort(a);//O(c)
			median[i] = a[3];
			//alist.add(a);
        }
    	return (median[median.length/2]);
    } 
    
    public static void main(String[] args) {
        Scanner s;
        int[] array;
        int k;
        if(args.length > 0) {
	    try{
		s = new Scanner(new File(args[0]));
		int n = s.nextInt();
		array = new int[n];
		for(int i = 0; i < n; i++){
		    array[i] = s.nextInt();
		}
	    } catch(java.io.FileNotFoundException e) {
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n", args[0]);
        }
	else {
	    s = new Scanner(System.in);
	    System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
	    int temp = s.nextInt();
	    ArrayList<Integer> a = new ArrayList<Integer>();
	    while(temp >= 0) {
		a.add(temp);
		temp=s.nextInt();
	    }
	    array = new int[a.size()];
	    for(int i = 0; i < a.size(); i++) {
		array[i]=a.get(i);
	    }
	    
	    System.out.println("Enter k");
        }
        k = s.nextInt();
        System.out.println("The " + k + "th smallest number is the list is "
			   + QuickSelect(array,k));	
    }
}
