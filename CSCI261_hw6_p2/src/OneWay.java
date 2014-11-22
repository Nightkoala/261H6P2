/**
 * OneWay.java
 * 
 * @author	Derek Brown
 *
 * Purpose:	To determine if there exists a single edge that you can add to the
 * 			graph that would make it strongly connected and if such an edge
 * 			exists, display it.
 */

import java.util.Scanner;

public class OneWay {
	
	// Constants
	public static final int MAX_VERTICES = 100000;
	public static final int MAX_OUTDEGREE = 100;

	// Hidden data members
	private int size;
	private int[][] adjList = new int[MAX_OUTDEGREE+1][MAX_VERTICES+1];
	private int[][] Gtranspose = new int[MAX_OUTDEGREE+1][MAX_VERTICES+1];
	private boolean[] seen;
	private int[] fin;
	private int time;
	private int bNumComps;
	private int aNumComps;
	
	// Constructor
	
	/**
	 * Constructor for creating an instance of an OneWay object, stores
	 * important info such as, the adjacency list, the transpose graph,
	 * the 'seen' array, and the time.
	 * 
	 * @param size	The number of vertices
	 * @param graph	The graph inputed by the user
	 */
	public OneWay( int size, int[][] graph ) {
		this.size = size;
		this.adjList = graph;
		seen = new boolean[size+1];
		fin = new int[size+1];
		for( int i = 1 ; i <= size ; i++ ) {
			seen[i] = false;
			fin[i] = Integer.MAX_VALUE;
		}//end for i
		time = 0;
		bNumComps = 0;
		aNumComps = 0;
	}//end OneWay constructor
	
	// Methods
	
	/**
	 * DFS helper function.
	 * 
	 * @param v		The vertex to be processed.
	 * @param graph	The graph the traversal will be performed on.
	 */
	public void DFS( int v, int[][] graph ) {
		seen[v] = true;
		int index = 0;
		while( graph[index][v] != 0 ) {
			if( !seen[graph[index][v]] ) {
				DFS( graph[index][v], graph );
			}//end if
			index++;
		}//end while
		time++;
		fin[v] = time;
	}//end DFS
	
	/**
	 * Creates the transpose of the graph given by user using the adjacency
	 * list.
	 */
	public void transpose() {
		int[] outdegree = new int[size+1];
		for( int i = 1 ; i <= size ; i++ ) {
			outdegree[i] = 0;
		}//end for i
		for( int c = 1 ; c <= size ; c++ ) {
			int i = 0;
			int v;
			while( adjList[i][c] != 0 ) {
				v = adjList[i][c];
				Gtranspose[outdegree[v]][v] = c;
				outdegree[v]++;
				i++;
			}//end while
		}//end for c
	}//end transpose
	
	/**
	 * The main algorithm for solving the problem,  Counts the number of
	 * strongly connected components in the original graph and then the number
	 * of strongly connected components in the transpose of the graph,
	 * 
	 * @return	True, if one of the counts == 1 and the other == 2,
	 * 			False, otherwise.
	 */
	public boolean numStrongComps() {
		for( int s = 1 ; s <= size ; s++ ) {
			if( !seen[s] ) {
				DFS( s, adjList );
				bNumComps++;
			}//end if
		}//end for s
		transpose();
		for( int i = 1 ; i <= size ; i++ ) {
			seen[i] = false;
			fin[i] = Integer.MAX_VALUE;
		}//end for i
		time = 0;
		for( int s = size ; s >= 1 ; s-- ) {
			if( !seen[s] ) {
				DFS( s, Gtranspose );
				aNumComps++;
			}//end if
		}//end for s
		if( bNumComps == 1 && aNumComps ==2 ) {
			return true;
		}//end if
		if( bNumComps == 2 && aNumComps == 1 ) {
			return true;
		}//end if
		return false;
	}//end numStrongComps

	/**
	 * The main logic for the program, Reads input from the user, creates the
	 * adjacency list, and then runs the numStrongComponents algorithm.
	 * 
	 * @param args	Command line arguments, unused.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner( System.in );
		String input;
		input = sc.next();
		int numVertices = Integer.parseInt( input );
		int[][] graph = new int[MAX_OUTDEGREE+1][MAX_VERTICES+1];
		int value;
		for( int v = 1 ; v <= numVertices ; v++ ) {
			input = sc.next();
			value = Integer.parseInt( input );
			int adj = 0;
			while( value != 0 ) {
				graph[adj][v] = value;
				input = sc.next();
				value = Integer.parseInt( input );
				adj++;
			}//end while
		}//end for v
		sc.close();
		
		OneWay O = new OneWay( numVertices, graph );
		boolean canDo = O.numStrongComps();
		if( canDo ) {
			System.out.println("YES");
		}//end if
		else {
			System.out.println("NO");
		}//end else
	}//end main
}//end OneWay class
