
/**
 * CS 251: Data Structures and Algorithms
 * Project 3: Part 1
 *
 * TODO: implement scapegoat.
 *
 * @author TODO: add your username here
 * @username TODO: add your Purdue username here
 * @sources TODO: list your sources here
 *
 * Use the algorithms written in sorting to implement this class.
 *
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Scapegoat {

    // Root node
    private Node root;
    // max node count required to implement deletion.
    private int MaxNodeCount = 0;
    // total node number
    private int NodeCount = 0;
    // parameter alpha
    private static final double threshold = 0.57;

    public class Node {
        T data;
        Node parent;
        Node left;
        Node right;
        public Node (T data, Node parent, Node left, Node right){
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
        public String toString(){
            return "[data="+data+"]";
        }
    }




    /**
     *
     * Constructor
     *
     */
    public Scapegoat() {
        root = null;
    }


    /**
     *
     * Constructor
     *
     */
    public Scapegoat(T data) {
        root = new Node(data, null, null, null);
        NodeCount ++;
    }


    /**
     *
     * @return return the root of the scapegoat tree
     *
     */
    public Node root(){
        return this.root;
    }


    /**
     *
     * This function finds the first scapegoat node and returns it.
     *
     * @return void
     *
     */
    private Node scapegoatNode(Node node) {
        int h = 0;  //height

        while (node.parent != null) {
            h++;
            if (size(node) <= (threshold * size(node.parent))) {
                node = node.parent;
            }
            else {
                System.out.println("Scapegoat found:");
                System.out.println(node.parent.toString());
                return node.parent;
            }

        }
        return null;
    }

    // helper function
    public Node buildBalancedTree(ArrayList<Node> list, int i, int size) {
        if (size == 0) {
            return null;
        }

        if (i > size) {
            return null;
        }

        int middle = (i + size) / 2 + (i + size) % 2;
        Node current = list.get(middle);

        current.left = buildBalancedTree(list, i, middle - 1);

        if (current.left != null) {
            current.left.parent = current;
        }

        current.right = buildBalancedTree(list, middle + 1, size);
        if (current.right != null) {
            current.right.parent = current;
        }

        return current;
    }

    /**
     *
     * This function re-builds the tree rooted at node into a perfectly balanced tree.
     * Jayjay: A ScapegoaTree keeps itself balanced by partial rebuilding operations. 
     *         If we let m = a.length/2, then a[m] becomes the root of the new subtree, 
     *         a[0], ... a[m-1] get stored recursibely in the left subtree and a[m+1], ... ,a[a.size()-1] in right subtree
     *
     * @return node
     *
     */
    public Node rebuild(Node node) {
        
        //1. get the elements in sorted order by traversing in inorder --> flatten 
        ArrayList<Node> sorted = new ArrayList<Node>();

        // 1-1. sorted tree ===> is it really a sorted list?
        sorted = (ArrayList<Node>) inorder(node);

        //2. build a perfectly balanced BST
        return buildBalancedTree(sorted, 0, sorted.size() - 1);
    }

    // helper function
    public int getDepth(Node node) {
        if (node == null) {
            return -1;
        }
        else if (node.parent == null){
            return 0;
        }
        else {
            return getDepth(node.parent) + 1;
        }
    }

    // helper function
    

    /**
     *
     * This function adds an element into the tree.
     *
     * @return void
     *
     */
    public void add(T data) {

        Node traverse = root;
        Node parentNode = null;

        Node checkDuplicate = find(data);
        if (checkDuplicate == null) {
            return; // why return? 
        }

        if (root == null) {
            root = new Node(data, null, null, null);
            NodeCount ++;
        } else {
            // 1. find the insertion point. know the depth at which the node is inserted
            insertionPoint(root, data);

            Node added = find(data);
            Node parentAdded = added.parent;
            NodeCount++;
            int depth = getDepth(added);

            //assign parent(root)?
            



            // 2. insert the new node
            /*Node add = new Node(data, parentNode, null, null);
            System.out.printf("before inserting: %d\n", data.a);
            print();
            if (parentNode.data.compareTo(add.data) == -1) {
                parentNode.right = add;
            }
            else {
                parentNode.left = add;
            }
            System.out.printf("after inserting: %d\n", data.a);
            print();*/

            // 3. check if the tree is still alpha weight balanced -> node? root?
            while (add.parent != null) {
                if ((size(add.left) <= (threshold * size(add))) && (size(add.right) <= (threshold * size(add)))) {
                    System.out.printf("%d seems balanced!\n", add.data.a);
                    add = add.parent;
                }
                else {
                    // not balanced -> rebuild =================== problem =====================
                    // 4-1. call function scapegoatNode
                    // 4-2. call rebuild
                    // 4.3 once you get the root of the newly rebuild ...
                    
                    System.out.printf("%d needs to rebuild!\n", add.data.a);

                    Node scapegoat = scapegoatNode(add);    //!
                    Node sgParent = scapegoat.parent;
                    Node rebuild = rebuild(sgParent);   // or rebuild(scpegoat)?
                    rebuild.parent = sgParent;

                    if (sgParent != null) {
                        if (sgParent.left == scapegoat) {
                            sgParent.left = rebuild;
                        }
                        else {
                            sgParent.right = rebuild;
                        }
                    }
                    if (scapegoat == root) {
                        root = rebuild;
                    }
                }
            }
            
        }
    }

    // help function
    public Node insertionPoint(Node r, T data) {
        if(data.compareTo(r.data) < 0) {
            if (r.left == null) {
                r.left = new Node(data, r, null, null);
            }
            else {
                r.left = insertionPoint(r.left, data);
            }
        }
        else if (data.compareTo(r.data) > 0) {
            if (r.right == null) {
                r.right = new Node(data, r, null, null); 
            }
            else {
                r.right = insertionPoint(r.right, data);
            }
        }
        else {
            return null;
        }
        return r;
    }


    /**
     *
     * This function removes an element from the tree.
     *
     * @return void
     *
     */
    public void remove(T data) {
        // TODO
        // -----------------------

        // -----------------------

    }


    // preorder traversal
    public List<Node> preorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(node);
        if(node.left != null){
            nodes.addAll(preorder(node.left));
        }
        if(node.right != null){
            nodes.addAll(preorder(node.right));
        }
        return nodes;
    }


    // inorder traversal
    public List<Node> inorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        if (node.left != null){
            nodes.addAll(inorder(node.left));
        }
        nodes.add(node);
        if(node.right != null){
            nodes.addAll(inorder(node.right));
        }
        return nodes;
    }


    // not used, but you can use this to debug
    public void print() {
        List<Node> nodes = inorder(root);
        for(int i=0;i<nodes.size();i++){
            System.out.println(nodes.get(i).toString());
        }
    }


    // return the node whose data is the same as the given data.
    public Node find(T data) {
        Node current = root;
        int result;
        while(current != null){
            result = data.compareTo(current.data);
            if(result == 0){
                return current;
            }else if(result > 0){
                current = current.right;
            }else{
                current = current.left;
            }
        }
        return null;
    }


    // find the succNode
    public Node succNode(Node node) {
        Node succ = null;
        int result;
        Node current = node;
        while(current != null){
            result = node.data.compareTo(current.data);
            if(result < 0){
                succ = current;
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return succ;
    }

    // used in scapegoatNode function, not a delicated one...
    // Just the brute force calculating the node's children's nubmer. Can be accelerated.
    private int size (Node node) {
        if (node == null)
            return 0;
        return 1 + size(node.left) + size(node.right);
    }


    // BFS(not used, you may use this to help you debug)
    public List<Node> breadthFirstSearch() {
        Node node = root;
        List<Node> nodes = new ArrayList<Node>();
        Deque<Node> deque = new ArrayDeque<Node>();
        if(node != null){
            deque.offer(node);
        }
        while(!deque.isEmpty()){
            Node first = deque.poll();
            nodes.add(first);
            if(first.left != null){
                deque.offer(first.left);
            }
            if(first.right != null){
                deque.offer(first.right);
            }
        }
        return nodes;
    }


    public static void main(String[] args) {
        // write your code here
        Scapegoat tree = new Scapegoat();
        
        tree.add(new T(40));
        tree.add(new T(10));
        //tree.remove(new T(40));

        T data = new T(40);
        Node node = tree.find(data);
        tree.scapegoatNode(node);

        tree.add(new T(8));
        tree.add(new T(12));
        tree.add(new T(7));
        tree.add(new T(9));
        tree.add(new T(11));
        tree.add(new T(14));
        tree.add(new T(16));
        System.out.println("adding 16: "+tree.breadthFirstSearch());
        tree.add(new T(18));
        System.out.println("adding 18: "+tree.breadthFirstSearch());
        System.out.println();

        tree.print();
        


        /* tree.remove(new T(14));
        tree.remove(new T(16));
        System.out.println("removing 14,16: "+tree.breadthFirstSearch());
        tree.remove(new T(12));
        System.out.println("removing 12: "+tree.breadthFirstSearch());
        tree.remove(new T(18));
        System.out.println("removing 18: "+tree.breadthFirstSearch()); */


    }


}

