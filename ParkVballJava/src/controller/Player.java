package controller;

import java.util.Objects;

public class Player {
	int id ; 
	String firstName ; 
	String lastName ; 
	String gender ; // M or F only
	String phone ; 
	String email ;
	
	public Player(String firstName, String lastName, String gender, String email, String phone, int id) {
		this.firstName = firstName ; 
		this.lastName = lastName ; 
		this.gender = gender ; 
		this.email = email ; 
		this.phone = phone ; 
		this.id = id ; 
		
		if(this.email == null || this.email.isEmpty()) {
			this.email = "unknown" ; 
		}
		if(this.phone == null || this.phone.isEmpty()) {
			this.phone = "unknown" ; 
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	} 
	
	public String toString() {
		return firstName + " " + lastName + " " + gender + " \t phone: " + phone + ", email: " + email + " id: " + id ;  
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName);
	}

	public boolean isValid() {
		boolean isValid = !(firstName == null || firstName.isEmpty()) ; 
		isValid = isValid && !(lastName == null || lastName.isEmpty()) ;
		isValid = isValid && !(gender == null || gender.isEmpty()) ;
		return isValid ;
	}
}
