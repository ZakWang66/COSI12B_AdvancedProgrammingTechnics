package edu.brandeis.cs12b.pa09;

public class BinaryFSM extends FSM {	
	/**
	 * Constructs an FSM for which matches(String input) returns true
	 * when input is a valid binary number, and false otherwise.
	 * A valid binary number is defined as a sequence of 1 or more 1's and 0's.
	 */
	State oneZeroState;
	public BinaryFSM() {
		//TODO PART 1: Implement me
		initial = new State("INIT", false);
		oneZeroState = new State("oneZeroState", true);
		initial.addTransition(new Transition(new char[] {'0','1'}, oneZeroState));
		oneZeroState.addTransition(new Transition(new char[] {'0','1'}, oneZeroState));
	}
}