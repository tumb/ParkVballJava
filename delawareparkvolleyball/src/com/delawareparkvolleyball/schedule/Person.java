package com.delawareparkvolleyball.schedule;

public class Person {
	private String firstName ; 
	private String lastName ; 
	private String[] aliases ; 
	
	private static Person[] MEN ;
	private static Person[] WOMEN ; 
	
	public Person(String firstName, String lastName) {
		this.firstName = firstName ; 
		this.lastName = lastName ; 
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName ; 
	}

	public static Person createExampleMan(int teamsCreated) {
		return GetMan(teamsCreated) ;
	}
	
	public static Person createExampleWoman(int teamsCreated) {
		return GetWoman(teamsCreated) ;
	}
	
	private static Person GetMan(int index) {
		if(MEN == null || MEN.length <= index ) {
			MEN = new Person[8] ; 
			MEN[0] = new Person("Thom", "Burnett") ;
			MEN[1] = new Person("Kai", "Burnett") ;
			MEN[2] = new Person("Larry", "Piegza") ;
			MEN[3] = new Person("Matt", "Guttman") ;
			MEN[4] = new Person("Bob", "Woods") ;
			MEN[5] = new Person("Brian", "Janiga") ;
			MEN[6] = new Person("Scott", "Grayson") ;
			MEN[7] = new Person("Randy", "Brewer") ;
		}
		return MEN[index] ; 
	}
	
	private static Person GetWoman(int index) {
		if(WOMEN == null || WOMEN.length <= index ) {
			WOMEN = new Person[8] ; 
			
			WOMEN[0] = new Person("Connie", "Caputo") ;
			WOMEN[1] = new Person("Alyssa", "Ostrowski") ;
			WOMEN[2] = new Person("Angela", "Omilian") ;
			WOMEN[3] = new Person("Megan", "Williams") ;
			WOMEN[4] = new Person("Lindsey", "Nowak") ;
			WOMEN[5] = new Person("Katy", "Klehman") ;
			WOMEN[6] = new Person("Lisa", "McClure") ;
			WOMEN[7] = new Person("Alison", "Pepi") ;
		}
		return WOMEN[index] ; 
	}
}
