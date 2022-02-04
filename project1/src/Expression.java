import javax.security.auth.kerberos.KerberosCredMessage;

/**
 * CS 251: Data Structures and Algorithms
 * Project 1
 * <p>
 * TODO: Complete Expression.
 *
 * @author , Jayjay Park
 * @username , park1125
 * @sources project1 handout
 */


public class Expression {

    /**
     * The expression to validate and evaluate
     */
    private final String expression;
    /**
     * Used for testing please do not modify
     */
    StringBuilder treeString;

    /**
     * Constructor to initialize the expression
     *
     * @param expression - the expression to evaluate
     */
    public Expression(String expression) {
        this.expression = expression;
        treeString = null;
    }

    /**
     * Checks whether the expression is valid or not
     *
     * @return true if the expression is valid
     */
    public boolean isValid() {
        //check the input
        System.out.println(this.expression);

        char[] input =  this.expression.toCharArray();
        int numOperators = 0;
        int numBrackets = 0;
        int numOperands = 0;

        for (int i = 0; i < input.length; i++) {
            // if operator
            if (input[i] == '+' || input[i] == '-' || input[i] == '*' || input[i] == '/' || input[i] == '%') {
                // expression should not start with operator
                if (i == 0) {
                    return false;
                }

                if ((i + 1) < input.length) {
                    if (input[i+1] == '+'|| input[i+1] == '-' || input[i+1] == '*' || input[i+1] == '/' || input[i+1] == '%') {
                        return false;
                    }
                }
                numOperators++;
            }
            // if brackets
            else if (input[i] == '(' || input[i] == ')') {
                // edge case: ()
                if ((i + 1) < input.length) {
                    if (input[i] == '(' && input[i+1] == ')') {
                        return false;
                    }
                }
                numBrackets++;
            }
            // if operand
            else if (input[i] >= 'a' && input[i] <= 'z') {
                // edge case: aa
                if ((i + 1) < input.length) {
                    if (input[i+1] >= 'a' && input[i+1] <= 'z') {
                        return false;
                    }
                }
                numOperands++;
            }
        }

        if ((numBrackets % 2) != 0) {
            return false;
        }
        if (numOperands != (numOperators + 1)) {
            return false;
        }

        return true;
    }

    /**
     * Makes an expression tree of the expression
     *
     * @return the root of the expression tree
     */
    public TreeNode makeTree() {

        MyStack<TreeNode> operator = new MyStack<TreeNode>();
        MyStack<TreeNode> operand = new MyStack<TreeNode>();

        char[] input =  this.expression.toCharArray();
        TreeNode root = new TreeNode();
        TreeNode leftNode = new TreeNode();
        TreeNode rightNode = new TreeNode();

        System.out.printf("----- New Expression: %s -----\n", this.expression);

        for (int i = 0; i < input.length; i++) {
            // 1. VARIABLE to Operand Stack
            if (input[i] >= 'a' && input[i] <= 'z') {
                operand.push(new TreeNode(input[i]));

                char currentTop = '\0';
                try{
                    currentTop = operand.peek().value;
                } catch (Exception e) {
                    System.out.println("Error1\n");
                }
                System.out.printf("VARIABLE top: %c\n", currentTop);
            }
            // 2. ( or OPERATOR to Operator Stack
            else if (input[i] == '(' || input[i] == '+' || input[i] == '-' ||
                     input[i] == '*' || input[i] == '/' || input[i] == '%') {
                operator.push(new TreeNode(input[i]));

                char currentTop = '\0';
                try{
                    currentTop = operator.peek().value;
                } catch (Exception e) {
                    System.out.println("Error2\n");
                }
                System.out.printf("OPERATOR top: %c\n", currentTop);
            }
            // 3. )
            if (input[i] == ')') {
                try {
                    // 3-2-1. pop operator
                    root = operator.pop();
                    System.out.printf("Operator popped: %c\n", root.value);
                } catch (Exception e) {
                    System.out.printf("Error3.1\n");
                }
                // 3-2. If top TreeNode of operator is not '('
                while (root.value != '(') {
                    try {
                            // 3-2-2. pop two operands
                            rightNode = operand.pop();
                            root.right = rightNode;
                            leftNode = operand.pop();
                            root.left = leftNode;
                            System.out.printf("Operand right: %c\n", root.right.value);
                            System.out.printf("Operand left: %c\n", root.left.value);
                            operand.push(root);
                            root = operator.pop();

                    } catch (Exception e) {
                        System.out.println("Error5!\n");
                    }
                }
            } 
        }

        int count = operator.getSize();

        try {
            root = operator.pop();
        } catch (Exception e) {
            System.out.println("Error6\n");
        }

            for (int k = 0; k < count; k++) {
                try {
                    rightNode = operand.pop();
                    root.right = rightNode;
                    leftNode = operand.pop();
                    root.left = leftNode;
                    System.out.printf("Operand right1: %c\n", root.right.value);
                    System.out.printf("Operand left1: %c\n", root.left.value);
                    operand.push(root);
                    root = operator.pop();
                } catch (Exception e) {
                    System.out.println("Error7\n");
                }
            }
        


        System.out.printf("previous root : %c\n", root.value);
        try {
            return operand.pop();
        } catch (Exception e) {
            System.out.println("Error8\n");
        }
        return null;
    }

