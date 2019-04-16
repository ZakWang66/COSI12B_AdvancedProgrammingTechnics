package edu.brandeis.cs12b.pa10.lexer;

public class Lexeme {
	private LexemeType type;
	private String value;
	
	public Lexeme(LexemeType type, String value) {
		this.type = type;
		this.value = value;
	}

	public Lexeme(LexemeType type) {
		this.type = type;
		value = null;
	}

	public LexemeType getType() {
		return type;
	}
	
	public String getValueAsString() {
		return value;
	}
	
	public double getValueAsNumber() {
		return Double.valueOf(value);
	}
	
	@Override
	public String toString() {
		return "[" + type + ": " + value + "]";
	}
	
	
}
