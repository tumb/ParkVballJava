package com.delawareparkvolleyball.schedule;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

public class Team {
	private Person man ; 
	private Person woman ; 
	private int year ; 
	private DayOfTheWeek night ; 
	private Division division ; 
	
	public Team(Person man, Person woman, int year, DayOfTheWeek night) {
		this.man = man ; 
		this.woman = woman ; 
		this.year = year ; 
		this.night = night ; 
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
	
	
}
