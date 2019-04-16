package edu.brandeis.cs12b.pa10.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.brandeis.cs12b.pa10.lexer.*;
import edu.brandeis.cs12b.pa10.parser.ParseTreeNode;

public class Interpreter {
	
	List<ParseTreeNode> ptns;
	Map<String, Double> res;
	
	public Interpreter(List<ParseTreeNode> ptns) {
		this.ptns = ptns;
	}

	/**
	 * Evaluates all ParseTreeNodes in the given list
	 * @return a map matching variable names to their corresponding values
	 */
	public Map<String, Double> evaluate() {
		// TODO Implement me!
		res = new HashMap<>();
		for (ParseTreeNode node : ptns) {
			res.put(node.getLeft().getLexeme().getValueAsString(), 
					evaluateNode(node.getRight()).getLexeme().getValueAsNumber());
		}
		return res;
	}
	
	private ParseTreeNode evaluateNode(ParseTreeNode node) {
		// At most one user input per expression.
		switch (node.getLexeme().getType()) {
		case NUMBER: return node;
		case VARIABLE:
			// Not found will cause exception
			return numToNode(res.get(node.getLexeme().getValueAsString()));
		case USER_INPUT:
			return numToNode(promptUser());
		case OPERATOR:
			double left = evaluateNode(node.getLeft()).getLexeme().getValueAsNumber();
			double right = evaluateNode(node.getRight()).getLexeme().getValueAsNumber();
			switch (node.getLexeme().getValueAsString()) {
			case "+": return numToNode(left + right);
			case "-": return numToNode(left - right);
			case "*": return numToNode(left * right);
			case "/": return numToNode(left / right);
			case "^": return numToNode(Math.pow(left, right));
			}		
		default: return null;
		}
	}
	
	private ParseTreeNode numToNode(Double num) {
		return new ParseTreeNode(new Lexeme(LexemeType.NUMBER, num.toString()));
	}

	private Double promptUser() {
		Scanner scan = new Scanner(System.in);
		return scan.nextDouble();
	}
	
}
