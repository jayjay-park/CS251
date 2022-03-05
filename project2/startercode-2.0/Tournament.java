/**
 * Part 4
 */


public class Tournament {

    private int teamNo;
    private String[] teamNames;
    private SoccerTeamPriorityQueue teamPQ;

    /**
     * Initialize the fields
     * @param teamNames
     */
    public Tournament(String[] teamNames) {

        teamNo = teamNames.length; 
        this.teamNames = teamNames;
        this.teamPQ = new SoccerTeamPriorityQueue(this.teamNo);

        for (int i = 0; i < teamNames.length; i++) {
            SoccerTeam newTeam1 = new SoccerTeam(teamNames[i]);
            int index = teamPQ.insert(new SoccerTeam(newTeam1));
            newTeam1.setPosInQueue(index);
        }
    }

    /**
     * @return an array of team names
     */
    public String[] getTeamNames() {
        return this.teamNames;
    }

    /**
     * Find the kth team (1-index) from the teams
     * @param k
     */
    public SoccerTeam findKthTeam(int k) {
        //create empty priority queue
        SoccerTeamPriorityQueue p = new SoccerTeamPriorityQueue(teamNo);
        SoccerTeamPriorityQueue r = new SoccerTeamPriorityQueue(teamPQ);

        p.insert(r.getMax());

        for (int i = 1; i < k; i++) {

            SoccerTeam t = p.getMax();

            //check if childs exists
            if (r.getTeamTable().get(t.getName()).getPosInQueue() * 2 + 1 < r.getNumTeams()) {
                int tLeft = r.getLeftChild(r.getTeamTable().get(t.getName()).getPosInQueue());
                SoccerTeam team = r.getTeamList()[tLeft];
                p.insert(team);    
            }
            if (r.getTeamTable().get(t.getName()).getPosInQueue() * 2 + 2 < r.getNumTeams()) {
                int tRight = r.getRightChild(r.getTeamTable().get(t.getName()).getPosInQueue());
                SoccerTeam team = r.getTeamList()[tRight];
                p.insert(team);
            }
            p.delMax();
        }

        return r.getTeamTable().get(p.getMax().getName());
    }

    /**
     * Update the priority queue and hash table with this information from a match.
     * @param teamName1
     * @param goal1
     * @param teamName2
     * @param goal2
     */
    public void updateTournament(String teamName1, int goal1, String teamName2, int goal2) {

        // retrieve their old info
        SoccerTeam team1 = teamPQ.getTeamTable().get(teamName1);

        team1.setGoalsScored(team1.getGoalsScored() + goal1);
        team1.setGoalsConceded(team1.getGoalsConceded() + goal2);
        team1.setMatchesPlayed(team1.getMatchesPlayed() + 1);

        SoccerTeam team2 = teamPQ.getTeamTable().get(teamName2);

        team2.setGoalsScored(team2.getGoalsScored() + goal2);
        team2.setGoalsConceded(team2.getGoalsConceded() + goal1);
        team2.setMatchesPlayed(team2.getMatchesPlayed() + 1);

         // add 1 to matchesPlayed
        if (goal1 > goal2) {
            team1.setPoints(team1.getPoints() + 3);
        }
        else if (goal2 > goal1) {
            team2.setPoints(team2.getPoints() + 3);
        }
        else {
            team1.setPoints(team1.getPoints() + 1);
            team2.setPoints(team2.getPoints() + 1);
        }
        

        // 3. update the priority queue calling update(index, S1 or S2)
        teamPQ.update(this.teamPQ.getTeamTable().get(teamName1).getPosInQueue(), team1);
        teamPQ.update(this.teamPQ.getTeamTable().get(teamName2).getPosInQueue(), team2);

    }

    /**
     * @return string representation of the priority queue
     */
    public String toString() {
        return "Priority Queue and hash table:\n" + this.teamPQ.toString();
    }

}