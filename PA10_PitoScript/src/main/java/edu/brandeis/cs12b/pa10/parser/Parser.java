package edu.brandeis.cs12b.pa10.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.brandeis.cs12b.pa10.lexer.Lexeme;
import edu.brandeis.cs12b.pa10.lexer.LexemeType;

public class Parser {

	Iterator<Lexeme> lexemes;
	
	public Parser(Iterator<Lexeme> lexemes) {
		this.lexemes = lexemes;
	}

	/**
	 * Turns the iterator of Lexemes into a list of ParseTreeNodes, with each element
	 * being the root of a different parse tree
	 * @return the List of ParseTreeNodes
	 */
	public List<ParseTreeNode> parse() {
		// TODO Implement me!
		if (lexemes == null) return null;
		List<List<ParseTreeNode>> data = new ArrayList<>();
		List<ParseTreeNode> res = new ArrayList<>();
		List<ParseTreeNode> temp = new ArrayList<>();
		// Divide data with semicolons
		while (lexemes.hasNext()) {
			Lexeme nextLexeme = lexemes.next();
			if (nextLexeme.getType() != LexemeType.SEMICOLON) {
				temp.add(new ParseTreeNode(nextLexeme));
			}
			else {
				data.add(temp);
				temp = new ArrayList<>();
			}
		}
		// For each record
		for (List<ParseTreeNode> record : data) {
			Map<Integer, List<ParseTreeNode>> priority = new TreeMap<>(Collections.reverseOrder());
			int currentPriority = 0;
			int i = 0;
			// Calculate the priority by the number of brackets before the calculation
			while (i < record.size()) {
				LexemeType nodeType = record.get(i).getLexeme().getType();
				switch (nodeType) {
				case LEFT_PAREN: currentPriority++; record.remove(i); break;
				case RIGHT_PAREN: currentPriority--; record.remove(i); break;
				default:
					if (nodeType == LexemeType.OPERATOR || nodeType == LexemeType.EQUALS) {
						if (priority.get(currentPriority) == null) priority.put(currentPriority, new ArrayList<ParseTreeNode>());
						priority.get(currentPriority).add(record.get(i));
					}
					i++;
				}
			}
			// Build the tree by the priority
			for (int j : priority.keySet()) {
				List<ParseTreeNode> operators = priority.get(j);
				for (int k = operators.size() - 1; k >= 0; k--) {
					ParseTreeNode operation = operators.get(k);
					int index = record.indexOf(operation);
					operation.setLeft(record.get(index - 1));
					operation.setRight(record.get(index + 1));
					// Replace 3 nodes in the List with one which is the root
					for (int l = 0; l < 3; l++) record.remove(index - 1);
					record.add(index - 1, operation);
				}
			}
			// Only the root left in each record
			res.add(record.get(0));
		}
		return res;
	}
	
}
