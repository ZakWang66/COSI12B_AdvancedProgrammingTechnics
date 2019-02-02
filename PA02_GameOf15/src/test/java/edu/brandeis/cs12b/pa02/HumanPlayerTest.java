package edu.brandeis.cs12b.pa02;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.brandeis.cs12b.pa02.provided.GameOf15;

public class HumanPlayerTest {

	private static final String newLine = System.getProperty("line.separator");
	
	private InputStream sysIn;
	private PrintStream sysOut;

	private ByteArrayOutputStream outContent;

	@Before
	public void setUp() throws Exception {
		sysOut = System.out;
		sysIn = System.in;

		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void tearDown() throws Exception {
		System.setIn(sysIn);
		System.setOut(sysOut);
	}

	@Test
	public void test() {
		GameOf15 game = new GameOf15( new int[][] 
				{{1, 2, 3, 4},
				{5, 6, 7, 8},
				{9, 10, 11, 12},
				{13, 14, 0, 15}});
		
		
		String inputString = "u" + newLine + "x" + newLine + "l" + newLine;
		System.setIn(new ByteArrayInputStream(inputString.getBytes()));

		HumanPlayer hp = new HumanPlayer();
		hp.play(game);
		
		/*
		 * This is the expected output for this game provided with inputString user interaction 
		 * (i.e. when prompted, the user enters u, then x, then l.
		 * 
		 * 
		 * 1		2	3	4	
		 * 5		6	7	8	
		 * 9		10	11	12	
		 * 13	14	 	15	
		 * 
		 * Enter a move: (l)eft, (u)p, (r)ight, (d)own, or (exit): 
		 * No move was made. Try again.
		 * 1		2	3	4	
		 * 5		6	7	8	
		 * 9		10	11	12	
		 * 13	14	 	15	
		 * 
		 * Enter a move: (l)eft, (u)p, (r)ight, (d)own, or (exit): 
		 * Invalid input. Try again.
		 * 1		2	3	4	
		 * 5		6	7	8	
		 * 9		10	11	12	
		 * 13	14	 	15	
		 * 
		 * Enter a move: (l)eft, (u)p, (r)ight, (d)own, or (exit): 
		 * 1		2	3	4	
		 * 5		6	7	8	
		 * 9		10	11	12	
		 * 13	14	15	 	
		 * 
		 * You win!
		 */
		
		String expectedOutput = 
				"1\t2\t3\t4\t\n5\t6\t7\t8\t\n9\t10\t11\t12\t\n13\t14\t \t15\t\n" + newLine
				+ "Enter a move: (l)eft, (u)p, (r)ight, (d)own, or (exit): "
				+ "No move was made. Try again." + newLine
				+ "1\t2\t3\t4\t\n5\t6\t7\t8\t\n9\t10\t11\t12\t\n13\t14\t \t15\t\n" + newLine
				+ "Enter a move: (l)eft, (u)p, (r)ight, (d)own, or (exit): "
				+ "Invalid input. Try again." + newLine
				+ "1\t2\t3\t4\t\n5\t6\t7\t8\t\n9\t10\t11\t12\t\n13\t14\t \t15\t\n" + newLine
				+ "Enter a move: (l)eft, (u)p, (r)ight, (d)own, or (exit): "
				+ "1\t2\t3\t4\t\n5\t6\t7\t8\t\n9\t10\t11\t12\t\n13\t14\t15\t \t\n" + newLine
				+ "You win!";
		
		
		String out = outContent.toString();		
		assertTrue(out.equals(expectedOutput));
	}
}
