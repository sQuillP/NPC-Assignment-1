import java.util.*;
import java.io.*;


public class Solve3CNF
{


  int[][] graph;
  ArrayList<Integer> vals;
  int graphSize;

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


  private void formatSAT(String val,int i)
  {
    if((i+1)%3 == 0&&i!=vals.size()-1)
      System.out.print(val + ")&(");
    else if((i+1)%3 == 2)
     System.out.print("|"+val+"|");
    else
      System.out.print(val);
  }


  private void randomSAT(int size)
  {
    int n;
    ArrayList<Integer> assignments = new ArrayList<Integer>();
    System.out.print("(");
    for(int i = 0; i<vals.size(); i++)
      formatSAT(String.valueOf(vals.get(i)),i);
    System.out.println(")");
    for(int i = 1; i<size; i++)
    {
      n = (int)(Math.random()*2+1)%2 == 0? 1:-1;
      assignments.add(n*i);
    }
    System.out.print("(");
    for(int i = 0; i<vals.size(); i++)
    {
      if(assignments.contains(vals.get(i)))
        formatSAT("T",i);
      else
        formatSAT("F",i);
    }
    System.out.println(")");
  }

  private void printAssignments(Set<Integer> set, int numVariables)
  {
    System.out.print("3CNF: No.1 [n="+vals.size()+"]\n[");
    for(int i = 1; i<=numVariables; i++)
    {
      if(set.contains(i))
        System.out.print(i+":T ");
      else if(set.contains(-i))
        System.out.print(i+":F ");
      else
        System.out.print(i+":X ");
    }
    System.out.print("]\n(");
  }


  private void printSAT(Set<Integer> set)
  {
    String val;
    System.out.print(") ==>(");
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
    System.out.println(")");
  }



  public void find3SAT()
  {
    ArrayList<Integer> cliques = new ArrayList<Integer>(),
    randomVals = new ArrayList<Integer>();
    Set<Integer> set = new HashSet<Integer>();
    String val;
    int numVariables = 0;
    boolean valid = true;
    cliques.add(vals.get(0));
    cliques.add(vals.get(5));
    for(int i = 0 ;i<vals.size(); i++)
      if(Math.abs(vals.get(i))>numVariables)
        numVariables = vals.get(i);
    for(int i : cliques)
      set.add(i);
    if(valid)
    {
      printAssignments(set,numVariables);
      for(int i = 0; i<vals.size(); i++)
        formatSAT(String.valueOf(vals.get(i)),i);
      printSAT(set);
   }
   else
   {
     System.out.println("No Solution");
     randomSAT(numVariables);
   }
}



  /* should run a file and find a clique*/
  /*Reads only one line (graph) for the sake of testing*/
  public void readFile(String filename)
  {
    String line;
    StringTokenizer st;
    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));

      //#######################################################
      // for(int i = 0; i<10; i++)
      // {
        // System.out.println("Graph "+(i+1));
      st = new StringTokenizer(br.readLine()," ");
      graphSize = st.countTokens();
      graph = new int[graphSize][graphSize];
      vals = new ArrayList<Integer>();
      for(int j = 0; j<graphSize; j++)
        vals.add(Integer.parseInt(st.nextToken()));
      buildGraph();
      find3SAT();
    // }
    } catch(IOException E){
      System.out.println("Could not open file");
    }
  }


  public static void main(String[] args)
  {
    Solve3CNF test = new Solve3CNF();
    test.readFile("mycnf.txt");

  }

}





//
