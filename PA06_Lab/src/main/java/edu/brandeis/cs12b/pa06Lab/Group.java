package edu.brandeis.cs12b.pa06Lab;

import java.util.HashSet;
import java.util.Set;

public class Group {
	private Set<Student> students;
	public Group() {
		students = new HashSet<Student>();
	}
	
	public void addStudent(Student s) {
		students.add(s);
	}
	
	public int sumHours() {
		int hours = 0;
		for (Student s : students) {
			hours  += s.getHours();
		}
		return hours;
	}
	
}
