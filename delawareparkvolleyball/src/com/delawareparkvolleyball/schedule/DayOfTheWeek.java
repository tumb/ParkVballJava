package com.delawareparkvolleyball.schedule;

public class DayOfTheWeek {
	public static DayOfTheWeek MONDAY = new DayOfTheWeek("Monday") ; 
	public static DayOfTheWeek THURSDAY = new DayOfTheWeek("Thursday") ; 
	private String day ; 
	
	private DayOfTheWeek(String day) {
		this.day = day ; 
	}
	
	@Override
	public String toString() {
		return day ; 
	}
	
}
