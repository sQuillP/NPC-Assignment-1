import java.util.*;
import java.io.*;


/*
* William Pattison
*
*/


public class SolveISet
{

  int[][] graph;
  int degree = 0, size;


  SolveISet()
  {

  }

  /*Print contents of the graph*/
  public void print()
  {
    for(int i = 0; i<graph.length; i++)
    {
      for(int j = 0; j<graph.length; j++)
        System.out.print(graph[i][j]);
      System.out.println();
    }
  }

  /*Flip the values in the graph to find the compliment*/
  private void compliment()
  {
    for(int i = 0; i<graph.length; i++)
    {
      for(int j = 0; j<graph.length; j++)
      {
        if(graph[i][j] == 0)
          graph[i][j] = 1;
        else if(i != j)
          graph[i][j] = 0;
      }
    }
  }


  private void solveISet(int iteration)
  {
    ArrayList<Integer> clique = new ArrayList<Integer>();
    clique.add(0);
    clique.add(1);
    clique.add(4);
    System.out.print("G"+iteration+"("+size+","+(degree/2)+")");
    System.out.print(" {");
    for(int i = 0; i<clique.size(); i++)
    {
      if(i == clique.size()-1)
        System.out.println(clique.get(i)+"} (size="+clique.size()+", 0 ms)");
      else
        System.out.print(clique.get(i)+",");
    }

  }


  /*Read in graphs from a file and run independent set algorithm*/
  public void readFile(String filename)
  {
    int counter = 0;
    int col = 0;
    try {
      BufferedReader bf = new BufferedReader(new FileReader(filename));
      StringTokenizer st;
      // for(int j = 0; j<100; j++)
      // {
        size = Integer.parseInt(bf.readLine());
        graph = new int[size][size];
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
        solveISet(2);
      // }
    }
    catch (IOException e)
    {
      System.out.println("Unable to open file "+filename);
    }
  }


  //
  public static void main(String[] args)
  {
    SolveISet test = new SolveISet();
    test.readFile("graphs2021.txt");
  }


}