    /**
     * Evaluate the expression tree
     *
     * @param root   of the expression tree
     * @param values of all the variables the values is an int array of size 26.
     *               values[0] represent the value of ‘a’ and values[1] represent the value of ‘b’ and so on
     * @return the value of the evaluated expression
     */
    public int evaluate(TreeNode root, int[] values) {
        // base case
        if (root == null) {
            return 0;
        }
        
        if (root.left == null && root.right == null) {
            // System.out.printf("index of %c: %d\n", root.value, 0 + (root.value - 'a'));
            System.out.printf("leaf: %c\n", root.value);
            return values[0 + (root.value - 'a')];
        }

        
        int leftChild = evaluate(root.left, values);
        
        
        int rightChild = evaluate(root.right, values);
        
        //function: current node must be operator cause that is all that is left after
        
        switch (root.value) {
            case '+': return leftChild + rightChild;
            case '-': return leftChild - rightChild;
            case '*': return leftChild * rightChild;
            case '/': return leftChild/ rightChild;
            case '%': return leftChild % rightChild;
        }
        
        return -1;
    }

    /**
     * DO NOT MODIFY
     * Used to print the tree and for testing
     * source = https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram-in-java/42449385#42449385
     *
     * @param root
     * @return
     */
    public String print(TreeNode root) {
        treeString = new StringBuilder();
        print("", root, false);
        return treeString.toString();
    }

    /**
     * DO NOT MODIFY
     * Used to print the tree and for testing
     * source = https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram-in-java/42449385#42449385
     *
     * @param prefix
     * @param n
     * @param isLeft
     */
    public void print(String prefix, TreeNode n, boolean isLeft) {
        if (n != null) {
            treeString.append(prefix + (isLeft ? "|-- " : "\\-- ") + n.value + "\n");
            print(prefix + (isLeft ? "|   " : "    "), n.left, true);
            print(prefix + (isLeft ? "|   " : "    "), n.right, false);
        }
    }


    /**
     * Main Can be used for manual testing
     *
     * @param args
     */
//    public static void main(String[] args) {
//        Expression expression = new Expression("(a + (a + b * z) + (x % u) + (p * x))");
//        System.out.println(expression.isValid());
//        TreeNode root = expression.makeTree();
//        System.out.println(expression.print(root));
//        int[] chars = new int[26];
//        for (int i = 0; i < 26; i++) {
//            chars[i] = i + i;
//            System.out.println((char)('a' + i));
//            System.out.println(chars[i]);
//        }
//        System.out.println(expression.evaluate(root, chars));
//
//    }

}
