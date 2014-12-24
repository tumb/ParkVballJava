package com.delawareparkvolleyball.schedule;

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

	// Something to save
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
