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
        //TODO: implement insert
        return 0;
    }

    /**
     * Implement the heapifyUp function
     *
     * @param index
     * @return the index where the heapifyUp for the given index ends
     */
    public int heapifyUp(int index) {
        //TODO implement heapifyUp
        return 0;
    }

    /**
     * Implement the heapifyDown function
     *
     * @param index
     * @return the index where the heapifyDown for the given index ends
     */
    public int heapifyDown(int index) {
        //TODO implement heapifyDown
        return 0;
    }

    /**
     * remove the team with the highest priority from the queue
     *
     * @return return the team removed
     */
    public SoccerTeam delMax() {
        //TODO implement delMax
        return null;
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
        //TODO implement getMax
        return null;
    }

    /**
     * @param index
     * @param oldTeamNewValue
     * @return return the new index of the team oldTeamNewValue updated in the heap
     */
    public int update(int index, SoccerTeam oldTeamNewValue) {
        //TODO implement update
        return 0;
    }

}