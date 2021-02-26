import java.util.*;
import java.io.*;


/*
* SolveISet will find an independent set from a given graph by inverting the bits and
* finding a clique.
*
* William Pattison
* IT 328
* NPC programming assignment 1
*/


public class SolveISet
{

  /*Adjacency matrix*/
  int[][] graph;

  /*Degree and the size of the graph */
  int degree = 0, size;

  /*Computing time for finding clique.*/
  long totalTime;

  /*Print contents of the graph debug purposes*/
  public void print()
  {
    for(int i = 0; i<graph.length; i++)
    {
      for(int j = 0; j<graph.length; j++)
        System.out.print(graph[i][j]);
      System.out.println();
    }
  }


  /*Method will find an independent set given a certain graph input.*/
  private void solveISet(int iteration)
  {
    long start = System.currentTimeMillis();
    // Assign clique algorithm output to the clique ArrayList.
    ArrayList<Integer> clique = new ArrayList<Integer>();
    //---------------------------------------------
    totalTime = System.currentTimeMillis()-start;
    System.out.print("\nG"+iteration+"("+size+","+(degree/2)+")");
    System.out.print(" {");
    for(int i = 0; i<clique.size(); i++)
    {
      if(i == clique.size()-1)
        System.out.print(clique.get(i));
      else
        System.out.print(clique.get(i)+",");
    }
    System.out.println("} (size="+clique.size()+", "+totalTime+" ms)");
  }


  /*Read in graphs from a file and run independent set algorithm,
  Prints out the results. USE THIS TO RUN THE PROGRAM*/
  public void readFile(String filename)
  {
    int counter = 0, iter = 0;
    int col = 0;
    String input;
    try {
      BufferedReader bf = new BufferedReader(new FileReader(filename));
      StringTokenizer st;
      input = bf.readLine();
      while(input!=null)
      {
        size = Integer.parseInt(input);
        graph = new int[size][size];
        iter++;
        for(int i = 0; i<size; i++)
        {
          st = new StringTokenizer(bf.readLine()," ");
          while(st.hasMoreTokens())
          {
            graph[i][col] = Integer.parseInt(st.nextToken())==1&&col!=i?0:1;
            if(graph[i][col] == 0)
              counter++;
            col++;
          }
          col = 0;
        }
        degree = counter;
        counter = 0;
        solveISet(iter);
        input = bf.readLine();
    }
  }
    catch (IOException e)
    {
      System.out.println("Unable to open file "+filename);
    }
  }
}
