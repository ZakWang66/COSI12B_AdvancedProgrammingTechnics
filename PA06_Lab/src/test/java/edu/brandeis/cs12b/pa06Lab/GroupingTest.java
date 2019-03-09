package edu.brandeis.cs12b.pa06Lab;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.Test;

import edu.brandeis.cs12b.pa06Lab.Group;
import edu.brandeis.cs12b.pa06Lab.GroupMaker;
import edu.brandeis.cs12b.pa06Lab.ImpossibleGroupingException;
import edu.brandeis.cs12b.pa06Lab.Student;

public class GroupingTest {
	
	private List<Student> makeStudents(int classSize){
		List<Student> students = new LinkedList<Student>();
		for (int i = 1; i<=classSize; i++) {
			students.add(0,new Student(i));
		}
		return students;
	}
    
	private boolean checkGroupings(Set<Group> groups, int desiredHours, int groupCount) {
		if(groups.size()!= groupCount) return false;
		for (Group g : groups) {
			if(g.sumHours()!=desiredHours) return false;
		}
		return true;
	}
	

	@Test
	public void testPossible1() {
		int classSize = 5;
		int groupCount = 3;
		int desiredHours = 5;
		List<Student> students = makeStudents(classSize);
		Set<Group> groups = GroupMaker.groupStudents(students, groupCount);
		assertTrue(checkGroupings(groups,desiredHours,groupCount));
	}

	@Test
	public void testPossible2() {
		int groupCount = 3;
		int classSize = 8;
		int desiredHours = 12;
		List<Student> students = makeStudents(classSize);
		Set<Group> groups = GroupMaker.groupStudents(students, groupCount);
		assertTrue(checkGroupings(groups,desiredHours,groupCount));
	}


	@Test
	public void testPossible3() {
		int groupCount = 4;
		int classSize = 8;
		int desiredHours = 9;
		List<Student> students = makeStudents(classSize);
		Set<Group> groups = GroupMaker.groupStudents(students, groupCount);
		assertTrue(checkGroupings(groups,desiredHours,groupCount));
	}
	

	@Test
	public void testImpossible1() {
		try {
			int groupCount = 4;
			int classSize = 6;
			List<Student> students = makeStudents(classSize);
			Set<Group> groups = GroupMaker.groupStudents(students, groupCount);
			fail();
		} catch(ImpossibleGroupingException e) {}
	}
	
	@Test
	public void testImpossible2() {
		try {
			int groupCount = 7;
			int classSize = 7;
			List<Student> students = makeStudents(classSize);
			Set<Group> groups = GroupMaker.groupStudents(students, groupCount);
			fail();
		} catch(ImpossibleGroupingException e) {}
	}

}

