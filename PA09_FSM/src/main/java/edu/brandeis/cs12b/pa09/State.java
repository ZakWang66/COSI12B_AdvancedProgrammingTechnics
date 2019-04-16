package edu.brandeis.cs12b.pa09;
import java.util.*;

public class State {
	private String name;
	private Set<Transition> transitions;
	private boolean isTerminal;
	
	/**
	 * constructs a new State object, with an empty set of transitions
	 * @param name of the state
	 * @param isTerminal whether or not it is a terminal/end state
	 */
	public State(String name, boolean isTerminal) {
		//TODO PART 1: Implement me
		transitions = new HashSet<>();
		this.name = name;
		this.isTerminal = isTerminal;
	}

	/**
	 * add a transition to this state's set of transitions
	 * @param t transition to add
	 */
	public void addTransition(Transition t) {
		//TODO PART 1: Implement me
		transitions.add(t);
	}

	/**
	 * find a transition in this state's set of transitions that matches
	 * the given character, and return the State object that this
	 * transition moves to
	 * @param inputChar character to match
	 * @return the State that the transition that matches inputChar moves to, 
	 * or null if none exists
	 */
	public State getNextState(char inputChar) {
		//TODO PART 1: Implement me
		for (Transition t : transitions) {
			// Find the first match and return
			State next = t.moveOn(inputChar);
			if (next != null) return next;
		}
		return null;
	}
	
	/**
	 * find all possible States you could end up at by transitioning
	 * on the given char
	 * @param c character to match
	 * @return set of possible States after moving on char c
	 */
	public Set<State> getAllNextStates(char inputChar) {
		//TODO PART 2: Implement me
		Set<State> res = new HashSet<>();
		for (Transition t : transitions) {
			// Find the first match and return
			State next = t.moveOn(inputChar);
			if (next != null) res.add(next);
		}
		return res;
	}
	
	/**
	 * @return the name of this state
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return true if is a terminal state, false if not
	 */
	public boolean isTerminal() {
		return isTerminal;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}