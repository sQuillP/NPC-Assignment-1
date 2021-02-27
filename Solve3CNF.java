import java.util.*;
import java.io.*;


/*Solve3CNF program uses the clique algorithm to find a satisfiable boolean formula.
*
* William Pattison
* IT 328
* NPC Programming Assignment 1
*/

public class Solve3CNF
{

  /*Adjacency matrix that holds CNF*/
  int[][] graph;

  /*Size of the graph*/
  int graphSize;

  /*Total time for the program to run*/
  long totalTime;

  /*Hold the boolean variables in the order that they were read*/
  ArrayList<Integer> vals;



  Solve3CNF(String filename)
  {
    readFile(filename);
  }


  /*Print the 3CNF and adjacency matrix (debug)*/
  public void print()
  {
    for(int i = 0 ;i< vals.size(); i++)
      System.out.print(vals.get(i)+" ");
      System.out.println();
    for(int i = 0; i<graph.length; i++)
    {
      for(int j = 0; j<graph.length; j++)
        System.out.print(graph[i][j]);
      System.out.println();
    }
  }


  /*Create a graph based on the 3CNF input. No adjacent nodes are connected to each other*/
  public void buildGraph()
  {
    int c = 0;
    for(int i = 0; i<graph.length; i++)
    {
      if(i != 0 && i%3 == 0)
        c++;
      for(int j = 0; j<graph.length; j++)
      {
        if(i>=3*c&&i<3*c+3&&j>=3*c&&j<3*c+3&& j != i||vals.get(j)==-1*vals.get(i))
          graph[i][j] = 0;
        else
          graph[i][j] = 1;
      }
    }
  }


  /*Appropriately formats a boolean variable in the 3SAT equation*/
  private void formatSAT(String val,int i)
  {
    if((i+1)%3 == 0&&i!=vals.size()-1)
      System.out.print(val + ")&(");
    else if((i+1)%3 == 2)
     System.out.print("|"+val+"|");
    else
      System.out.print(val);
  }


  /*Assigns random values to the 3SAT and prints the boolean formula as a result.*/
  private void randomSAT(int size, long totalTime)
  {
    int n;
    ArrayList<Integer> assignments = new ArrayList<Integer>();
    String append;
    System.out.print("(");
    for(int i = 0; i<vals.size(); i++)
      formatSAT(String.valueOf(vals.get(i)),i);
    System.out.print(")");
    for(int i = 1; i<=size; i++)
    {
      n = (int)(Math.random()*2+1)%2 == 0? 1:-1;
      assignments.add(n*i);
    }
    System.out.print("[");
    for(int i = 0; i<assignments.size(); i++)
    {
      append = assignments.get(i)<0?":F":":T";
      if(i == assignments.size()-1)
        System.out.print((i+1)+append);
      else
        System.out.print((i+1)+append+"|");
    }
    System.out.print("] ==> (");
    for(int i = 0; i<vals.size(); i++)
    {
      if(assignments.contains(vals.get(i)))
        formatSAT("T",i);
      else
        formatSAT("F",i);
    }
    System.out.println(") ("+totalTime+" ms)");
  }


  /*Print the assigned values that would satisfy the 3SAT*/
  private void printAssignments(Set<Integer> set, int numVariables)
  {
    System.out.print("[");
    for(int i = 1; i<=numVariables; i++)
    {
      if(set.contains(i))
        System.out.print(i+":T ");
      else if(set.contains(-i))
        System.out.print(i+":F ");
      else
        System.out.print(i+":X ");
    }
    System.out.print("]");
  }


  /*Print the SAT formula with the true and false value assignments.
  Helper method for find3SAT().*/
  private void printSAT(Set<Integer> set)
  {
    String val;
    System.out.print(" ==>(");
    for(int i = 0; i<vals.size(); i++)
    {
      if(set.contains(vals.get(i)))
        val = "T";
      else if(set.contains(-vals.get(i)))
        val = "F";
      else
        val = "X";

      formatSAT(val,i);
    }
    System.out.print(")");
  }


  /*Method finds the 3SAT of a given 3CNF and prints the solution.
  The cliques arraylist contains the k-clique that has been found
  in the graph.*/
  public void find3SAT(int graphNumber)
  {
    solveClique solve = new solveClique(graph);
    long start = System.currentTimeMillis();
    solve.findCliqueSizeN(solve.findTheoreticalMaxClique());
    ArrayList<Integer> cliques =  solve.bestClique;
    totalTime = System.currentTimeMillis()-start;
    Set<Integer> set = new HashSet<Integer>();
    String val;
    int numVariables = 0;
    for(int i = 0; i<vals.size(); i++)
      if(Math.abs(vals.get(i))>numVariables)
        numVariables = vals.get(i);
    for(int i : cliques)
      set.add(vals.get(i));
    System.out.print("\n\n3CNF No."+graphNumber+":[n="+numVariables+" k="+vals.size()/3+"]  ");
    if(cliques.size()>=graph.length/3) //If a there exists an assignment that satisfies 3SAT
    {
      printAssignments(set,numVariables);
      System.out.print("\n(");
      for(int i = 0; i<vals.size(); i++)
        formatSAT(String.valueOf(vals.get(i)),i);
      System.out.print(") ");
      printAssignments(set,numVariables);
      printSAT(set);
      System.out.println(" ("+totalTime+" ms)");
   }
   else
   {
     System.out.println("No Solution.");
     randomSAT(numVariables,totalTime);
   }
}


  /* Reads a file and runs 3SAT algorithm USE THIS TO RUN THE PROGRAM*/
  public void readFile(String filename)
  {
    String line;
    StringTokenizer st;
    int counter = 0;
    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      line = br.readLine();
      while(line != null)
      {
        counter++;
        st = new StringTokenizer(line," ");
        graphSize = st.countTokens();
        graph = new int[graphSize][graphSize];
        vals = new ArrayList<Integer>();
        for(int j = 0; j<graphSize; j++)
          vals.add(Integer.parseInt(st.nextToken()));
        buildGraph();
        find3SAT(counter);
        line = br.readLine();
      }
    } catch(IOException E){
      System.out.println("Could not open file");
    }
  }
}
