package edu.brandeis.cs12b.pa06;

import java.util.ArrayList;
import java.util.List;

public class TA {
	
	private String name;
	private List<String> conflicts;
	
	public TA(String name) {
		this.name = name;
		conflicts = new ArrayList<String>();
	}
	
	/**
	 * @return The name of the TA
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Adds a student to the TAs list of conflicts of interest.
	 * @param student The name of the student to be declared as a conflict
	 */
	public void addConflict(String student) {
		conflicts.add(student);
	}
	
	/**
	 * @return A copy of the TA's List of conflicts of interest
	 */
	public List<String> getConflicts() {
		return new ArrayList<String>(conflicts);
	}

}
