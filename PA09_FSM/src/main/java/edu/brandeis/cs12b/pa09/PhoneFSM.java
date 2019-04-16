package edu.brandeis.cs12b.pa09;

public class PhoneFSM extends FSM {	
	/**
	 * Constructs an FSM for which matchesND(String input) returns true
	 * when input is a valid phone number, and false otherwise.
	 * A valid phone number is defined as a string of any of the following forms:
	 	* #-###-###-####
	 	* (###)###-####
	 	* ###-###-####
	 	* ###-####
	 */
	static final char[] NUMBERS = new char[] {'0', '1', '2' ,'3', '4', '5', '6', '7', '8', '9'};
	static final char[] DASH = new char[] {'-'};
	static final char[] LEFTBRACKET = new char[] {'('};
	static final char[] RIGHTBRACKET = new char[] {')'};
	
	State S_nationCode;
	State S_dashNC;
	
	State S_stateCode1;
	State S_stateCode2;
	State S_stateCode3;
	State S_dashSC;
	
	State S_leftBracket;
	State S_inBracket1;
	State S_inBracket2;
	State S_inBracket3;
	State S_rightBracket;
	
	State S_digit1;
	State S_digit2;
	State S_digit3;
	State S_dash;
	State S_digit4;
	State S_digit5;
	State S_digit6;
	State S_digit7;
	
	public PhoneFSM() {
		//TODO PART 2: Implement me
		initial = new State("INIT", false);
		
		S_nationCode = new State("S_nationCode", false);
		S_dashNC = new State("S_dashNC", false);
		
		S_stateCode1 = new State("S_stateCode1", false);
		S_stateCode2 = new State("S_stateCode2", false);
		S_stateCode3 = new State("S_stateCode3", false);
		S_dashSC = new State("S_dashSC", false);
		
		S_leftBracket = new State("S_leftBracket", false);
		S_inBracket1 = new State("S_inBracket1", false);
		S_inBracket2 = new State("S_inBracket2", false);
		S_inBracket3 = new State("S_inBracket3", false);
		S_rightBracket = new State("S_rightBracket", false);
		
		S_digit1 = new State("S_digit1", false);
		S_digit2 = new State("S_digit2", false);
		S_digit3 = new State("S_digit3", false);
		S_dash = new State("S_dash", false);
		S_digit4 = new State("S_digit4", false);
		S_digit5 = new State("S_digit5", false);
		S_digit6 = new State("S_digit6", false);
		S_digit7 = new State("S_digit7", true);
		
		initial.addTransition(new Transition(NUMBERS, S_nationCode));
		initial.addTransition(new Transition(LEFTBRACKET, S_leftBracket));
		initial.addTransition(new Transition(NUMBERS, S_stateCode1));
		initial.addTransition(new Transition(NUMBERS, S_digit1));
		
		S_nationCode.addTransition(new Transition(DASH, S_dashNC));
		S_dashNC.addTransition(new Transition(NUMBERS, S_stateCode1));
		
		S_stateCode1.addTransition(new Transition(NUMBERS, S_stateCode2));
		S_stateCode2.addTransition(new Transition(NUMBERS, S_stateCode3));
		S_stateCode3.addTransition(new Transition(DASH, S_dashSC));
		S_dashSC.addTransition(new Transition(NUMBERS, S_digit1));
		
		S_leftBracket.addTransition(new Transition(NUMBERS, S_inBracket1));
		S_inBracket1.addTransition(new Transition(NUMBERS, S_inBracket2));
		S_inBracket2.addTransition(new Transition(NUMBERS, S_inBracket3));
		S_inBracket3.addTransition(new Transition(RIGHTBRACKET, S_rightBracket));
		S_rightBracket.addTransition(new Transition(NUMBERS, S_digit1));
		
		S_digit1.addTransition(new Transition(NUMBERS, S_digit2));
		S_digit2.addTransition(new Transition(NUMBERS, S_digit3));
		S_digit3.addTransition(new Transition(DASH, S_dash));
		S_dash.addTransition(new Transition(NUMBERS, S_digit4));
		S_digit4.addTransition(new Transition(NUMBERS, S_digit5));
		S_digit5.addTransition(new Transition(NUMBERS, S_digit6));
		S_digit6.addTransition(new Transition(NUMBERS, S_digit7));
	}
}