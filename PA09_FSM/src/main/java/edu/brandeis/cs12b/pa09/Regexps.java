package edu.brandeis.cs12b.pa09;

import java.util.regex.Pattern;

public class Regexps {
	
	public static String singleLetterRegexp = "[a-zA-Z]"; // Regexp for a single letter
	public static String alphabeticRegexp = "[a-zA-Z]+"; // Regexp for a string of one or more letters
	public static String manyLetterDigitRegexp = "([a-zA-Z][\\d])+"; //Regexp for alternating sequence of
	//letters and digits- at least one letter-digit pair must be present.
	public static String funkyRegexp = "[a-zA-Z]\\.[\\d]+";//Regexp for string starting with a letter, 
	//followed by a period, then one or more digits.
	
	public static String emailAddressRegexp = "[a-zA-Z\\d]+@[a-zA-Z\\d]+(.[a-zA-Z\\d]+)+";
	//TODO: fill in the email address regex
	
	public static void main(String[] args) {
		showRegexp(Regexps.singleLetterRegexp);
		showRegexp(Regexps.alphabeticRegexp);
		showRegexp(Regexps.manyLetterDigitRegexp);
		showRegexp(Regexps.funkyRegexp);
	}
	
	private static void showRegexp(String regexp) {
		System.out.println("Regexp: " + regexp);
		System.out.println("Is Valid? a " + Pattern.matches(regexp, "a"));
		System.out.println("Is Valid? abc " + Pattern.matches(regexp, "abc"));
		System.out.println("Is Valid? a1b2 " + Pattern.matches(regexp, "a1b2"));
		System.out.println("Is Valid? x.9057393223 " + Pattern.matches(regexp, "x.9057393223"));
		System.out.println();
	}

}
