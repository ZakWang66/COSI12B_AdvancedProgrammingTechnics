package edu.brandeis.cs12b.pa02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.brandeis.cs12b.pa02.ComputerPlayer;
import edu.brandeis.cs12b.pa02.provided.GameOf15;

public class ComputerPlayerTest {

	private PrintStream sysOut;
	private ByteArrayOutputStream outContent;
	private static final String newLine = System.getProperty("line.separator");

	
	@Before
	public void setUp() throws Exception {
		sysOut = System.out;
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(sysOut);
	}
	
	@Test
	public void test() {
		GameOf15 game = new GameOf15( new int[][] 
				{{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 10, 12, 0},
				{13, 14, 11, 15}});
		
		ComputerPlayer comp = new ComputerPlayer();
		game = comp.solveRelaxed(game);
		
		String expectedOutput = "u" + newLine;
		
		assertEquals(game.getValue(GameOf15.NUM_ROWS - 1, GameOf15.NUM_COLS - 1), 0);
		String a = outContent.toString();
		assertTrue(outContent.toString().equals(expectedOutput));
			
	}
	
	@Test
	public void testReal() {
		GameOf15 game = new GameOf15( new int[][] 
				{{1, 2, 3, 4},
				{5, 6, 8, 12},
				{9, 0, 10, 7},
				{13, 14, 11, 15}});
		
		ComputerPlayer comp = new ComputerPlayer();
		game = comp.solveReal(game);
		
		String expectedOutput = "l" + newLine +
								"l" + newLine +
								"d" + newLine +
								"r" + newLine +
								"u" + newLine +
								"u" + newLine +
								"l" + newLine;
		
		assertEquals(game.getValue(GameOf15.NUM_ROWS - 1, GameOf15.NUM_COLS - 1), 0);
		String a = outContent.toString();
		assertTrue(outContent.toString().equals(expectedOutput));
	}
}
