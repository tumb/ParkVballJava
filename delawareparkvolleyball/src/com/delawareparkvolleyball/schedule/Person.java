package com.delawareparkvolleyball.schedule;

public class Person {
	private String firstName ; 
	private String lastName ; 
	private boolean isMale ; 
	
	private static Person[] MEN ;
	private static Person[] WOMEN ; 
	
	public Person(String firstName, String lastName) {
		this.firstName = firstName ; 
		this.lastName = lastName ; 
	}

	public Person(String firstName, String lastName, String gender) {
		this.firstName = firstName ; 
		this.lastName = lastName ; 
		this.isMale = "M".equalsIgnoreCase(gender) ;
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
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (isMale ? 1231 : 1237);
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (isMale != other.isMale)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
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
