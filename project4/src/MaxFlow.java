import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MaxFlow
{
    HashMap<Integer,ArrayList<Edge>> adj_list;      // adjacency list representation of graph
    int []parent;                                   // parent array used in bfs
    int N;                                          // total number of nodes

    /** TODO
    * initialize constructor function. 
    * @param N : number of nodes
    */
    public MaxFlow(int N)
    {
        this.N = N; // Q. is node number of drainage points?
        this.parent = new int[N+2];   // Q. is this size correct? 
        this.adj_list = new HashMap<Integer, ArrayList<Edge>>();

        for (int i = 0; i < N+2; i++) {
            parent[i] = -1;
        }

        // ! if an edge say (v_k, v_m) is not specified in the input file,
        // ! then you will add the edge from those two vertices with flow rate of 0.
        for (int i = 0; i < N + 2; i++) {
            for (int j = 0; j < N+2; j++) {
                Edge temp = new Edge(i, j, 0);

                if (adj_list.get(i) != null) {
                    adj_list.get(i).add(temp);
                }
                else {
                    ArrayList<Edge> list = new ArrayList<Edge>();
                    list.add(temp);
                    adj_list.put(i, list);
                }
            }
        }

    }

    /** TODO
    * gradually build the graph by inserting edges
    * this function inserts a new edge into the graph
    *
    * hint : remember to consider the opposite direction of flow
    *
    * @param source : source node
    * @param destination : destination node
    * @param flow_rate : maximum rate of flow through the edge
    */
    public void insEdge(int source, int destination, int flow_rate)
    {
        //System.out.printf("source: %d destination: %d flow_rate: %d\n", source, destination, flow_rate);
        // check source in hash map
        Edge newEdge = new Edge(source, destination, flow_rate);
        // Edge reverse = new Edge(destination, source, 0);


        //ArrayList<Edge> edgeList = new ArrayList<Edge>();
        //edgeList.add(newEdge);
        adj_list.get(source).set(destination, newEdge);
        //adj_list.put(source, edgeList);


        // ! opposite direction of flow
        /* if (adj_list.get(destination) != null) {
            adj_list.get(destination).add(reverse);
        }
        else {
            ArrayList<Edge> edgeList = new ArrayList<Edge>();
            edgeList.add(reverse);
            adj_list.put(destination, edgeList);
        } */

        /*for (int i = 0; i < adj_list.size(); i++) {
            for (int j = 0; j < adj_list.get(i).size(); j++) {
                System.out.printf("key: %d %d %d\n", i, adj_list.get(i).get(j).destination, adj_list.get(i).get(j).flow_rate);
            }
        }*/
    }

    /** TODO
    * implement BFS function        
    *
    * @return true if there is a path; if no, return false.
    */
    boolean bfs()
    {
        Queue<Integer> q = new LinkedList<>();
        boolean v[] = new boolean[N+2];
        for (int i = 0; i < N+2; i++) {
            v[i] = false;
        }

        // add the source to queue
        q.add(0);
        v[0] = true;

        while (!q.isEmpty()) {
            //System.out.println(q);
            //System.out.println("Queue");

            int current = q.remove();
            for (Edge e : adj_list.get(current)) {
                if ((e.flow_rate > 0) && (v[e.destination] == false)) {
                    q.add(e.destination);
                    v[e.destination] = true;
                    parent[e.destination] = current;
                    if (e.destination == N+1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** TODO
    * implement path augmentation
    *
    * traverse the graph using BFS to find a path from source to sink
    * find the possible amount of flow along the path
    * add the flow to the total maximum flow
    * update the flow rate of the edges along the path 
    * repeat as long as a path exist from source to sink with nonzero flow
    *
    * @return maximum amount of flow
    */
    int pathAugmentation()
    {
        
        int maxFlow = 0;
        int flow = Integer.MAX_VALUE;

        while (bfs()) {
            int min = Integer.MAX_VALUE;

         for (int i = N+1; i != 0; i = parent[i]) {
                
                if ( min > getFlow(parent[i], i)) {
                    min = getFlow(parent[i], i);
                }
                flow = min;
            }
            /* while (current != 0) {
                if (min > getFlow(parent[current], current)) {
                    current = getFlow(parent[current], current);
                }
                current = parent[current];
                flow = min;
            } */
            
            for (int i = N+1; i != 0; i = parent[i]) {
                setFlow(parent[i], i, getFlow(parent[i], i)-flow);
                setFlow(i, parent[i], getFlow(i, parent[i])+flow);
            }
            /*int traverse = N+1;
            while (traverse != 0) {
                setFlow(parent[traverse], traverse, getFlow(parent[], destination));
            } */
            
            /* for (int i = 0; i < N+2; i++) {
                System.out.printf("%d ", parent[i]);
            }
            System.out.println(); */
            maxFlow += flow;
            Arrays.fill(parent, -1);

        }
        return maxFlow;
    }

    /** TODO
    * get the flow along a certain edge
    *
    * @param source : source node of the directed edge
    * @param destination : destination node of the directed edge
    *
    * @return flow rate along the edge
    */
    int getFlow(int source, int destination)
    {
        return adj_list.get(source).get(destination).flow_rate;
        /* for (int i = destination; i != source; i = parent[i]) {
            int prev = parent[i];
            int capacity = adj_list.get(prev).get(i).flow_rate;
            if (capacity < flow) {
                flow = capacity;
            }
        } */


        /*int flow = 0;
        int start = source;
        int next = -1;

        for (int i = 0; i < N+2; i++) {
            if (parent[i] == start) {
                next = i;
            }
            flow += adj_list.get(start).get(next).flow_rate;
            start = next;
            if (next == destination) {
                return flow;
            }
        }*/
    }

    /** TODO
    * set the value of flow along a certain edge
    *
    * @param source : source node of the directed edge
    * @param destination : destination node of the directed edge
    * @param flow_rate : flow rate along the edge        
    */
    void setFlow(int source, int destination, int flow_rate)
    {
       // find bottle neck -> given flow_rate
       // capacity - bottle neck to original graph
       // + bottle neck to reverse graph
       
       /* for (int i = destination; i != source; i = parent[i]) {
            int prev = parent[i];
            adj_list.get(prev).get(i).flow_rate -= flow_rate;
            adj_list.get(i).get(prev).flow_rate += flow_rate;
       } */
       //System.out.println("set flow");
       //System.out.println(adj_list.get(source).get(destination).flow_rate);
       adj_list.get(source).get(destination).flow_rate = flow_rate;
       //System.out.println(adj_list.get(source).get(destination).flow_rate);

    }

    public static void main(String []args)
    {

        try {
            MaxFlow obmax = new MaxFlow(0);
            File myObj = new File("./project4/src/sampleMaxFlowData.txt");
            Scanner myReader = new Scanner(myObj);
            int line = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                if(line == 0)
                {
                    int tot = Integer.parseInt(data);
                    System.out.println(tot);
                    obmax = new MaxFlow(tot);
                }
                else
                {
                    String []comp = data.split(" ");
                    int s = Integer.parseInt(comp[0]);
                    int d = Integer.parseInt(comp[1]);
                    int f = Integer.parseInt(comp[2]);
                    System.out.println(s+" "+d+" "+f);
                    obmax.insEdge(s, d, f);
                }
                line += 1;
            }
            myReader.close();
            int mflow = obmax.pathAugmentation();
            System.out.println("Maxflow is: "+mflow);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
