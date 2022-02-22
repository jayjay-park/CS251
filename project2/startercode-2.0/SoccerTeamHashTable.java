/**
 * Part 2
 */

import java.util.ArrayList;

public class SoccerTeamHashTable {

    private ArrayList<SoccerTeam>[] table;
    private int numTeams;
    private int tableCapacity;

    /**
     * Initialize a new SoccerTeam array with the argument passed.
     */
    public SoccerTeamHashTable(int capacity) {
        this.tableCapacity = this.getNextPrime(capacity);
        this.table = new ArrayList[this.tableCapacity];
        this.numTeams = 0;
    }

    // manual for testing
    public static void main(String[] args) {

    }

    /**
     * Write the code for the evaluating the expression
     *
     * @param expression
     * @return
     */
    public int evaluateExpression(String expression) {
        //TODO implement evaluateExpression
        return 0;
    }

    /**
     * for a given name, it will return an expression to calculate the hash value.
     *
     * @param name
     * @return expression to calculate the hash value
     */
    public String getExpression(String name) {
        
        for (char ch : name.toCharArray()) {
            String s = ch + "%" + "M";
        }
        return "";
    }

    /**
     * calculate the hashvalue.
     *
     * @param name
     * @return
     */
    public int getHash(String name) {
        //TODO implement getHash
        return 0;
    }

    /**
     * find if a SoccerTeam object with the name is in the hash table
     *
     * @param name
     * @return
     */
    public SoccerTeam get(String name) {
        //TODO implement get
        return null;
    }

    /**
     * Insert the SoccerTeam object in the hash table.
     *
     * @param c
     */
    public void put(SoccerTeam c) {
        //TODO implement put
        return;
    }

    /**
     * remove the soccer team else return null
     *
     * @param name
     * @return
     */
    public SoccerTeam remove(String name) {
        //TODO implement remove
        return null;
    }

    // return the number of teams
    public int size() {
        return this.numTeams;
    }

    // get the table capacity
    public int getTableCapacity() {
        return this.tableCapacity;
    }

    // get the next prime number p >= num
    private int getNextPrime(int num) {
        if (num == 2 || num == 3) {
            return num;
        }

        int rem = num % 6;

        switch (rem) {
            case 0:
            case 4:
                num++;
                break;
            case 2:
                num += 3;
                break;
            case 3:
                num += 2;
                break;
        }

        while (!isPrime(num)) {
            if (num % 6 == 5) {
                num += 2;
            } else {
                num += 4;
            }
        }

        return num;
    }

    // determines if a number > 3 is prime
    private boolean isPrime(int num) {
        if (num % 2 == 0) {
            return false;
        }

        int x = 3;

        for (int i = x; i < num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    // return the table
    public ArrayList<SoccerTeam>[] getArray() {
        return this.table;
    }

    // returns the string representation of the object
    public String toString() {
        String ret = "";
        for (int i = 0; i < this.table.length; i++) {
            ret += Integer.valueOf(i);

            // System.out.println(this.table[i]);

            if (this.table[i] != null) {
                for (SoccerTeam team : this.table[i]) {
                    ret += " ";
                    ret += team.toString();
                    ret += " ";
                }

                ret = ret.trim();
                // ret = ret.substring(0, ret.length() - 1);

            }

            ret += "\n";
        }
        return ret.substring(0, ret.length() - 1);
    }
}