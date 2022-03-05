/**
 * Part 3
 */
public class SoccerTeamPriorityQueue {

    private SoccerTeam[] teamList;
    private int numTeams;
    private SoccerTeamHashTable teamTable;

    /**
     * @return return the priority queue
     */
    public SoccerTeam[] getTeamList() {
        return teamList;
    }

    /**
     * @return the team table
     */
    public SoccerTeamHashTable getTeamTable() {
        return teamTable;
    }

    /**
     * Constructor of the class.
     * Initialize a new SoccerTeam array with the argument passed.
     */
    public SoccerTeamPriorityQueue(int capacity) {
        this.teamList = new SoccerTeam[capacity];
        this.numTeams = 0;
        this.teamTable = new SoccerTeamHashTable(capacity);

    }

    /**
     * Constructor to initialize the priority queue and global variables
     *
     * @param spq
     */
    public SoccerTeamPriorityQueue(SoccerTeamPriorityQueue spq) {
        SoccerTeam[] teams = spq.getTeamList();
        this.teamList = new SoccerTeam[teams.length];
        this.numTeams = teams.length;
        this.teamTable = new SoccerTeamHashTable(this.numTeams);

        for (int i = 0; i < this.numTeams; i++) {
            this.teamList[i] = new SoccerTeam(teams[i]);
            this.teamTable.put(new SoccerTeam(teams[i]));
        }

    }

    /**
     * @return return the number of teams
     */
    public int getNumTeams() {
        return this.numTeams;
    }

    /**
     * @return String representation of the priority queue
     */
    public String toString() {
        String str = "Priority Queue:\n";

        for (SoccerTeam team : this.teamList) {
            if (team != null) {
                str = str + team.toString() + "\n";
            }
        }

        str = str.substring(0, str.length());

        str += this.teamTable.toString();

        return str;
    }


    public int getParent(int index) {
        return (int) (index - 1) / 2;
    }

    public int getLeftChild(int index) {
        return index * 2 + 1;
    }

    public int getRightChild(int index) {
        return index * 2 + 2;
    }

    /**
     * @return return the index at which the team was inserted
     */

    public int insert(SoccerTeam c) {

        teamList[numTeams] = c;
        teamTable.put(c);
        int finalIndex = heapifyUp(numTeams);
        numTeams++;
        c.setPosInQueue(finalIndex);
        return finalIndex;

    }

    public void swap (int child, int parent) {
        SoccerTeam temp = teamList[parent];
        teamList[parent] = teamList[child];
        teamList[child] = temp;
        teamList[parent].setPosInQueue(parent);
        teamList[child].setPosInQueue(child);
    }

    /**
     * Implement the heapifyUp function
     *
     * @param index
     * @return the index where the heapifyUp for the given index ends
     */
    public int heapifyUp(int index) {

        int above = this.getParent(index);
        if (index != 0 && teamList[above].compareTo(teamList[index]) == -1) {
            swap(index, above);
            return heapifyUp(above);
        }
        return index;
    }

    /**
     * Implement the heapifyDown function
     *
     * @param index
     * @return the index where the heapifyDown for the given index ends
     */
    public int heapifyDown(int index) {
        //TODO implement heapifyDown
        int i = index;
        int leftChild = getLeftChild(index);
        int rightChild = getRightChild(index);

        if (leftChild >= numTeams || teamList[leftChild] == null) {
            return index;
        }
        if (rightChild >= numTeams || teamList[rightChild] == null) {
            if (teamList[leftChild].compareTo(teamList[i]) > 0) {
                swap(leftChild, index);
                return leftChild;
            }
            else {
                return index;
            }
        }

        if (teamList[leftChild].compareTo(teamList[i]) > 0) {
            i = leftChild;
        }
        if (teamList[rightChild].compareTo(teamList[i]) > 0) {
            i = rightChild;
        }
        if (i != index) {
            swap(i, index);
            return heapifyDown(i);
        }
        return index;
    }

    /**
     * remove the team with the highest priority from the queue
     *
     * @return return the team removed
     */
    public SoccerTeam delMax() {
        SoccerTeam root = teamList[0];
        String name = root.getName();
        teamTable.remove(name);
        teamList[0] = teamList[numTeams - 1];
        teamList[numTeams - 1] = null;
        heapifyDown(0);
        numTeams--;
        
        return root;
    }

    /**
     * @return return the number of teams currently in the queue
     */
    public int size() {
        return this.numTeams;
    }

    /**
     * @return return true if the queue is empty; false else
     */
    public boolean isEmpty() {
        return (this.numTeams == 0);
    }

    /**
     * @return return the team with the maximum priority
     */
    public SoccerTeam getMax() {
        
        return teamList[0];
    }

    /**
     * @param index
     * @param oldTeamNewValue
     * @return return the new index of the team oldTeamNewValue updated in the heap
     */
    public int update(int index, SoccerTeam oldTeamNewValue) {
        teamTable.put(oldTeamNewValue);
        teamList[index] = oldTeamNewValue;
        if (index == 0 ) {
            // heapifyDown
            index = heapifyDown(index);
        }
        else if (index == numTeams) {
            // heapifyUp
            index = heapifyUp(index);
        }
        else {
            int parent = getParent(index);
            if (teamList[parent].compareTo(teamList[index]) < 0) {
                index = heapifyUp(index);
            }
            else {
                index = heapifyDown(index);
            }
        }
        return index;
    }

}