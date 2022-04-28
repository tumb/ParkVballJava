package controller;

import java.util.Objects;

public class League {
	private String dayOfWeek ; // Monday, Thursday, etc
	private int year ; // 2022 etc
	private int numberOfDivisions ; 
	private String divisionName ; 
	
	public boolean isLeagueSelected() {
		boolean isSelected = year > 0 ;
		isSelected = isSelected && !(divisionName == null || divisionName.isEmpty()) ;
		isSelected = isSelected && !(dayOfWeek == null || dayOfWeek.isEmpty()) ; 
		return isSelected ; 
	}
	
	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setYear(String yearString) {
		this.year = Integer.parseInt(yearString) ;
	}
	
	public int getNumberOfDivisions() {
		return numberOfDivisions;
	}

	public void setNumberOfDivisions(int numberOfDivisions) {
		this.numberOfDivisions = numberOfDivisions;
	}
	
	public String toString() {
		return " " + this.divisionName + " division" + " for " + this.dayOfWeek + "s " + year ; 
	}

	public boolean isValid() {
		boolean isValid = year > 0 ;
		isValid = isValid && !(dayOfWeek == null || dayOfWeek.isEmpty()) ; 
		return isValid ; 
	}
	
	public boolean isMissingDayYearOrDivision() {
		boolean isEmpty = year == 0 ;
		isEmpty = isEmpty || (this.dayOfWeek == null || this.dayOfWeek.isEmpty()) ;
		isEmpty = isEmpty || (this.divisionName == null || this.divisionName.isEmpty()) ;
		return isEmpty ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dayOfWeek, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		League other = (League) obj;
		return Objects.equals(dayOfWeek, other.dayOfWeek) && year == other.year;
	}

	public boolean hasDivision() {
		return ! (this.divisionName == null || this.divisionName.isEmpty());
	}
	
}
