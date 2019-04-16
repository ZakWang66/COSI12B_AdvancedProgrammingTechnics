package edu.brandeis.cs12b.pa10;

import static org.junit.Assert.assertEquals;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import edu.brandeis.cs12b.pa10.lexer.Lexeme;
import edu.brandeis.cs12b.pa10.lexer.LexemeType;
import edu.brandeis.cs12b.pa10.lexer.Lexer;
import edu.brandeis.cs12b.pa10.parser.ParseTreeNode;
import edu.brandeis.cs12b.pa10.parser.Parser;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;

public class ParserTest {
	
	/**
	 * 
	 * Similar to FSM's GraphViz problem, this method will let you generate
	 * a GraphViz representation of your parse tree like our diagrams in the
	 * assignment PDF. Simply enter the PitoScript program for which you wish to
	 * see a parse tree, uncomment the "@Test", and run the unit tests.
	 */
	// @Test
	public void generateParseTreePNG() throws IOException {
		String pitoScript = "YOUR PITOSCRIPT HERE";
		createGraphic(pitoScript, "test");
	}
	
	@Test
	public void test1() {
		// "x = 5 + 6;"
		Iterator<Lexeme> i = Arrays.asList(new Lexeme[] {
			new Lexeme(LexemeType.VARIABLE, "x"),
			new Lexeme(LexemeType.EQUALS, null),
			new Lexeme(LexemeType.NUMBER, "5"),
			new Lexeme(LexemeType.OPERATOR, "+"),
			new Lexeme(LexemeType.NUMBER, "6"),
			new Lexeme(LexemeType.SEMICOLON, null)}).iterator();
		Parser p = new Parser(i);
		
		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);

