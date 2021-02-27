import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class solveClique {
	//graph read in
	 int[][] graph;

	 //all nodes in the graph
	 int[] allNodes;
	 //true number of edges if graph has been flipped
	 int trueNumEdges;
	 //list of nodes that are able to be considered for a particular click
	 ArrayList<Integer> nodesConsiderable;

	 //tracks the combinations
	 ArrayList<Integer> track;
	 public ArrayList<Integer> bestClique;

	 //number of nodes able to be considered
	 int numNodesConsiderable;



	 boolean cliqueFound;
	 int graphNumber = 0;
	 int numEdge = 0;
	 long startTime;
	 long endTime;
	 BufferedReader br;


	 solveClique(int[][] graph)
	 {
		 this.graph = graph;
	 }


	//find the maximum clique in the graph
	 //****FOR WILLIAM****- the while loop can easily be removed and you can use it to search for the clique size you want here
	 //If the clique you're searching for cant be found, findClique will be returned as false. if it's not false, the clique you need is found in bestClique
	public void findCliqueSizeN(int sizeSearchingFor) {
		int max = sizeSearchingFor;
		sizeSearchingFor = 2;
		nodesConsiderable = new ArrayList<>(100);

		//this loop is to continually try to find larger cliques
		while(sizeSearchingFor <= max) {
			cliqueFound = false;

		//if theoretically impossible, dont bother searching for it
		if (!cliquePossible(sizeSearchingFor)) {
			break;
		}


		//if it is possible a clique of this size exists, search for it

		   //consider all nodes with enough edges
			nodesConsiderable = new ArrayList<Integer>(sizeSearchingFor);
			for (int i = 0; i < allNodes.length; i++) {
				if (allNodes[i] >= sizeSearchingFor -1) {
					nodesConsiderable.add(i);
				}
			}


				    //if it wasnt able to find a clique of this size, stop searching for
					if (!findClique(nodesConsiderable.size(), sizeSearchingFor)) {
						break;
					}
		sizeSearchingFor++;


		}
		// printOutput();
		// bestClique = new ArrayList<Integer>(100);
}


	//begins the process of finding a clique of size cliqueSize out of numElements possible nodes
	public boolean findClique(int numElements, int cliqueSize) {
		track = new ArrayList<Integer>(100);

	    if(backtrack(nodesConsiderable.size(), cliqueSize, 0)) {
	    	return true;
	    }

	    return false;

	}

	//back track until all combinations have been tried or a clique has been found
	boolean backtrack(int numElements, int cliqueSize, int start) {
	    // reach the bottom of tree

		if (cliqueFound == true) {
			return true;
		}

	    if (cliqueSize == track.size()) {
	    	if (verifyClique()) {
	    		cliqueFound = true;
	    		return true;
	    	}

	    }


	    for (int i = start; i < numElements; i++) {
	    	if (cliqueFound == true)
	    		return true;
	        track.add(nodesConsiderable.get(i));

	        backtrack(numElements, cliqueSize, i + 1);
	        track.remove(track.size()-1);
	    }
	    return false;
	}


	//checks a new node being added to the clique against all other cliques
	public boolean verifyClique() {
		for (int j = 0; j < track.size(); j++) {
			for (int i = 0; i < track.size(); i++) {
				if (graph[track.get(j)][track.get(i)] == 0) {
					return false;
				}
			}
		}

		//this is our current best clique found
		bestClique = new ArrayList<>(track);
		return true;

	}




























	// //Opens file, reads the file, and calls function to find maximum clique
	// 	public static void main(String[] args) {
	//
	// 		int theoreticalMaxClique;
	//
	// 		solveClique test = new solveClique();
	//
	// 		//open input file to be read
	// 		test.openFile(args[0]);
	//
	// 		//read in opened file
	// 		while(test.readFile()) {
	//
	// 			//try to cut down on time solving by finding a theoretical maximum clique
	// 				theoreticalMaxClique = test.findTheoreticalMaxClique();
	//
	//
	// 				//solve the clique problem
	// 				test.findCliqueSizeN(theoreticalMaxClique);
	// 		}
	//
	// 	}


	//prints formatted final output
	public void printOutput() {
		 endTime = System.nanoTime();
	        System.out.print("G"+ graphNumber + " ("+ graph.length + "," + numEdge + ") {");
	        for (int i = 0; i < bestClique.size(); i++) {
	        	System.out.print(bestClique.get(i));
	        	if (i != bestClique.size() -1)
	        		System.out.print(",");
	        }
	        System.out.println("} (size=" + bestClique.size()+", " + ((endTime - startTime)/1000000) + " ms)");
	}






























	//using some easy analysis we can cut down on the upperbound of the cliques we test given the following:
		//1. To form a clique in a graph of size N, you need (N*(N-1))/2 edges in the graph
		//2. To form a clique of size X, you need X nodes, each with at least X-1 edges.
		//3. Figuring out the number of edges a particular node has allows us to choose better nodes when we solve the problem-
		     //obviously if we are looking for a clique of size 6, we are not going to consider nodes with fewer than 5 edges
		public int findTheoreticalMaxClique()
		{
		 //assuming all graphs have at least one edge in them, so max size is starts at 2
		 int counter = 1;
		 numEdge =0;
		 int theoreticalMax;
		 allNodes = new int[graph.length];


		 //traverse graph ignoring nodes connecting to themselves and the symmetrical half of the matrix
		 for(int i = 0 ;i< graph.length; i++) {
			    for(int j = counter; j<graph.length; j++) {

			        //count edges in graph as well as the number of edges each node has
			    	if (graph[i][j] == 1) {
				    	allNodes[i] +=1;
				    	allNodes[j] +=1;
				    	numEdge++;
			    	}
			    }
			    counter++;
		    }

		 //A complete graph is made up of (n*(n-1))/2 edges, if we solve for the edges and apply the quadratic formula, it gives us a better upper bound on the max clique possible in the graph.
		 theoreticalMax = (int)(1 + Math.pow((1 + 8*numEdge),.5)/2.0);


		 return theoreticalMax;

		}






	//this is much like finding the theoretical max, but this function searches for different things and is called every time a new clique size is being searched for in the same graph
	public boolean cliquePossible(int sizeSearchingFor) {
		int nodesWithAtLeastNEdges = 0;
		for (int i = 0; i < allNodes.length; i++) {

			//if the node we're looking at has at least k-1 edges, it is possible for it to be a node in a complete graph of size k
			if (allNodes[i] >= sizeSearchingFor -1)
				nodesWithAtLeastNEdges +=1;

			//if we have enough of these nodes to find a graph, proceed
			if (nodesWithAtLeastNEdges >= sizeSearchingFor) {
				return true;

			}



		}
		return false;
	}





//ALL CODE BELOW THIS POINT IS RELATED TO READING THE GRAPHS IN


	//reads the file into graph[][]
	public boolean readFile()
	{
	  startTime = System.nanoTime();
	  graphNumber++;
	  String line;
	  String[] tokens;
	  int size;
	  //read one graph in and create the graph.
	  try {
	    line = br.readLine();
	    tokens = line.split(" ");
	    size = Integer.parseInt(tokens[0]);
	    if (size == 0) {
	    	br.close();
	    	return false;
	    }
	    graph = new int[size][size];

	    for (int currentRow = 0; currentRow < size; currentRow++) {
	    	 line = br.readLine();
	 	      tokens = line.split(" ");
		    for(int currentColumn = 0; currentColumn< size; currentColumn++) {
		      graph[currentRow][currentColumn] = Integer.parseInt(tokens[currentColumn]);
		    }

	    }

	  } catch(IOException E){
	    System.out.println("Could not read file");
	  }

	  return true;
	}




	//opens buffered reader and throws exception if file unable to be opened
	public void openFile(String filename)
	{

	  try {
	     br = new BufferedReader(new FileReader(filename));
	  } catch(IOException E){
	    System.out.println("Could not open file");
	  }
	}


	//prints all rows and columns of graph for verification purposes
	  public void printGraph()
	  {

		  int counter = 1;
		  System.out.println("NEW GRAPH");


			 for(int i = 0 ;i< graph.length; i++) {
				 for (int x = 0; x< counter; x++)
			        	System.out.print("x");

				    for(int j = counter; j<graph.length; j++) {

				        System.out.print(graph[i][j]);
				    }
				    counter++;
				    System.out.println();
			    }
	  }

	  public void flipGraph() {
		  trueNumEdges = 0;
		  int counter = 0;
		  for(int i = 0 ;i < graph.length; i++) {
			    for(int j = counter; j<graph.length; j++) {
			        //count edges in graph
			    	if (graph[i][j] == 1)
				    	trueNumEdges++;
			    }
			    counter++;
		    }



		  for (int currentRow = 0; currentRow < graph.length; currentRow++) {
			    for(int currentColumn = 0; currentColumn< graph.length; currentColumn++) {
			    	if (graph[currentRow][currentColumn] == 1 && currentRow != currentColumn )
			    		graph[currentRow][currentColumn] = 0;
			    	else
			    		graph[currentRow][currentColumn] = 1;

			    }

		    }
	  }

}
