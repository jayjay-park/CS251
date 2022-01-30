/**
 * CS 251: Data Structures and Algorithms
 * Project 1
 * <p>
 * TODO: Complete MyStack.
 *
 * @author , TODO: add your name here
 * @username , TODO: add your Purdue username here
 * @sources TODO: list your sources here
 */

public class MyStack<Item> {


    /**
     * Pointer to top of the stack
     */
    private StackNode<Item> top;
    /**
     * Keeps track of the size of the stack
     */
    private int size;

    /**
     * Default constructor of the class initializes all
     * parameters to default values
     */
    public MyStack() {
        //TODO implement MyStack
    }

    /**
     * @return if the stack is empty or not
     */
    public boolean isEmpty() {
        //TODO implement isEmpty
        return false;
    }

    /**
     * Pushes a new a new value into the stack
     * Remember to update size
     *
     * @param value the value to be pushed
     */
    public void push(Item value) {
        //TODO implement push
    }

    /**
     * Peeks the top element of the stack
     *
     * @return the value at the top of the stack
     * @throws EmptyStackException if the stack is empty.
     *                             You can throw an exception by “throw new EmptyStackException();”
     */
    public Item peek() throws EmptyStackException {
        //TODO implement peek
        return null;
    }

    /**
     * Pops the top element of the stack
     * Remember to update size
     *
     * @return the popped element
     * @throws EmptyStackException if the stack is empty
     *                             You can throw an exception by “throw new EmptyStackException();”
     */
    public Item pop() throws EmptyStackException {
        //TODO implement pop
        return null;
    }

    /**
     * @return the size of the stack
     */
    public int getSize() {
        //TODO implement getSize
        return Integer.MIN_VALUE;
    }

}
