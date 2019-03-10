package edu.brandeis.cs12b.pa06Lab;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GroupMaker  {
	
	/**
	 * Your task is to generate student groups for an upcoming class
	 * project.
	 * 
	 * @param students a List of students who must be sorted into groups
	 * @param groupCount the number of groups that must be created.
	 * @return groups a Set of Group objects.
	 *
	 * Each student has an attribute, hours, which
	 * represents the number of hours the student is able to put into the
	 * project. To make this problem easier, the List has the following order:
	 * Suppose there are N students in the class. Then the first student on the 
	 * list will have N hours, the second student will have N-1 hours, the third 
	 * will have N-2 hours, etc., until the last student on the list has only 1 
	 * hour of availability. 
	 * 
	 * Each group must have the same TOTAL number of hours of availability. Ex:
	 * if there is a class of 4 students, and 2 groups are desired,
	 * then valid groupings would be: (student with 1 hour and the student 
	 * with 4 hours), and (student with 2 hours and the student with 3 hours)
	 * because 1+4 = 2+3.
	 * 
	 * When it is impossible to create a grouping satisfying the requirements,
	 * you must throw the ImpossibleGroupingException.
	 * 
	 * @throws ImpossibleGroupingException
	 */
	
    public static Set<Group> groupStudents(List<Student> students, int groupCount){
		//TODO Implement Me!
		double averageHour = 0.0;
		Collections.sort(students);
		for (Student s : students) {
			averageHour += (int)s.getHours();
		}
    	averageHour /= (double)groupCount;
    	if (averageHour != (int)averageHour) throw new ImpossibleGroupingException();
    	Set<Group> result = new HashSet<Group>();
    	while (!students.isEmpty()) {
    		Group g = new Group();
    		g.addStudent(students.get(0));
    		Iterator <Student> iterator = students.iterator();
    		if (iterator.hasNext()) iterator.next();	
    		else return result;
    		if (makeGroup(iterator, g, students, (int)averageHour)) {
    			result.add(g);
    			students.remove(0);
    		}
    		else throw new ImpossibleGroupingException();
    	}
    	return result;
    }
    
    private static boolean makeGroup(Iterator<Student> iterator, Group g, List<Student> students, int averageHour) {
		if (g.sumHours() == averageHour) {
			return true;
		}
		while(iterator.hasNext()) {
			Student s = iterator.next();
			if (s.getHours() + g.sumHours() <= averageHour) {
				g.addStudent(s);
				iterator.remove();
				if (g.sumHours() == averageHour) {
					return true;
				}
				else {
					return makeGroup(iterator, g, students, averageHour);
				}
			}
		}
		return false;
    }
    
}