		assertEquals(1, nodes.size());
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("x", root.getLeft().getLexeme().getValueAsString());

		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("+", root.getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLexeme().getType());
		assertEquals(5.0, root.getRight().getLeft().getLexeme().getValueAsNumber(), 0.01);
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLexeme().getType());
		assertEquals(6.0, root.getRight().getRight().getLexeme().getValueAsNumber(), 0.01);

	
	}
	
	@Test
	public void test2() {
		// "x = 5 + (6 *   2);"
		Iterator<Lexeme> i = Arrays.asList(new Lexeme[] {
			new Lexeme(LexemeType.VARIABLE, "x"),
			new Lexeme(LexemeType.EQUALS, null),
			new Lexeme(LexemeType.NUMBER, "5"),
			new Lexeme(LexemeType.OPERATOR, "+"),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "6"),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.NUMBER, "2"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.SEMICOLON, null)}).iterator();
		Parser p = new Parser(i);
		
		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);

		assertEquals(1, nodes.size());
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("x", root.getLeft().getLexeme().getValueAsString());

		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLexeme().getType());
		assertEquals(5.0, root.getRight().getLeft().getLexeme().getValueAsNumber(), 0.01);
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLexeme().getType());

		
	}
		
	@Test
	public void test3() {
		// "x = 5 + (6 *   2); y = 5 + 6;"
		Iterator<Lexeme> i = Arrays.asList(new Lexeme[] {
			new Lexeme(LexemeType.VARIABLE, "x"),
			new Lexeme(LexemeType.EQUALS, null),
			new Lexeme(LexemeType.NUMBER, "5"),
			new Lexeme(LexemeType.OPERATOR, "+"),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "6"),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.NUMBER, "2"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.SEMICOLON, null),
			new Lexeme(LexemeType.VARIABLE, "y"),
			new Lexeme(LexemeType.EQUALS, null),
			new Lexeme(LexemeType.NUMBER, "5"),
			new Lexeme(LexemeType.OPERATOR, "+"),
			new Lexeme(LexemeType.NUMBER, "6"),
			new Lexeme(LexemeType.SEMICOLON, null)}).iterator();
		Parser p = new Parser(i);
		
		List<ParseTreeNode> nodes = p.parse();

		assertEquals(2, nodes.size());
		
		ParseTreeNode root = nodes.get(0);
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("x", root.getLeft().getLexeme().getValueAsString());

		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLexeme().getType());
		assertEquals(5.0, root.getRight().getLeft().getLexeme().getValueAsNumber(), 0.01);
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLexeme().getType());

		
		root = nodes.get(1);
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("y", root.getLeft().getLexeme().getValueAsString());

		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("+", root.getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLexeme().getType());
		assertEquals(5.0, root.getRight().getLeft().getLexeme().getValueAsNumber(), 0.01);
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLexeme().getType());
		assertEquals(6.0, root.getRight().getRight().getLexeme().getValueAsNumber(), 0.01);
	
	}
	
	

	@Test
	public void test4() {
		// "l = (5 + (6 - 3)) * ((2 / 4) * 4);"
		Iterator<Lexeme> i = Arrays.asList(new Lexeme[] {
			new Lexeme(LexemeType.VARIABLE, "l"),
			new Lexeme(LexemeType.EQUALS, null),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "5"),
			new Lexeme(LexemeType.OPERATOR, "+"),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "6"),
			new Lexeme(LexemeType.OPERATOR, "-"),
			new Lexeme(LexemeType.NUMBER, "3"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "2"),
			new Lexeme(LexemeType.OPERATOR, "/"),
			new Lexeme(LexemeType.NUMBER, "4"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.NUMBER, "4"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.SEMICOLON, null)}).iterator();
		Parser p = new Parser(i);
		
		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);

		assertEquals(1, nodes.size());
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("l", root.getLeft().getLexeme().getValueAsString());

		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLexeme().getValueAsString());
		
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLeft().getLexeme().getType());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getRight().getLexeme().getType());


		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLexeme().getType());

		
	}
	
	@Test
	public void test5() {
		// "l = (5 / (3^2)) * ((8 * 2) / (4^4));"
		Iterator<Lexeme> i = Arrays.asList(new Lexeme[] {
			new Lexeme(LexemeType.VARIABLE, "l"),
			new Lexeme(LexemeType.EQUALS, null),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "5"),
			new Lexeme(LexemeType.OPERATOR, "/"),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "3"),
			new Lexeme(LexemeType.OPERATOR, "^"),
			new Lexeme(LexemeType.NUMBER, "2"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "8"),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.NUMBER, "2"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.OPERATOR, "/"),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "4"),
			new Lexeme(LexemeType.OPERATOR, "^"),
			new Lexeme(LexemeType.NUMBER, "4"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.SEMICOLON, null)}).iterator();
		Parser p = new Parser(i);
		
		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);

		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(null, root.getLexeme().getValueAsString());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("l", root.getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getLexeme().getType());
		assertEquals("/", root.getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLeft().getLexeme().getType());
		assertEquals("5", root.getRight().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getRight().getLexeme().getType());
		assertEquals("^", root.getRight().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getRight().getLeft().getLexeme().getType());
		assertEquals("3", root.getRight().getLeft().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getRight().getRight().getLexeme().getType());
		assertEquals("2", root.getRight().getLeft().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals("/", root.getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("*", root.getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLeft().getLexeme().getType());
		assertEquals("8", root.getRight().getRight().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getRight().getLexeme().getType());
		assertEquals("2", root.getRight().getRight().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getRight().getLexeme().getType());
		assertEquals("^", root.getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("4", root.getRight().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("4", root.getRight().getRight().getRight().getRight().getLexeme().getValueAsString());

		
	}
	
	@Test
	public void test6() {
		// "l = ((5*5*5) / (3^(2*1))) * (((8/2) * 2) / (4^4));"
		Iterator<Lexeme> i = Arrays.asList(new Lexeme[] {
			new Lexeme(LexemeType.VARIABLE, "l"),
			new Lexeme(LexemeType.EQUALS, null),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "5"),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.NUMBER, "5"),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.NUMBER, "5"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.OPERATOR, "/"),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "3"),
			new Lexeme(LexemeType.OPERATOR, "^"),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "2"),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.NUMBER, "1"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "8"),
			new Lexeme(LexemeType.OPERATOR, "/"),
			new Lexeme(LexemeType.NUMBER, "2"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.NUMBER, "2"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.OPERATOR, "/"),
			new Lexeme(LexemeType.LEFT_PAREN, null),
			new Lexeme(LexemeType.NUMBER, "4"),
			new Lexeme(LexemeType.OPERATOR, "^"),
			new Lexeme(LexemeType.NUMBER, "4"),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.RIGHT_PAREN, null),
			new Lexeme(LexemeType.SEMICOLON, null)}).iterator();
		Parser p = new Parser(i);
		
		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);
		
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(null, root.getLexeme().getValueAsString());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("l", root.getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getLexeme().getType());
		assertEquals("/", root.getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getLeft().getLexeme().getType());
		assertEquals("*", root.getRight().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLeft().getLeft().getLexeme().getType());
		assertEquals("5", root.getRight().getLeft().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getLeft().getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLeft().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLeft().getRight().getLeft().getLexeme().getType());
		assertEquals("5", root.getRight().getLeft().getLeft().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLeft().getRight().getRight().getLexeme().getType());
		assertEquals("5", root.getRight().getLeft().getLeft().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getRight().getLexeme().getType());
		assertEquals("^", root.getRight().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getRight().getLeft().getLexeme().getType());
		assertEquals("3", root.getRight().getLeft().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLeft().getRight().getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLeft().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("2", root.getRight().getLeft().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("1", root.getRight().getLeft().getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals("/", root.getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("*", root.getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLeft().getLeft().getLexeme().getType());
		assertEquals("/", root.getRight().getRight().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLeft().getLeft().getLexeme().getType());
		assertEquals("8", root.getRight().getRight().getLeft().getLeft().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLeft().getRight().getLexeme().getType());
		assertEquals("2", root.getRight().getRight().getLeft().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getRight().getLexeme().getType());
		assertEquals("2", root.getRight().getRight().getLeft().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getRight().getLexeme().getType());
		assertEquals("^", root.getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("4", root.getRight().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("4", root.getRight().getRight().getRight().getRight().getLexeme().getValueAsString());
	}
	
	
	@Test
	public void test7() {
		// "myVar = 5 * 6 - 3 + 2 / 8 ^ 2;"
		Iterator<Lexeme> i = Arrays.asList(new Lexeme[] {
			new Lexeme(LexemeType.VARIABLE, "myVar"),
			new Lexeme(LexemeType.EQUALS, null),
			new Lexeme(LexemeType.NUMBER, "5"),
			new Lexeme(LexemeType.OPERATOR, "*"),
			new Lexeme(LexemeType.NUMBER, "6"),
			new Lexeme(LexemeType.OPERATOR, "-"),
			new Lexeme(LexemeType.NUMBER, "3"),
			new Lexeme(LexemeType.OPERATOR, "+"),
			new Lexeme(LexemeType.NUMBER, "2"),
			new Lexeme(LexemeType.OPERATOR, "/"),
			new Lexeme(LexemeType.NUMBER, "8"),
			new Lexeme(LexemeType.OPERATOR, "^"),
			new Lexeme(LexemeType.NUMBER, "2"),
			new Lexeme(LexemeType.SEMICOLON, null)}).iterator();
		Parser p = new Parser(i);
		
		List<ParseTreeNode> nodes = p.parse();
		ParseTreeNode root = nodes.get(0);

		
		assertEquals(LexemeType.EQUALS, root.getLexeme().getType());
		assertEquals(null, root.getLexeme().getValueAsString());
		assertEquals(LexemeType.VARIABLE, root.getLeft().getLexeme().getType());
		assertEquals("myVar", root.getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getLexeme().getType());
		assertEquals("*", root.getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getLeft().getLexeme().getType());
		assertEquals("5", root.getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getLexeme().getType());
		assertEquals("-", root.getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("6", root.getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getRight().getLexeme().getType());
		assertEquals("+", root.getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("3", root.getRight().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("/", root.getRight().getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("2", root.getRight().getRight().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.OPERATOR, root.getRight().getRight().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("^", root.getRight().getRight().getRight().getRight().getRight().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getRight().getRight().getLeft().getLexeme().getType());
		assertEquals("8", root.getRight().getRight().getRight().getRight().getRight().getLeft().getLexeme().getValueAsString());
		assertEquals(LexemeType.NUMBER, root.getRight().getRight().getRight().getRight().getRight().getRight().getLexeme().getType());
		assertEquals("2", root.getRight().getRight().getRight().getRight().getRight().getRight().getLexeme().getValueAsString());

	}
	
	private static void createGraphic(String pitoScript, String filename) throws IOException {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.OFF);
		System.out.println("Creating ParseTree graphic for: " + pitoScript + "...");
		
		Lexer l = new Lexer(pitoScript);
		Parser p = new Parser(l);
		List<ParseTreeNode> roots = p.parse();
		
		for(int i = 0; i < roots.size(); i++) {
			createGraphicForRoot(roots.get(i), filename, i, roots.size());
		}
		
		System.out.println("Created ParseTree graphic for: " + pitoScript);
	}
	
	private static void createGraphicForRoot(ParseTreeNode root, String filename, int index, int total) throws IOException {
		String parseTree = root.toString();
		
		if(total > 1) filename += "_page" + (index+1);
		
		File outputPNG = new File(filename + ".png");
		
		MutableGraph g = guru.nidi.graphviz.parse.Parser.read(parseTree);
		Graphviz.fromGraph(g).render(Format.PNG).toFile(outputPNG);
		
		if(total > 1) System.out.println("Created page " + (index+1) + " of " + total);
		
		if(Desktop.isDesktopSupported()) Desktop.getDesktop().open(outputPNG);
	}


}
