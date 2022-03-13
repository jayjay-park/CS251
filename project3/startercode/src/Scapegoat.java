
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

        while (node.parent != null) {
            node = node.parent;
            if (size(node) > (threshold * size(node.parent))) {
                break;
            }
        }
        return node.parent;
    }

    // helper function
    public Node buildBalancedTree(List<Node> list) {
        if (list.isEmpty()) {
            return null;
        }

        int middle = list.size() / 2;
        Node current = list.get(middle);

        List<Node> left = new ArrayList<Node>();
        for (int i = 0; i < middle; i++) {
            left.add(list.get(i));
        }

        List<Node> right = new ArrayList<Node>();
        for (int i = middle + 1; i < list.size(); i++) {
            right.add(list.get(i));
        }

        current.left = buildBalancedTree(left);
        if (current.left != null) {
            current.left.parent = current;
        }
        current.right = buildBalancedTree(right);
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
        
        Node p = node.parent;
        Node rebuilt = buildBalancedTree(inorder(node));
        rebuilt.parent = p;
        
        // -----inserted
        MaxNodeCount = NodeCount;

        return rebuilt;

        //1. get the elements in sorted order by traversing in inorder --> flatten 
        // 1-1. sorted tree ===> is it really a sorted list?
        //2. build a perfectly balanced BST
    }

    

    /**
     *
     * This function adds an element into the tree.
     *
     * @return void
     *
     */
    public void add(T data) {

        Node checkDuplicate = find(data);

        if (checkDuplicate != null) {
            return;
        }

        if (root == null) {
            root = new Node(data, null, null, null);
            NodeCount ++;

        } else {
            // 1. find the insertion point. know the depth at which the node is inserted
            insertionPoint(root, data);

            Node added = find(data);
            if (added == null) {
                System.out.println("node was not added");
                return;
            }

            NodeCount++;
            Node p = added.parent;

            int h = 0;
            while (p != null) {
                //if (h > 20) {
                //    break;
                //}
                h++;
                p = p.parent;
            }

            
            // 3. check if the tree is still alpha weight balanced -> node? root?
                if (h > Math.floor(Math.log(NodeCount) / Math.log(1 / threshold))) {
                    // not balanced -> rebuild
                    // 4-1. call function scapegoatNode
                    // 4-2. call rebuild
                    // 4.3 once you get the root of the newly rebuild ...
                    
                    //System.out.printf("%d needs to rebuild!\n", added.data.a);
                    print();
                    
                    Node sg = scapegoatNode(added);
                    if (sg == root) {
                        root = rebuild(sg);
                    }
                    else if (sg.parent.left != null && sg.parent.left.equals(sg)) {
                        sg.parent.left = rebuild(sg); 
                    }
                    else if (sg.parent.right != null && sg.parent.right.equals(sg)) {
                        sg.parent.right = rebuild(sg);

                    }
                }

                MaxNodeCount = Math.max(MaxNodeCount, NodeCount);
                //System.out.printf("%d seems balanced!\n", added.data.a);
                //print();
        }
    }

    // help function (add)
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


    // helper function
    public Node removeNode(Node remove, T data) {

        if (remove == null) {
            return remove;
        }
        // --- traverse --- //
        if (data.compareTo(remove.data) < 0) {
            remove.left = removeNode(remove.left, data);
        }
        else if (data.compareTo(remove.data) > 0) {
            remove.right = removeNode(remove.right, data);
        }
        else {
            if (remove.left == null && remove.right == null) {
                Node parent = remove.parent;
                if (parent.left == remove) {
                    parent.left = null;
                }
                else {
                    parent.right = null;
                }
                remove = null;
            }
            else if (remove.left != null && remove.right != null) {
                Node suc = succNode(remove);
                remove.data = suc.data;
                remove.right = removeNode(remove.right, suc.data);
            }    
            else {
                // single child
                if (remove.left != null) {
                    Node left = remove.left;
                    left.parent = remove.parent;
                    remove = left;
                }
                else {
                    Node right = remove.right;
                    right.parent = remove.parent;
                    remove = right;
                }
            }

            
            //past code
            // deleting root
            /* if (remove.parent == null) {
                // leaf node
                if (remove.left == null && remove.right == null) {
                    remove = null;
                    root = null;
                }
                else if (remove.left != null && remove.left.parent == remove) {
                    root = remove.left;
                    root.parent = null;
                }
                else if (remove.right != null && remove.right.parent == remove) {
                    root = remove.right;
                    root.parent = null;
                }
                else {
                    Node successor = succNode(remove);
                    remove.data = successor.data;
                    remove.right = removeNode(remove.right, successor.data);
                }
            }
            // if it is a leaf node in the middle
            else if (remove.left == null && remove.right == null) {
                Node p = remove.parent;
                if (p.left != null && p.left.equals(remove)) {
                    p.left = null;
                }
                else if (p.right != null && p.right.equals(remove)) {
                    p.right = null;
                }
                remove.parent = null;
                remove = null;
            }
            // if there's two child
            else if (remove.left != null && remove.right != null) {
                Node successor = succNode(remove);
                remove.data = successor.data;
                remove.right = removeNode(remove.right, successor.data);
            }
            // if there is one child
            else {
                Node c = null; 
                if (remove.left != null) {
                    c = remove.left;
                }
                else if (remove.right != null) {
                    c = remove.right;
                }
                Node p = remove.parent;
                if (p.left != null && p.left.equals(remove)) {
                    p.left = c;
                }
                else if (p.right != null && p.right.equals(remove)) {
                    p.right = c;
                }
                c.parent= p;
                remove = c;

            }*/

        }
        return remove;
    }

    /**
     *
     * This function removes an element from the tree.
     *
     * @return void
     *
     */
    public void remove(T data) {
        // make sure it's not null --> avoid null pointer
        if (find(data) == null) {
            return;
        }
        else {
            root = removeNode(this.root, data);
            NodeCount--;
            if (NodeCount <= threshold * MaxNodeCount) {
                root = rebuild(root);
                MaxNodeCount = NodeCount;

            }
        }

        // 1. standard deletion operation for binary search trees would be run
        /*removeNode(root, data);
        // 2. n would be decremented by a factor of 1
        NodeCount--;
        // 3. n would be compared with q/2 (upper-bound for the number of items a tree holds)
        // 4. if n < q/2 the entire tree would be re-built and q would be set equal to n
        if (NodeCount <= threshold * MaxNodeCount) {
            MaxNodeCount = NodeCount;
            rebuild(root);
        }*/

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
        tree.remove(new T(40));
        tree.print();
        System.out.println();

        tree.add(new T(8));
        tree.add(new T(12));
        tree.add(new T(7));
        tree.add(new T(9));
        tree.add(new T(11));
        tree.add(new T(14));
        tree.add(new T(16));
        //System.out.println("adding 16: "+tree.breadthFirstSearch());
        tree.add(new T(18));
        tree.print();
        System.out.println();
        //System.out.println("adding 18: "+tree.breadthFirstSearch());

        tree.remove(new T(14));
        tree.print();
        System.out.println();
        tree.remove(new T(16));
        tree.print();
        System.out.println();
        tree.remove(new T(12));
        tree.print();
        System.out.println();
        tree.remove(new T(18));
        //tree.print();
        //System.out.println();
        //System.out.println("removing 18: "+tree.breadthFirstSearch());*/



    }


}

