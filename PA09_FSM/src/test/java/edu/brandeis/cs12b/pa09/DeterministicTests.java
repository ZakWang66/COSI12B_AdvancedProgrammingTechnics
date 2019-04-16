package edu.brandeis.cs12b.pa09;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.brandeis.cs12b.pa09.BinaryFSM;
import edu.brandeis.cs12b.pa09.State;
import edu.brandeis.cs12b.pa09.Transition;

public class DeterministicTests {
	@Test
	public void stateTest() {
		State s = new State("state", false);
		assertEquals(null, s.getNextState('a'));
		
		s.addTransition(new Transition(new char[] {'a', 'b'}, s));
		assertEquals("state", s.getName());
		assertEquals(s.getName(), s.getNextState('a').getName());
		assertEquals(s.getName(), s.getNextState('b').getName());
		assertEquals(null, s.getNextState('c'));
	}
	
	@Test
	public void transitionTest() {
		State s = new State("state", false);
		Transition t = new Transition(new char[] {'a', 'b'}, s);
		
		assertEquals(null, t.moveOn('c'));
		assertEquals(null, t.moveOn('0'));
		assertEquals(s.getName(), t.moveOn('a').getName());
		assertEquals(s.isTerminal(), t.moveOn('a').isTerminal());
		assertEquals(s.getName(), t.moveOn('b').getName());
		assertEquals(s.isTerminal(), t.moveOn('b').isTerminal());
	}
	
	@Test
	public void binaryTestSingleDigit() {
		BinaryFSM binaryFSM = new BinaryFSM();
		assertTrue(binaryFSM.matches("0"));
		assertTrue(binaryFSM.matches("1"));
	}

	@Test
	public void binaryTestManyDigit() {
		BinaryFSM binaryFSM = new BinaryFSM();
		assertTrue(binaryFSM.matches("011100101000"));
		assertTrue(binaryFSM.matches("111100101000"));
	}

}
