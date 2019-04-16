package edu.brandeis.cs12b.pa10.parser;

import edu.brandeis.cs12b.pa10.lexer.Lexeme;
import edu.brandeis.cs12b.pa10.lexer.LexemeType;

public class ParseTreeNode {
	private Lexeme value;
	private ParseTreeNode left;
	private ParseTreeNode right;




	public ParseTreeNode(Lexeme value) {
		this.value = value;
	}

	public ParseTreeNode getLeft() {
		return left;
	}

	public void setLeft(ParseTreeNode left) {
		this.left = left;
	}
	public ParseTreeNode getRight() {
		return right;
	}
	public void setRight(ParseTreeNode right) {
		this.right = right;
	}

	public Lexeme getLexeme() {
		return value;
	}

	@Override
	public String toString() {
		return "digraph G {\n" + toString("") + "\n}";
	}
	
	public String toString(String prefix, boolean withPrefix) {
		return "digraph G {\n" + toString(prefix) + "\n}";
	}

	private String toString(String path) {
		StringBuilder toR = new StringBuilder();
		String myLabel = getLexeme().getType().toString() + path;

		String label = getLexeme().getValueAsString();
		if (label == null && getLexeme().getType() == LexemeType.EQUALS)
			label = "=";
		
		toR.append(myLabel + " [ label=\"" + label + "\" ];\n");


		if (getLeft() != null) {
			String newPath = path + "L";
			toR.append(myLabel 
					+ " -> " 
					+ getLeft().getLexeme().getType().toString() + newPath 
					+ ";\n");
			toR.append(getLeft().toString(newPath));
		}

		if (getRight() != null) {
			String newPath = path + "R";
			toR.append(myLabel 
					+ " -> " 
					+ getRight().getLexeme().getType().toString() + newPath 
					+ ";\n");
			toR.append(getRight().toString(newPath));
		}

		return toR.toString();
	}

}
