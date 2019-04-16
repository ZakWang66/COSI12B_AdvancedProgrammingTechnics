package edu.brandeis.cs12b.pa10;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.brandeis.cs12b.pa10.compiler.Compiler;
import edu.brandeis.cs12b.pa10.lexer.Lexeme;
import edu.brandeis.cs12b.pa10.lexer.LexemeType;
import edu.brandeis.cs12b.pa10.parser.ParseTreeNode;
import edu.brandeis.cs12b.vm.VM;

public class CompilerTest {
	
	@Test
	public void test1() {
		// "x = 5 + 6;"
		List<ParseTreeNode> l = Arrays.asList(new ParseTreeNode[] {
			newPTN(new Lexeme(LexemeType.EQUALS, null),
				newPTN(new Lexeme(LexemeType.VARIABLE, "x")),
				newPTN(new Lexeme(LexemeType.OPERATOR, "+"),
					newPTN(new Lexeme(LexemeType.NUMBER, "5")),
					newPTN(new Lexeme(LexemeType.NUMBER, "6"))))});
		Compiler c = new Compiler(l);
		VM vm = new VM();
		
		
		Map<String, Double> values = vm.evaluate(c.compile());
		
		assertEquals(11.0, values.get("x"), 0.01);
	}
	
	@Test
	public void test2() {
		// "x = (5 - 4) + (6 * 4);"
		List<ParseTreeNode> l = Arrays.asList(new ParseTreeNode[] {
			newPTN(new Lexeme(LexemeType.EQUALS, null),
				newPTN(new Lexeme(LexemeType.VARIABLE, "x")),
				newPTN(new Lexeme(LexemeType.OPERATOR, "+"),
					newPTN(new Lexeme(LexemeType.OPERATOR, "-"),
						newPTN(new Lexeme(LexemeType.NUMBER, "5")),
						newPTN(new Lexeme(LexemeType.NUMBER, "4"))),
					newPTN(new Lexeme(LexemeType.OPERATOR, "*"),
						newPTN(new Lexeme(LexemeType.NUMBER, "6")),
						newPTN(new Lexeme(LexemeType.NUMBER, "4")))))});
		Compiler c = new Compiler(l);
		VM vm = new VM();
		
		
		Map<String, Double> values = vm.evaluate(c.compile());
		
		assertEquals(25.0, values.get("x"), 0.01);
	}
	
	@Test
	public void test3() {
		// "x = (5 - 4) + (6 * 4); y = x * 4; z = x + (y * (2^2));"
		List<ParseTreeNode> l = Arrays.asList(new ParseTreeNode[] {
			newPTN(new Lexeme(LexemeType.EQUALS, null),
				newPTN(new Lexeme(LexemeType.VARIABLE, "x")),
				newPTN(new Lexeme(LexemeType.OPERATOR, "+"),
					newPTN(new Lexeme(LexemeType.OPERATOR, "-"),
						newPTN(new Lexeme(LexemeType.NUMBER, "5")),
						newPTN(new Lexeme(LexemeType.NUMBER, "4"))),
					newPTN(new Lexeme(LexemeType.OPERATOR, "*"),
						newPTN(new Lexeme(LexemeType.NUMBER, "6")),
						newPTN(new Lexeme(LexemeType.NUMBER, "4"))))),
			newPTN(new Lexeme(LexemeType.EQUALS, null),
				newPTN(new Lexeme(LexemeType.VARIABLE, "y")),
				newPTN(new Lexeme(LexemeType.OPERATOR, "*"),
					newPTN(new Lexeme(LexemeType.VARIABLE, "x")),
					newPTN(new Lexeme(LexemeType.NUMBER, "4")))),
			newPTN(new Lexeme(LexemeType.EQUALS, null),
				newPTN(new Lexeme(LexemeType.VARIABLE, "z")),
				newPTN(new Lexeme(LexemeType.OPERATOR, "+"),
					newPTN(new Lexeme(LexemeType.VARIABLE, "x")),
					newPTN(new Lexeme(LexemeType.OPERATOR, "*"),
						newPTN(new Lexeme(LexemeType.VARIABLE, "y")),
						newPTN(new Lexeme(LexemeType.OPERATOR, "^"),
							newPTN(new Lexeme(LexemeType.NUMBER, "2")),
							newPTN(new Lexeme(LexemeType.NUMBER, "2"))))))});
		Compiler c = new Compiler(l);
		VM vm = new VM();
		
		
		Map<String, Double> values = vm.evaluate(c.compile());
		
		assertEquals(25.0, values.get("x"), 0.01);
		assertEquals(100.0, values.get("y"), 0.01);
		assertEquals(425.0, values.get("z"), 0.01);
	}
	
	@Test
	public void test4() {	
		InputStream sysin = System.in;
		PrintStream sysout = System.out;
		System.setIn(new ByteArrayInputStream("6\n".getBytes()));
		System.setOut(new PrintStream(new ByteArrayOutputStream()));
		
		// "x = ? + 5;"
		List<ParseTreeNode> l = Arrays.asList(new ParseTreeNode[] {
			newPTN(new Lexeme(LexemeType.EQUALS, null),
				newPTN(new Lexeme(LexemeType.VARIABLE, "x")),
				newPTN(new Lexeme(LexemeType.OPERATOR, "+"),
					newPTN(new Lexeme(LexemeType.USER_INPUT, null)),
					newPTN(new Lexeme(LexemeType.NUMBER, "5"))))});
		Compiler c = new Compiler(l);
		VM vm = new VM();
		
		Map<String, Double> values = vm.evaluate(c.compile());
		
		System.setIn(sysin);
		System.setOut(sysout);
		
		assertEquals(11.0, values.get("x"), 0.01);
	}
	
	private ParseTreeNode newPTN(Lexeme lexeme, ParseTreeNode left, ParseTreeNode right) {
		ParseTreeNode p = newPTN(lexeme);
		p.setLeft(left); p.setRight(right);
		return p;
	}

	private ParseTreeNode newPTN(Lexeme lexeme) {
		return new ParseTreeNode(lexeme);
	}
}
