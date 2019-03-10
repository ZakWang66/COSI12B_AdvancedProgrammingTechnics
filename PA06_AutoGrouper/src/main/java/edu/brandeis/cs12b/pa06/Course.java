package edu.brandeis.cs12b.pa06;

import java.util.ArrayList;
import java.util.List;

public class Course {
	
	private String name;
	private List<Assignment> assignments;
	private List<String> students;
	private List<TA> tas;

	public Course(String name) {
		this.name = name;
		assignments = new ArrayList<Assignment>();
		students = new ArrayList<String>();
		tas = new ArrayList<TA>();
	}
	
	/**
	 * @return the name of the course
	 */
	public String getName() {
		return name;
	}
	
	/** 
	 * Adds a new TA to this course's list of TAs.
	 * @param ta The TA to be added
	 */
	public void addTA(TA ta) {
		tas.add(ta);
	}
	
	/**
	 * Creates a new assignment and adds it to the course.
	 * @param name The name of the assignment to be created
	 * @return The created Assignment object
	 */
	public Assignment addAssignment(String name) {
		Assignment newAssignment = new Assignment(name, this);
		assignments.add(newAssignment);
		return newAssignment;
	}
	
	/**
	 * @return A copy of the List of all assignments in the course
	 */
	public List<Assignment> getAssignments() {
		return new ArrayList<Assignment>(assignments);
	}
	
	/**
	 * @return A copy of the List of all TAs in the course
	 */
	public List<TA> getTAs() {
		return new ArrayList<TA>(tas);
	}

	/**
	 * Adds a student to the course's list of students.
	 * @param studentName The name of the student to be added
	 */
	public void addStudent(String studentName) {
		students.add(studentName);
	}
	
	/**
	 * @return A copy of the List of all students in the course
	 */
	public List<String> getStudents() {
		return new ArrayList<String>(students);
	}

}
