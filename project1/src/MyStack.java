/**
 * CS 251: Data Structures and Algorithms
 * Project 1
 * <p>
 * TODO: Complete MyStack.
 *
 * @author , Jayjay Park
 * @username , park1125
 * @sources project1 handout
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
        this.top = null;
        this.size = 0;
    }

    /**
     * @return if the stack is empty or not
     */
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Pushes a new a new value into the stack
     * Remember to update size
     *
     * @param value the value to be pushed
     */
    public void push(Item value) {
        StackNode<Item> push = new StackNode<Item>(value, top);
        this.top = push;
        this.size += 1;
    }

    /**
     * Peeks the top element of the stack
     *
     * @return the value at the top of the stack
     * @throws EmptyStackException if the stack is empty.
     *                             You can throw an exception by “throw new EmptyStackException();”
     */
    public Item peek() throws EmptyStackException {
        if (this.size == 0) {
            throw new EmptyStackException();
        }

        return this.top.value;
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
        if (this.size == 0) {
            throw new EmptyStackException();
        }

        // save old top
        StackNode<Item> popNode = this.top;
        // update new top
        this.top = this.top.next;
        // update size
        this.size -= 1;

        return popNode.value;
    }

    /**
     * @return the size of the stack
     */
    public int getSize() {
        
        return this.size;
    }

}
