package edu.brandeis.cs12b.pa10.lexer;

import java.util.Iterator;

public class Lexer implements Iterator<Lexeme> {
	
	private String toLex;
	private int currentIndex;
	private int nextIndex;
	private Lexeme next;
	
	private LexemeType getCharType(char c) {
		if ((c >= '0' && c <= '9') || c == '.') {
			return LexemeType.NUMBER;
		}
		else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
			return LexemeType.VARIABLE;
		}
		else {
			switch (c) {
			case '(': return LexemeType.LEFT_PAREN;
			case ')': return LexemeType.RIGHT_PAREN;
			
			case '*': return LexemeType.OPERATOR;
			case '+': return LexemeType.OPERATOR;
			case '-': return LexemeType.OPERATOR;
			case '/': return LexemeType.OPERATOR;
			case '^': return LexemeType.OPERATOR;
			
			case '=': return LexemeType.EQUALS;
			case ';': return LexemeType.SEMICOLON;
			case '?': return LexemeType.USER_INPUT;
			}
		}
		return null;
	}
	
	/**
	 * Initializes a lexer
	 * @param toLex the PitoScript program that will be turned into a series of Lexemes
	 */
	public Lexer(String toLex) {
		this.toLex = toLex;
		currentIndex = -1;
	}

	/**
	 * @return true if the String toLex has more characters to be turned into Lexemes, otherwise, false
	 */
	@Override
	public boolean hasNext() {
		// TODO Implement me!
		nextIndex = currentIndex + 1;
		if (nextIndex > toLex.length() - 1) {
			next = null;
			return false;
		}
		char c = toLex.charAt(nextIndex);
		while (nextIndex < toLex.length() && (c == ' ' || c == '\n')) {
			nextIndex++;
			c = toLex.charAt(nextIndex);
		}
		LexemeType nextType = getCharType(c);
		if (nextType == null) {
			// TODO throw IllegalSymbolException
			return false;
		}
		else if (nextType != LexemeType.NUMBER && nextType != LexemeType.VARIABLE && nextType != LexemeType.OPERATOR) {
			next = new Lexeme(nextType);
			return true;
		}
		else {
			String nextValue = "";
			while (nextIndex < toLex.length() && getCharType(toLex.charAt(nextIndex)) == nextType) {
				nextValue += toLex.charAt(nextIndex);
				nextIndex++;
			}
			nextIndex--;
			next = new Lexeme(nextType, nextValue);
			return true;
		}
	}
	
	/**
	 * @return the Lexeme generated from the next non-whitespace characters in toLex
	 */
	@Override
	public Lexeme next() {
		// TODO Implement me!
		hasNext();
		System.out.println(next);
		currentIndex = nextIndex;
		return next;
	}
	
	
}
