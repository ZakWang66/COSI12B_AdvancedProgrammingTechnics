package edu.brandeis.cs12b.pa06;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Assignment {
	
	private String name;
	private Course course;
	private Map<TA, List<String>> groups;
	private int minAssignment;

	/**
	 * Initializes the Assignment object.
	 * @param name The name of the programming assignment
	 * @param course The Course object to which the assignment belongs
	 */
	public Assignment(String name, Course course) {
		this.name = name;
		this.course = course;
		groups = new HashMap<TA, List<String>>();
	}
	
	/**
	 * Randomly assign students to TAs evenly
	 */
	private void randomAssign() {
		List<String> students = course.getStudents();
		List<TA> tas = course.getTAs();
		// This value is the size of a 'small' group, a 'big' group will have one more element than this.
		minAssignment = students.size()/tas.size();
		for (TA ta : tas) {
			List<String> taStudentList = new LinkedList<String>();
			for (int i = 0; i < minAssignment; i++) {
				int seed = (int)Math.round((Math.random() * ((double)students.size() - 1)));
				String student = students.get(seed);
				students.remove(student);
				taStudentList.add(student);
			}
			groups.put(ta, taStudentList);
		}
		// Assign the rest to some TAs to make 'big' groups
		int i = 0;
		while (!students.isEmpty()) {
			groups.get(tas.get(i)).add(students.get(0));
			students.remove(0);
			i++;
		}
	}
	
	/**
	 * Solve the conflict problem by swapping so the even assignment rule won't be broken,
	 * if fail to swap, try to move the conflict student to another TA's group (this group must be a 'big' one
	 * and the target TA's group must be a 'small' one in order to move)
	 */
	private void solveConflict() {
		List<TA> tas = course.getTAs();
		for (TA thisTA : tas) {
			List<String> ttaStudentList = groups.get(thisTA);
			List<String> ttaConflictList = thisTA.getConflicts();
			for (int i = 0; i < ttaStudentList.size(); i++) {
				String e1 = ttaStudentList.get(i);
				if (ttaConflictList.contains(e1)) {
					if (!searchTarget(true, i, e1, thisTA, ttaStudentList, ttaConflictList)) {
						if (ttaStudentList.size() == minAssignment || !searchTarget(false, i, e1, thisTA, ttaStudentList, ttaConflictList))
							throw new ImpossibleGroupException();
						i--;
					}
				}
			}
		}
	}
	
	/**
	 * This function have two functions controlled by the boolean argument - function
	 * true:  Search for a target student in another TA's student list (pre-assignment list) who can swap with conflict student to solve the conflict
	 * false: Search for a target TA who can accept the conflict student from another TA when it has failed to swap, 
	 *        this can only success when the original TA has one more student than the target TA.
	 * @param i - the index of the original conflict student in the original TA's student list (pre-assignment list)
	 * @param e1 - the original conflict student
	 * @param thisTA - the original TA who reported a conflict
	 * @param ttaStudentList - the original TA's student list (pre-assignment list)
	 * @param ttaConflictList - the original TA's conflict list
	 * @return Whether the target conflict successfully solved by swapping/moving or not
	 */
	private boolean searchTarget(boolean function, int i, String e1, TA thisTA, List<String> ttaStudentList, List<String> ttaConflictList) {
		List<TA> tas = course.getTAs();
		for (TA anotherTA : tas) {
			List<String> ataStudentList = groups.get(anotherTA);
			List<String> ataConflictList = anotherTA.getConflicts();
			if (!anotherTA.getName().equals(thisTA.getName()) && !ataConflictList.contains(e1)) {
				// Swap
				if (function) {
					for (int j = 0; j < ataStudentList.size(); j++) {
						String e2 = ataStudentList.get(j);
						if (!ttaConflictList.contains(e2)) {
							swap (i, j, ttaStudentList, ataStudentList);
							return true;
						}
					}
				}
				// Move
				else {
					if (ataStudentList.size() == minAssignment) {
						move (i, ttaStudentList, ataStudentList);
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Swap two elements in two specific positions of two Lists
	 * @param p1
	 * @param p2
	 * @param l1
	 * @param l2
	 */
	private void swap (int p1, int p2, List<String> l1, List<String> l2) {
		String e1 = l1.remove(p1);
		String e2 = l2.remove(p2);
		l1.add(p1, e2);
		l2.add(p2, e1);
	}
	
	/**
	 * Remove a element from l1 and insert it to the end of l2
	 * @param p1
	 * @param l1
	 * @param l2
	 */
	private void move (int p1, List<String> l1, List<String> l2) {
		String e1 = l1.remove(p1);
		l2.add(e1);
	}
	
	/**
	 * Assign students rotatively to TAs based on the last assignment order
	 */
	private void rotateAssign() {
		Assignment lastAssignment = course.getAssignments().get(course.getAssignments().size() - 2);
		List<TA> tas = course.getTAs();
		List<String> ta0List = lastAssignment.getGroupForTA(tas.get(0));
		for (int i = 0; i < tas.size(); i++) {
			TA ta1 = tas.get(i);
			List<String> ta2List;
			if (i == tas.size() - 1) {
				ta2List = ta0List;
			}
			else {
				ta2List = lastAssignment.getGroupForTA(tas.get(i + 1));
			}
			groups.put(ta1, ta2List);
		}
	}
	
	/**
	 * Populates the groups map by assigning every TA to a group of students.
	 * Guarantees that groups will be evenly assigned, and that no TA will be
	 * assigned to a student whom they have declared as a conflict of interest.
	 * @throws ImpossibleGroupException if the problem is unsolvable in the given circumstances.
	 */
	public void createGroups() {
		if (course.getTAs().size() == 0) throw new ImpossibleGroupException();
		if (course.getAssignments().size() == 1) {
			randomAssign();
		}
		else {
			rotateAssign();
		}
		solveConflict();
		return;
	}

	/**
	 * Returns the group that has been assigned to a given TA.
	 * @param ta The TA whose group will be returned
	 * @return The names of the students to whom that TA has been assigned
	 */
	public List<String> getGroupForTA(TA ta) {
		return groups.get(ta);
	}

}
