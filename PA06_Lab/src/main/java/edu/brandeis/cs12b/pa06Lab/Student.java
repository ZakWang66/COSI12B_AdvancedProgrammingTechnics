package edu.brandeis.cs12b.pa06Lab;

public class Student implements Comparable<Student>{
	private int hours;
	public Student(int hours) {
		this.hours = hours;
	}
	
	public int getHours() {
		return hours;
	}
	
	public int compareTo(Student anotherStudent) {
		return anotherStudent.getHours() - getHours();
	}
	
	@Override
	public boolean equals(Object anotherStudent) {
		if (anotherStudent instanceof Student) {
			return ((Student)anotherStudent).getHours() == getHours();
		}
		else return false;
	}
}
