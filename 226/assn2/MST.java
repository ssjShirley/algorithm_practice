/* MST.java
   CSC 226 - Fall 2018
   Problem Set 2 - Template for Minimum Spanning Tree algorithm
   
   The assignment is to implement the mst() method below, using Kruskal's algorithm
   equipped with the Weighted Quick-Union version of Union-Find. The mst() method computes
   a minimum spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in time O(m log m)
   on a graph with n vertices and m edges.
   
   This template includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
       java MST
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. graphs.txt), run the program with
       java MST graphs.txt
   
   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:
   
       <number of vertices>
       <adjacency matrix row 1>
       ...
       <adjacency matrix row n>
   	
   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following
   
   3
   0 1 0
   1 0 2
   0 2 0
   	
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the mst() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).
   
   (originally from B. Bird - 03/11/2012)
   (revised by N. Mehta - 10/9/2018)
*/

import java.util.Scanner;
import java.io.File;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.*;



public class MST {

	//create an edge class
    private static class Edge implements Comparable<Edge>{ 
        int S, D, W; 
        public int compareTo(Edge compareEdge) {
            return this.W-compareEdge.W; 
        }
    }; 
  	
  	//create a subset class
    private static class subset{ 
        int parent, rank; 
    }; 
  
  	//create Graph value
	private static int V, E; 
    private static Edge edge[]; 
    
    //initial Graph value
    MST(int v, int e) { 
       	V = v; 
        E = e; 
        edge = new Edge[E]; 
        for (int i=0; i<e; ++i) 
            edge[i] = new Edge(); 
    }
    
    public static int find(subset s[], int i) 
    { 
        // find root and make root as parent of i (path compression) 
        if (s[i].parent != i) 
            s[i].parent = find(s, s[i].parent); 
        return s[i].parent; 
    } 
  
    // A function that does union of two sets of x and y 
    // (uses union by rank) 
    public static void Union(subset s[], int x, int y){ 
        int xroot = find(s, x); 
        int yroot = find(s, y); 
        if (s[xroot].rank < s[yroot].rank) 
            s[xroot].parent = yroot; 
        else if (s[xroot].rank > s[yroot].rank) 
            s[yroot].parent = xroot; 
        else{ 
            s[yroot].parent = xroot; 
            s[xroot].rank++; 
        } 
    }
    
    // The main function to construct MST using Kruskal's algorithm 
    public static int KruskalMST() { 
        Edge result[] = new Edge[V];  // Tnis will store the resultant MST 
        int e = 0; 
        int i = 0; 
        for (i=0; i<V; ++i) 
            result[i] = new Edge(); 
        Arrays.sort(edge); 
  
        // Allocate memory for creating V ssubsets 
        subset s[] = new subset[V]; 
        for(i=0; i<V; ++i) 
            s[i]=new subset(); 
  
        // Create V subsets with single elements 
        for (int v = 0; v < V; ++v){ 
            s[v].parent = v; 
            s[v].rank = 0; 
        } 
  
        i = 0;  // Index used to pick next edge 
        while (e < V - 1){ 
            Edge next_edge = new Edge(); 
            next_edge = edge[i++]; 
            int x = find(s, next_edge.S); 
            int y = find(s, next_edge.D); 
            if (x != y){ 
                result[e++] = next_edge; 
                Union(s, x, y); 
            } 
        } 
  		int t = 0;
        for (i = 0; i < e; ++i) 
            t = t + result[i].W;
        
        return t;
    }

	
    static int mst(int[][][] adj) {
    
		int n = adj.length;
		int e = 0;
		for (int i = 0; i < n; i++){
			e = e + adj[i].length;
		}
		e = e/2;
		MST graph = new MST(n, e);
		int v = 0;
		int ve = 0;
		for (int i = 0; i < e; i++){
			while(Repeat(graph,adj,v,ve,i)){
				ve++;
				if(ve >= adj[v].length){
					ve=0;
					v++;
				}
			}
			graph.edge[i].S = v;
			graph.edge[i].D = adj[v][ve][0];
			graph.edge[i].W = adj[v][ve][1];
			ve++;
			if(ve >= adj[v].length){
				ve = 0;
				v++;
			}

		}
		int totalWeight = graph.KruskalMST();;
		return totalWeight;
		
    }
	public static boolean Repeat(MST graph, int[][][] adj,int v, int ve, int i){
		for (int j = 0; j < i; j++){
			if(adj[v][ve][1] == graph.edge[j].W && v == graph.edge[j].D && adj[v][ve][0] == graph.edge[j].S){
				return true;
			}
		}
		return false;
	
	}

    public static void main(String[] args) {
	/* Code to test your implementation */
	/* You may modify this, but nothing in this function will be marked */

	int graphNum = 0;
	Scanner s;

	if (args.length > 0) {
	    //If a file argument was provided on the command line, read from the file
	    try {
		s = new Scanner(new File(args[0]));
	    }
	    catch(java.io.FileNotFoundException e) {
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n",args[0]);
	}
	else {
	    //Otherwise, read from standard input
	    s = new Scanner(System.in);
	    System.out.printf("Reading input values from stdin.\n");
	}
		
	//Read graphs until EOF is encountered (or an error occurs)
	while(true) {
	    graphNum++;
	    if(!s.hasNextInt()) {
		break;
	    }
	    System.out.printf("Reading graph %d\n",graphNum);
	    int n = s.nextInt();

	    int[][][] adj = new int[n][][];
	    
	    int valuesRead = 0;
	    for (int i = 0; i < n && s.hasNextInt(); i++) {
		LinkedList<int[]> edgeList = new LinkedList<int[]>(); 
		for (int j = 0; j < n && s.hasNextInt(); j++) {
		    int weight = s.nextInt();
		    if(weight > 0) {
			edgeList.add(new int[]{j, weight});
		    }
		    valuesRead++;
		}
		adj[i] = new int[edgeList.size()][2];
		Iterator it = edgeList.iterator();
		for(int k = 0; k < edgeList.size(); k++) {
		    adj[i][k] = (int[]) it.next();
		}
	    }
	    if (valuesRead < n * n) {
		System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
		break;
	    }
		
	    int totalWeight = mst(adj);
	    System.out.printf("Graph %d: Total weight of MST is %d\n",graphNum,totalWeight);

				
	}
    }

    
}
