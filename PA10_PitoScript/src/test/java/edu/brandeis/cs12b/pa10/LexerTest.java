package edu.brandeis.cs12b.pa10;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brandeis.cs12b.pa10.lexer.Lexeme;
import edu.brandeis.cs12b.pa10.lexer.LexemeType;
import edu.brandeis.cs12b.pa10.lexer.Lexer;

public class LexerTest {

	@Test
	public void lexerTest1() {
		String toTest = "x =   ( 1.2 + 2) * 6;";
		
		Lexer l = new Lexer(toTest);
		List<Lexeme> lexemes = toList(l);
		
		assertEquals(LexemeType.VARIABLE, lexemes.get(0).getType());
		assertEquals("x", lexemes.get(0).getValueAsString());
		assertEquals(LexemeType.EQUALS, lexemes.get(1).getType());
		assertEquals(LexemeType.LEFT_PAREN, lexemes.get(2).getType());
		assertEquals(LexemeType.NUMBER, lexemes.get(3).getType());
		assertEquals(1.2, lexemes.get(3).getValueAsNumber(), 0.01);
		assertEquals(LexemeType.OPERATOR, lexemes.get(4).getType());
		assertEquals(LexemeType.NUMBER, lexemes.get(5).getType());
		assertEquals(2.0, lexemes.get(5).getValueAsNumber(), 0.01);
		assertEquals(LexemeType.RIGHT_PAREN, lexemes.get(6).getType());
		assertEquals(LexemeType.OPERATOR, lexemes.get(7).getType());
		assertEquals(LexemeType.NUMBER, lexemes.get(8).getType());
		assertEquals(6.0, lexemes.get(8).getValueAsNumber(), 0.01);
		assertEquals(LexemeType.SEMICOLON, lexemes.get(9).getType());
	}

	@Test
	public void lexerTest2() {
		String toTest = "x = ?;\ny=x+2;";
		
		Lexer l = new Lexer(toTest);
		List<Lexeme> lexemes = toList(l);
		
		assertEquals(LexemeType.VARIABLE, lexemes.get(0).getType());
		assertEquals("x", lexemes.get(0).getValueAsString());
		assertEquals(LexemeType.EQUALS, lexemes.get(1).getType());
		assertEquals(LexemeType.USER_INPUT, lexemes.get(2).getType());
		assertEquals(LexemeType.SEMICOLON, lexemes.get(3).getType());
		assertEquals(LexemeType.VARIABLE, lexemes.get(4).getType());
		assertEquals(LexemeType.EQUALS, lexemes.get(5).getType());
		assertEquals(LexemeType.VARIABLE, lexemes.get(6).getType());
		assertEquals(LexemeType.OPERATOR, lexemes.get(7).getType());
		assertEquals(LexemeType.NUMBER, lexemes.get(8).getType());
		assertEquals(LexemeType.SEMICOLON, lexemes.get(9).getType());	
	}
	
	
	@Test
	public void lexerTest3() {
		String toTest = "myVar = ?;\ny=x+2;";
		
		Lexer l = new Lexer(toTest);
		List<Lexeme> lexemes = toList(l);
		
		assertEquals(LexemeType.VARIABLE, lexemes.get(0).getType());
		assertEquals("myVar", lexemes.get(0).getValueAsString());
		assertEquals(LexemeType.EQUALS, lexemes.get(1).getType());
		assertEquals(LexemeType.USER_INPUT, lexemes.get(2).getType());
		assertEquals(LexemeType.SEMICOLON, lexemes.get(3).getType());
		assertEquals(LexemeType.VARIABLE, lexemes.get(4).getType());
		assertEquals(LexemeType.EQUALS, lexemes.get(5).getType());
		assertEquals(LexemeType.VARIABLE, lexemes.get(6).getType());
		assertEquals(LexemeType.OPERATOR, lexemes.get(7).getType());
		assertEquals(LexemeType.NUMBER, lexemes.get(8).getType());
		assertEquals(LexemeType.SEMICOLON, lexemes.get(9).getType());
	}
	
	@Test
	public void lexerTest4() {
		String toTest = "myVar = ?;\ny=x+2.5643;";
		
		Lexer l = new Lexer(toTest);
		List<Lexeme> lexemes = toList(l);
		
		assertEquals(LexemeType.VARIABLE, lexemes.get(0).getType());
		assertEquals("myVar", lexemes.get(0).getValueAsString());
		assertEquals(LexemeType.EQUALS, lexemes.get(1).getType());
		assertEquals(LexemeType.USER_INPUT, lexemes.get(2).getType());
		assertEquals(LexemeType.SEMICOLON, lexemes.get(3).getType());
		assertEquals(LexemeType.VARIABLE, lexemes.get(4).getType());
		assertEquals(LexemeType.EQUALS, lexemes.get(5).getType());
		assertEquals(LexemeType.VARIABLE, lexemes.get(6).getType());
		assertEquals(LexemeType.OPERATOR, lexemes.get(7).getType());
		assertEquals(LexemeType.NUMBER, lexemes.get(8).getType());
		assertEquals(LexemeType.SEMICOLON, lexemes.get(9).getType());
	}
	
	private List<Lexeme> toList(Lexer l) {
		List<Lexeme> lexemes = new ArrayList<Lexeme>();
		l.forEachRemaining(lexemes::add);
		return lexemes;
	}

}
