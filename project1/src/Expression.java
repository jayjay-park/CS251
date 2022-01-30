/**
 * CS 251: Data Structures and Algorithms
 * Project 1
 * <p>
 * TODO: Complete Expression.
 *
 * @author , TODO: add your name here
 * @username , TODO: add your Purdue username here
 * @sources TODO: list your sources here
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
        //TODO: complete isValid
        return false;
    }

    /**
     * Makes an expression tree of the expression
     *
     * @return the root of the expression tree
     */
    public TreeNode makeTree() {
        //TODO complete makeTree
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
        //TODO complete evaluate
        return Integer.MIN_VALUE;
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
