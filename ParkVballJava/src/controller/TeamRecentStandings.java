package controller;

public class TeamRecentStandings implements Comparable<TeamRecentStandings> {
	private Team team ; 
	private int wins ; 
	private int losses ;
	
	public TeamRecentStandings(Team team, int wins, int losses) {
		this.team = team ; 
		this.wins = wins ;
		this.losses = losses ;
	}
	
	public TeamRecentStandings(Team team) {
		this.team = team ; 
		this.wins = 0 ;
		this.losses = 0 ;
	}
	
	public Team getTeam() {
		return team;
	}
	public int getWins() {
		return wins;
	}
	public int getLosses() {
		return losses;
	}

	@Override
	public int compareTo(TeamRecentStandings other) {
		int higher = 0 ; // -1 is higher, 1 is lower in sorting
		boolean hasMoreWins = this.wins > other.wins ; 
		boolean hasMoreLosses = this.losses > other.losses ;
		int thisDiff = this.wins - this.losses ;
		int otherDiff = other.wins - other.losses ;
		if(thisDiff > otherDiff) {
			higher = -1 ; 
		}
		else if (thisDiff < otherDiff) {
			higher = 1 ; 
		}
		else if(thisDiff > 0) {
			if(hasMoreLosses) {
				higher = 1 ; 
			}
			else if (this.losses < other.losses) {
				higher = -1 ;
			}
		}
		else if(thisDiff < 0) {
			if(hasMoreWins) {
				higher = -1 ; 
			}
			else if (this.wins < other.wins) {
				higher = 1 ;
			}
		}
		else {
			higher = 0 ; 
		}
		return higher;
	}

	public void addMatchResult(int newWins, int newLosses) {
		this.wins += newWins ; 
		this.losses += newLosses ; 
	} 

	
	
}
