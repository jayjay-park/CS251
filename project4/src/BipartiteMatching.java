import java.util.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

public class BipartiteMatching {
    HashMap<Integer,ArrayList<Node>> adj_list;          // adjacency list representation of graph
    int []match;                                        // which node a node is matched with
    int M, N;                                           // M, N are number of machines and cards respectively    
    boolean []used;                                     // whether a node has been already used in matching or not

    /** TODO
    * initialize constructor function. 
    * @param M : number of machines
    * @param N : number of cards
    */
    public BipartiteMatching(int M, int N)
    {
		this.M = M;
        this.N = N;
        this.adj_list = new HashMap<Integer, ArrayList<Node>>();
        this.match = new int[M+N+1];  // number of matched node?
        this.used = new boolean[M+N+1];

        // initialize
        for (int i = 0; i < match.length;  i++) {
            match[i] = -1;
        }
        for (int i = 0; i < used.length; i++) {
            used[i] = false;
        }
    }

    /* ! Going to use index that starts from 1.
     * ! 1 ~ M | M+1 ~ M+N
     */

    /** TODO
     * gradually build the graph by inserting edges
     * this function inserts all the nodes connected with node u

     * hint : remember that this is an undirected graph.

     * @param u : node under consideration -> card
     * @param node_list : all the nodes connected with node u
    **/
    public void insList(int u, int []node_list)
    {		
        // create the graph
        // Given the input, each line is corresponding machine list, the i-th line indicates the i-th card.
        // add undirected edge from the i-th card to the corresponding machines.
        // use hashmap

        // u is 0 based index
        int cardIndex = u + M + 1;

        ArrayList<Node> machineList = new ArrayList<Node>();

        for (int i = 0; i < node_list.length; i++) {
            Node temp = new Node(node_list[i]);
            machineList.add(temp);
        }
        adj_list.put(cardIndex, machineList);

        // DEBUG
        /* System.out.println("*----- Adding Node -----*");
        System.out.printf("Card Index: %d\n", cardIndex - M);

        for (int i = 0; i < node_list.length; i++) {
            System.out.printf("Machine: %d\n", node_list[i]);
        } */
        // print hash map
        System.out.printf("CardIndex: %d\n", cardIndex - M);
        for (int j = 0; j < adj_list.get(cardIndex).size(); j++) {
            System.out.printf("M: %d\n", adj_list.get(cardIndex).get(j).node_id);
        }
        System.out.println();
       
    }

    /** TODO
    * implement DFS function
    *
    * @param v : starting node for DFS -> Machine
    *
    * @return true if there is an augment path; if no, return false.
    */
    // finding the augmented path - algorithm 2
    boolean dfs(int v)
    {
        //System.out.printf("dfs input: %d\n", v);
        if (used[v] == true) {
            return false;
        }

        used[v] = true;

        if (adj_list.get(v) != null) {
            for (int u = 0; u < adj_list.get(v).size(); u++) {
                Node temp = adj_list.get(v).get(u);
                int id = temp.node_id;
                
                if (match[id] == -1) {
                    match[id] = v;
                    match[v] = id;
                    return true;
                }
                else if (match[id] != -1) {
                    int w = match[id];
                    if ((used[w] == false) && (dfs(w) == true)) {
                        match[id] = v;
                        match[v] = id;
                        return true;
                    }
                }
                
            }
        }

        return false;
    }

    /** TODO
    *
    * implement the bipartite matching algorithm
    * traverse the nodes
    * call dfs to see if there exists any augment path
    *
    * @return the max matching
    */
    int bipartiteMatching()
    {
        int res = 0;

        for (int i = 1; i <= M; i++) {           
        
            // initialize
            for (int j = 1; j <= M; j++) {
                used[j] = false;
            } 
            if (match[i] == -1) {
                if (dfs(i) == true) {
                    res++;
                }
            }
        }

        return res;
    }

    public static void main(String []args)
    {
        try {
            BipartiteMatching model = new BipartiteMatching(0, 0);
            File myObj = new File("./project4/src/sampleBipartiteTest.txt");
            Scanner myReader = new Scanner(myObj);
            int line = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                if(line == 0)
                {
                    String []str = data.split(" ");
                    int M = Integer.parseInt(str[0]);
                    int N = Integer.parseInt(str[1]);
                    System.out.println(M + "  " + N);
                    model = new BipartiteMatching(M, N);
                }
                else
                {
                    String []str = data.split(" ");
                    int [] input = new int[str.length];
                    for (int i=0; i<str.length; i++)
                        input[i] = Integer.parseInt(str[i]);

                    System.out.println(input);
                    model.insList(line-1, input);
                }
                line += 1;
            }
            myReader.close();
            int res = model.bipartiteMatching();
            System.out.println("BipartiteMatching is: "+res);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}