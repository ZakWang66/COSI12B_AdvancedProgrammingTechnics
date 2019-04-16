package edu.brandeis.cs12b.pa09;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public abstract class FSM {
	protected State initial;
	
	/**
	 * after processing each character of the input string,
	 * checks if the current state is a terminal/end state
	 * @param input String to process
	 * @return true if processing every character of the input string 
	 * ends up in a terminal/end state
	 */
	public boolean matches(String input) {
		//TODO PART 1: Implement me
		return matchesND(input);
		//throw new UnsupportedOperationException("matches implemented");
	}
	
	/**
	 * after processing each character of the input string nondeterministically,
	 * checks if any of the possible current states is a terminal/end state
	 * @param input String to process
	 * @return true if processing every character of the input string
	 * could possibly end up in a terminal/end state
	 */
	public boolean matchesND(String input) {
		//TODO PART 2: Implement me
		if (input == null || input.length() == 0) return false;
		char[] in = input.toCharArray();
		int j = 0;
		Queue<State> states = new LinkedList<>();
		states.offer(initial);
		while (!states.isEmpty()) {
			int size = states.size();
			for (int i = 0; i < size; i++) {
				State current = states.poll();
				if (j == in.length) {
					if (current.isTerminal()) return true;
					//else return false;
				}
				else {
					char key = in[j];
					Set<State> nextStates = current.getAllNextStates(key);
					// if (nextStates.isEmpty()) return false;
					for (State s : nextStates) {
						states.offer(s);
					}
				}
			}
			j++;
		}
		return false;
		// throw new UnsupportedOperationException("matchesND not yet implemented");
	}
	
	/**
	 * EXTRA CREDIT: generates a String representing the FSM in DOT language,
	 * such that it can be read by GraphViz
	 * 
	 * @return the FSM as a DOT string
	 */
	public String toGraphVizString() {
		//TODO EXTRA CREDIT
		return null;
	}
}