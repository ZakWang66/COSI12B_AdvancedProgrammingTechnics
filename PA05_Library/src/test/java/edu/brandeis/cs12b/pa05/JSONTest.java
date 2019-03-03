package edu.brandeis.cs12b.pa05;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class JSONTest {

	@Test
	public void testSmallLibrary() {
		Library l = new Library(1, 
				new int[] {1}, 
				new int[][]{new int[] {1}}, 
				new int[][][]{new int[][]{ new int[] {5}}});

		BookLocation la = l.addNewBook("Linear Algebra");
		l.addNewBook("Distributed Algorithms");
		l.addNewBook("Art of Computer Programming");
		l.addNewBook("A Modern Approach to Artificial Intelligence");
		BookLocation cod = l.addNewBook("Computer Organization and Design");
		l.addNewBook("Fundamentals of Database Systems");

		
		l.writeToFile(new File("test.json"));
		
		Library l2 = Library.makeLibraryFromFile(new File("test.json"));

		assertEquals(1, l2.getNumberOfFloors());
		assertEquals(1, l2.getCasesOnFloor(0));
		assertEquals(5, l2.getCapacityOfShelf(0, 0, 0));
		
		assertTrue(l2.getBooksAt(la).contains("Linear Algebra"));
		assertTrue(l2.getBooksAt(cod).contains("Computer Organization and Design"));
		assertFalse(l2.getBooksAt(cod).contains("Fundamentals of Database Systems"));


	}
	
	@Test
	public void testBigLibrary() {
		Library l = new Library(3, 
			new int[] {2, 2, 2}, 
			new int[][]{new int[] {2, 2, 2}, 
			            new int[] {2, 2, 2}, 
			            new int[] {2, 2, 2}
			}, 
			new int[][][]{ new int[][]{ 
				new int[] {5, 5},
				new int[] {5, 5}
			},
			new int[][]{ 
				new int[] {5, 5},
				new int[] {5, 5}
			},
			new int[][]{ 
				new int[] {5, 5},
				new int[] {5, 5}
			}
		});

		
		
		Set<String> bookNames = new HashSet<String>();
		
		for (int i = 0; i < 60; i++) {
			bookNames.add("book " + i);
		}
		
		bookNames.forEach(l::addNewBook);
		
		assertTrue("no more room", l.addNewBook("test") == null);
		
		l.writeToFile(new File("test.json"));
		Library l2 = Library.makeLibraryFromFile(new File("test.json"));
		
		for (String s : bookNames) {
			assertTrue("have book we added", l2.getLocationOfBook(s) != null);
		}
		
		assertTrue("don't have book we didn't add", l2.getLocationOfBook("other") == null);
	}

}
