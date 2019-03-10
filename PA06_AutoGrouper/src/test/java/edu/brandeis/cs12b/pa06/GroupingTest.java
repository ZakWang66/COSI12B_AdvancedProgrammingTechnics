package edu.brandeis.cs12b.pa06;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class GroupingTest {	
	
	private Course cosi12;
	private TA ari;
	private List<String> students;
	
	@Rule
	public Timeout globalTimeout = new Timeout(60000); // 60 seconds max per method tested
	
	@Before
	// Prepare a course full of TAs and students to use in our tests.
	public void setUp() {
		cosi12 = new Course("COSI 12B");
		ari = new TA("Ari");
		String[] tas = {"Adam", "Belle", "Cam", "Evalyn", "Mitchell", "Roman", "Sam"};
		
		cosi12.addTA(ari);
		for(String name : tas) cosi12.addTA(new TA(name));

		students = new ArrayList<String>();
		for(int i = 1; i <= 80; i++) students.add("Student #" + i);
		for(String s : students) cosi12.addStudent(s);
	}
	
	@Test
	// Check that evenly-sized groups are assigned and that all students are covered.
	public void testBasicGrouping() {
		Assignment pa1 = cosi12.addAssignment("PA1");
		pa1.createGroups();
		Set<String> groupedStudents = new HashSet<String>();
		for(TA ta : cosi12.getTAs()) {
			List<String> curr = pa1.getGroupForTA(ta);
			assertEquals(10, curr.size());
			groupedStudents.addAll(curr);
		}
		assertEquals(new HashSet<String>(students), groupedStudents);
	}
	
	@Test
	// Check that the groups don't stay the same.
	public void testVaryingGroups() {
		Assignment pa1 = cosi12.addAssignment("PA1");
		pa1.createGroups();
		Assignment pa2 = cosi12.addAssignment("PA2");
		pa2.createGroups();
		for(TA ta : cosi12.getTAs()) {
			assertNotEquals(pa1.getGroupForTA(ta), pa2.getGroupForTA(ta));
		}
	}
	
	@Test
	// Make sure Ari is never assigned to his conflict of interest.
	public void testConflictAvoidance() {
		ari.addConflict("Student #13");
		for(int i = 0; i < 100000; i++) {
			Assignment pa = cosi12.addAssignment("PA" + i);
			pa.createGroups();
			assertFalse(pa.getGroupForTA(ari).contains("Student #13"));
		}
	}
	
	@Test
	// Test a case in which the problem is unsolvable.
	public void testImpossible() {
		String s = students.get(0);
		for(TA ta : cosi12.getTAs()) ta.addConflict(s);
		Assignment pa1 = cosi12.addAssignment("PA1");
		try {
			pa1.createGroups();
			fail();
		} catch(ImpossibleGroupException e) {}
	}
	
	@Test
	// Test that the groups' sizes only differ by 1 at most.
	public void testUnevenGroups() {
		Course eightyOneStudents = new Course("81 Students");
		for(TA ta : cosi12.getTAs()) eightyOneStudents.addTA(ta);
		for(int i = 1; i <= 81; i++) eightyOneStudents.addStudent("Student #" + i);
		Assignment pa1 = eightyOneStudents.addAssignment("PA1");
		pa1.createGroups();
		
		// Make sure that there are 7 groups of 10 students and 1 group of 11 students
		Map<Integer, Integer> groupSizeCount = new HashMap<Integer, Integer>();
		Set<String> studentsInGroups = new HashSet<String>();
		for(TA ta : eightyOneStudents.getTAs()) {
			List<String> group = pa1.getGroupForTA(ta);
			studentsInGroups.addAll(group);
			int size = group.size();
			if(groupSizeCount.containsKey(size)) groupSizeCount.put(size, groupSizeCount.get(size)+1);
			else groupSizeCount.put(size, 1);
		}
		
		assertEquals(new HashSet<String>(eightyOneStudents.getStudents()), studentsInGroups);
		assertEquals(1, (int) groupSizeCount.get(11));
		assertEquals(7, (int) groupSizeCount.get(10));
	}
	
//	EXTRA CREDIT: Uncomment the "@Test" line to run this unit test!
	@Test
//	Test that a small course can rotate its groups
	public void testExtraCreditRotation() {
		Course rotationExample = new Course("Rotation example");
		List<TA> tas = Arrays.asList(new TA[] {new TA("Ari"), new TA("Cam"), new TA("Sam")});
		for(TA ta : tas) rotationExample.addTA(ta);
		for(int i = 1; i <= 15; i++) rotationExample.addStudent("Student " + i);
		
		Assignment pa1 = rotationExample.addAssignment("PA1");
		pa1.createGroups();
		Assignment pa2 = rotationExample.addAssignment("PA2");
		pa2.createGroups();
		Assignment pa3 = rotationExample.addAssignment("PA3");
		pa3.createGroups();
		
		// Determine the order in which groups rotate
		List<TA> rotationOrder = buildRotationOrder(tas, pa1, pa2);
		
		// Make sure that group order is upheld
		for(int i = 0; i < tas.size(); i++) {
			TA current = rotationOrder.get(i);
			TA next = rotationOrder.get((i+1)%tas.size());
			// Using HashSets means the order of the students in lists is ignored.
			assertEquals(new HashSet<String>(pa2.getGroupForTA(current)), new HashSet<String>(pa3.getGroupForTA(next)));
		}
	}
	
	private List<TA> buildRotationOrder(List<TA> tas, Assignment pa1, Assignment pa2) {
		List<TA> rotationOrder = new ArrayList<TA>();
		TA currentTA = tas.get(0);
		rotationOrder.add(currentTA);
		
		while(rotationOrder.size() < tas.size()) {
			currentTA = findTAForGroup(tas, pa2, pa1.getGroupForTA(currentTA));
			assertNotNull(currentTA);
			rotationOrder.add(currentTA);
		}
		
		assertEquals(new HashSet<TA>(rotationOrder), new HashSet<TA>(tas));
		
		return rotationOrder;
	}

	private TA findTAForGroup(List<TA> tas, Assignment pa2, List<String> pa1Group) {
		for(TA ta : tas) {
			if((new HashSet<String>(pa2.getGroupForTA(ta))).equals(new HashSet<String>(pa1Group))) {
				return ta;
			}
		}
		return null;
	}
	
}
