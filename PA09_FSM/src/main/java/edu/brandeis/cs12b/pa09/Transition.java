package edu.brandeis.cs12b.pa09;

public class Transition {
	private char[] matchChars;
	private State matchState;
	
	/**
	 * Constructs a transition that moves to matchState when the input character 
	 * is equal to any of the characters in the matchChars array
	 * @param matchChar
	 * @param matchState
	 */
	public Transition(char[] matchChars, State matchState) {
		//TODO PART 1: Implement me
		this.matchChars = matchChars;
		this.matchState = matchState;
	}
	
	/**
	 * Attempts to move on the transition, given an input char
	 * @param input
	 * @return matchState if the input character is equal to any of the characters 
	 * in matchChar[]; null otherwise
	 */
	public State moveOn(char input) {
		// TODO PART 1: Implement me
		for (char m : matchChars) {
			if (m == input) return matchState;
		}
		return null;
	}
}