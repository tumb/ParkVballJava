package com.delawareparkvolleyball.schedule;


public class Team {
	private Person man ; 
	private Person woman ; 
	private int year ; 
	private DayOfTheWeek night ; 
	private Division division ; 
	private String teamName ; // The name to appear on schedule. Usually woman's first name.
	private int teamId ; // the id in the database. User should never see.
	private int leagueId ; 
	private int manId ; 
	private int womanId ; 
	
	public int getLeagueId() {
		return leagueId;
	}

	public int getManId() {
		return manId;
	}

	public int getWomanId() {
		return womanId;
	}

	public Team(Person man, Person woman, int year, DayOfTheWeek night) {
		this.man = man ; 
		this.woman = woman ; 
		this.year = year ; 
		this.night = night ; 
	}

	public Team(Person man, Person woman, String teamName, int year, DayOfTheWeek night) {
		this.man = man ; 
		this.woman = woman ; 
		this.year = year ; 
		this.night = night ; 
		this.teamName = teamName ; 
	}

	/**
	 * Incomplete person but all we need for standings.
	 * @param womanFirstName
	 * @param womanLastName
	 * @param manFirstName
	 * @param manLastName
	 */
	public Team(String womanFirstName, String womanLastName, String manFirstName, String manLastName) {
		this.man   = new Person(manFirstName, manLastName, "M") ; 
		this.woman = new Person(womanFirstName, womanLastName, "F") ; 
	}
	
	public Team(String teamName) {
		this.teamName = teamName ; 
	}
	
	public Team(int teamId, int leagueId, int manId, int womanId, String teamName) {
		this.teamName = teamName ; 
		this.teamId = teamId ; 
		this.leagueId = leagueId ; 
		this.manId = manId ; 
		this.womanId = womanId ; 
	}

	public Person getMan() {
		return man;
	}

	public Person getWoman() {
		return woman;
	}

	public int getYear() {
		return year;
	}

	public DayOfTheWeek getNight() {
		return night;
	}

	public Division getDivision() {
		return division;
	}
	
	public String getTeamName() {
		return teamName ; 
	}

	public int getTeamId() {
		return this.teamId ; 
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((man == null) ? 0 : man.hashCode());
		result = prime * result + ((night == null) ? 0 : night.hashCode());
		result = prime * result + ((woman == null) ? 0 : woman.hashCode());
		result = prime * result + year;
		return result;
	}

	public boolean playerMatch(Team otherTeam) {
		boolean isMatch = otherTeam != null ; 
		isMatch = isMatch && otherTeam.man.equals(this.man) ;
		isMatch = isMatch && otherTeam.woman.equals(this.woman) ; 
		return isMatch ; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (man == null) {
			if (other.man != null)
				return false;
		} else if (!man.equals(other.man))
			return false;
		if (night == null) {
			if (other.night != null)
				return false;
		} else if (!night.equals(other.night))
			return false;
		if (woman == null) {
			if (other.woman != null)
				return false;
		} else if (!woman.equals(other.woman))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	// Something to save more
	private static int teamsCreated ; 
	public static Team CreateExampleTeam(int year, DayOfTheWeek night) {
		Person man = Person.createExampleMan(teamsCreated) ; 
		Person woman = Person.createExampleWoman(teamsCreated) ;
		Team newTeam = new Team(man, woman, year, night) ; 
		teamsCreated++ ;
		teamsCreated = teamsCreated % 8 ; 
		return newTeam ; 
	}

	@Override
	public String toString() {
		String string = this.teamName + ", " ;
		string += "teamId: " + this.teamId + ", " ; 
		string += "manId: " + this.manId + ", "; 
		string += "leagueId: " + this.leagueId + ", ";
		string += "man: " + this.man + ", "; 
		string += "woman: " + this.woman + ", ";
		string += "manId: " + this.manId + ", "; 
		string += "womanId: " + this.womanId ; 
		return string ; 
	}

	public void setId(int teamId) {
		this.teamId = teamId ; 
	}
	
}
