package edu.brandeis.cs12b.pa09;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import edu.brandeis.cs12b.pa09.PhoneFSM;
import edu.brandeis.cs12b.pa09.State;
import edu.brandeis.cs12b.pa09.Transition;

public class NonDeterministicTests {


	@Test
	public void stateNDTest() {
		State start = new State("state", false);
		State terminal = new State("terminal", true);
		assertEquals(null, start.getNextState('a'));
		
		start.addTransition(new Transition(new char[] {'a', 'b'}, start));
		start.addTransition(new Transition(new char[] {'b'}, terminal));
		
		Set<State> nextStates = start.getAllNextStates('b');
		assertTrue(nextStates.contains(start));
		assertTrue(nextStates.contains(terminal));
		assertEquals(2, nextStates.size());
		
		nextStates = start.getAllNextStates('a');
		assertTrue(nextStates.contains(start));
		assertEquals(1, nextStates.size());
	}
	
	@Test
	public void phoneTestForm1() {
		PhoneFSM phoneFSM = new PhoneFSM();
		assertTrue(phoneFSM.matchesND("1-123-456-7890"));
	}

	@Test
	public void phoneTestForm2() {
		PhoneFSM phoneFSM = new PhoneFSM();
		assertTrue(phoneFSM.matchesND("(123)456-7890"));
	}

	@Test
	public void phoneTestForm3() {
		PhoneFSM phoneFSM = new PhoneFSM();
		assertTrue(phoneFSM.matchesND("123-456-7890"));
	}

	@Test
	public void phoneTestForm4() {
		PhoneFSM phoneFSM = new PhoneFSM();
		assertTrue(phoneFSM.matchesND("123-4567"));
	}

	@Test
	public void phoneTestFailure() {
		PhoneFSM phoneFSM = new PhoneFSM();
		assertFalse(phoneFSM.matchesND("asalaskddvsd"));
	}
	
}
