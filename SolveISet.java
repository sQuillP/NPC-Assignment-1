import java.util.*;
import java.io.*;

public class SolveISet
{

  int[][] graph;

  SolveISet()
  {

  }


  public void print()
  {
    for(int i = 0; i<graph.length; i++)
    {
      for(int j = 0; j<graph.length; j++)
        System.out.print(graph[i][j]);
      System.out.println();
    }
  }



  private void compliment()
  {
    
  }



  //Read only one element
  public void readFile(String filename)
  {
    try {
      BufferedReader bf = new BufferedReader(new FileReader(filename));
      StringTokenizer st;
      int size = Integer.parseInt(bf.readLine()), col = 0;
      graph = new int[size][size];
      for(int i = 0; i<size; i++)
      {
        st = new StringTokenizer(bf.readLine()," ");
        while(st.hasMoreTokens())
          graph[i][col++] = Integer.parseInt(st.nextToken());
        col = 0;
      }
    }
    catch (IOException e)
    {
      System.out.println("Unable to open file "+filename);
    }
  }





  public static void main(String[] args)
  {
    SolveISet test = new SolveISet();
    test.readFile("graphs2021.txt");
    test.print();
  }


}
