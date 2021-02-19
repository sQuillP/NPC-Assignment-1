import java.util.*;
import java.io.*;
public class Solve3CNF
{
  int[][] graph;
  ArrayList<Integer> vals;

  Solve3CNF()
  {

  }


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


  public void buildGraph()
  {
    int c = 0;
    for(int i = 0; i<graph.length; i++)
    {
      if(i != 0 && i%3 == 0)
        c++;
      for(int j = 0; j<graph.length; j++)
      {
        if(i >= 3*c && i < 3*c + 3 && j >= 3*c && j < 3*c +3 || vals.get(j)  == -1*vals.get(i))
          graph[i][j] = 0;
        else
          graph[i][j] = 1;
      }
    }
  }


  /* should run a file and find a clique*/
  /*Reads only one line (graph) for the sake of testing*/
  public void readFile(String filename)
  {
    String line;
    StringTokenizer st;
    int size;
    //read one graph in and create the graph.
    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      st = new StringTokenizer(br.readLine()," ");
      size = st.countTokens();
      graph = new int[size][size];
      vals = new ArrayList<>();
      for(int i = 0; i<size; i++)
        vals.add(Integer.parseInt(st.nextToken()));

      buildGraph();


    } catch(IOException E){
      System.out.println("Could not open file");
    }
  }



  public static void main(String[] args)
  {
    Solve3CNF test = new Solve3CNF();
    test.readFile("cnfs2021.txt");
    test.print();

  }

}





//
