package controller;

public class Bracket {
	private League league ; 
	private String name ;
	private int id ; 
	
	public Bracket(String divisionName, League league) {
		this.name = divisionName ; 
		this.league = league ; 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
	
	public String toString() {
		return "Name " + name + ", league " + league + ", id " + id ; 
	}
	
}
