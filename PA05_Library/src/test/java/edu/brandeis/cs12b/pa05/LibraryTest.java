package edu.brandeis.cs12b.pa05;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.brandeis.cs12b.pa05.BookLocation;
import edu.brandeis.cs12b.pa05.Library;

public class LibraryTest {
	
	@Test
	public void testGetters() {
		Library l = new Library(1, 
				new int[] {1}, 
				new int[][]{new int[] {1}}, 
				new int[][][]{new int[][]{ new int[] {5}}});
		
		assertEquals(1, l.getNumberOfFloors());
		assertEquals(1, l.getCasesOnFloor(0));
		assertEquals(1, l.getShelvesInCase(0, 0));
		assertEquals(5, l.getCapacityOfShelf(0, 0, 0));
	}

	@Test
	public void testAddBooks() {
		Library l = new Library(1, 
				new int[] {1}, 
				new int[][]{new int[] {1}}, 
				new int[][][]{new int[][]{ new int[] {5}}});

		BookLocation la = l.addNewBook("Linear Algebra");
		BookLocation da = l.addNewBook("Distributed Algorithms");
		BookLocation aocp = l.addNewBook("Art of Computer Programming");
		BookLocation ai = l.addNewBook("A Modern Approach to Artificial Intelligence");
		BookLocation cod = l.addNewBook("Computer Organization and Design");
		BookLocation fdb = l.addNewBook("Fundamentals of Database Systems");


		assertTrue("room for book 1", la != null);
		assertTrue("room for book 2", da != null);
		assertTrue("room for book 3", aocp != null);
		assertTrue("room for book 4", ai != null);
		assertTrue("room for book 5", cod != null);

		assertEquals("no room for 6th book", null, fdb);
	}

	@Test
	public void testGetBooksAt() {
		Library l = new Library(1, 
				new int[] {1}, 
				new int[][]{new int[] {1}}, 
				new int[][][]{new int[][]{ new int[] {5}}});

		BookLocation la = l.addNewBook("Linear Algebra");
		BookLocation da = l.addNewBook("Distributed Algorithms");
		BookLocation aocp = l.addNewBook("Art of Computer Programming");
		BookLocation ai = l.addNewBook("A Modern Approach to Artificial Intelligence");
		BookLocation cod = l.addNewBook("Computer Organization and Design");
		BookLocation fdb = l.addNewBook("Fundamentals of Database Systems");


		assertTrue("room for book 1", la != null);
		assertTrue("room for book 2", da != null);
		assertTrue("room for book 3", aocp != null);
		assertTrue("room for book 4", ai != null);
		assertTrue("room for book 5", cod != null);

		assertEquals("no room for 6th book", null, fdb);

		assertEquals("5 books on the shelf", 5, l.getBooksAt(new BookLocation(0,0,0)).size());
	}

	@Test
	public void testBookLocations() {
		Library l = new Library(1, 
				new int[] {1}, 
				new int[][]{new int[] {2}}, 
				new int[][][]{new int[][]{ new int[] {3, 3}}});

		BookLocation la = l.addNewBook("Linear Algebra");
		BookLocation da = l.addNewBook("Distributed Algorithms");
		BookLocation aocp = l.addNewBook("Art of Computer Programming");
		BookLocation ai = l.addNewBook("A Modern Approach to Artificial Intelligence");
		BookLocation cod = l.addNewBook("Computer Organization and Design");
		BookLocation fdb = l.addNewBook("Fundamentals of Database Systems");


		assertTrue("room for book 1", la != null);
		assertTrue("room for book 2", da != null);
		assertTrue("room for book 3", aocp != null);
		assertTrue("room for book 4", ai != null);
		assertTrue("room for book 5", cod != null);
		assertTrue("room for book 5", fdb != null);


		assertTrue("Book where it says it is", l.getBooksAt(la).contains("Linear Algebra"));
		assertTrue("Book where it says it is", l.getBooksAt(da).contains("Distributed Algorithms"));
		assertTrue("Book where it says it is", l.getBooksAt(aocp).contains("Art of Computer Programming"));
		assertTrue("Book where it says it is", l.getBooksAt(ai).contains("A Modern Approach to Artificial Intelligence"));
		assertTrue("Book where it says it is", l.getBooksAt(cod).contains("Computer Organization and Design"));
		assertTrue("Book where it says it is", l.getBooksAt(fdb).contains("Fundamentals of Database Systems"));

	}

	@Test
	public void testCheckOut() {
		Library l = new Library(1, 
				new int[] {1}, 
				new int[][]{new int[] {2}}, 
				new int[][][]{new int[][]{ new int[] {3, 3}}});

		BookLocation la = l.addNewBook("Linear Algebra");
		BookLocation da = l.addNewBook("Distributed Algorithms");
		BookLocation aocp = l.addNewBook("Art of Computer Programming");
		BookLocation ai = l.addNewBook("A Modern Approach to Artificial Intelligence");
		BookLocation cod = l.addNewBook("Computer Organization and Design");
		BookLocation fdb = l.addNewBook("Fundamentals of Database Systems");


		assertTrue("room for book 1", la != null);
		assertTrue("room for book 2", da != null);
		assertTrue("room for book 3", aocp != null);
		assertTrue("room for book 4", ai != null);
		assertTrue("room for book 5", cod != null);
		assertTrue("room for book 5", fdb != null);

		l.checkOut("Computer Organization and Design");
		l.checkOut("Distributed Algorithms");
		l.checkOut("Fundamentals of Database Systems");
		l.checkIn("Computer Organization and Design");


		assertTrue("Book where it says it is", l.getBooksAt(la).contains("Linear Algebra"));
		assertFalse("Book checked out", l.getBooksAt(da).contains("Distributed Algorithms"));
		assertTrue("Book where it says it is", l.getBooksAt(aocp).contains("Art of Computer Programming"));
		assertTrue("Book where it says it is", l.getBooksAt(ai).contains("A Modern Approach to Artificial Intelligence"));
		assertTrue("Book where it says it is", l.getBooksAt(cod).contains("Computer Organization and Design"));
		assertFalse("book checked out", l.getBooksAt(fdb).contains("Fundamentals of Database Systems"));

		assertEquals("No room for new book", null, l.addNewBook("Algorithms"));

	}

	@Test
	public void testLargerLibrary() {
		Library l = new Library(1, 
				new int[] {1}, 
				new int[][]{new int[] {2}}, 
				new int[][][]{new int[][]{ new int[] {3, 3}}});

		BookLocation la = l.addNewBook("Linear Algebra");
		BookLocation da = l.addNewBook("Distributed Algorithms");
		BookLocation aocp = l.addNewBook("Art of Computer Programming");
		BookLocation ai = l.addNewBook("A Modern Approach to Artificial Intelligence");
		BookLocation cod = l.addNewBook("Computer Organization and Design");
		BookLocation fdb = l.addNewBook("Fundamentals of Database Systems");


		assertTrue("room for book 1", la != null);
		assertTrue("room for book 2", da != null);
		assertTrue("room for book 3", aocp != null);
		assertTrue("room for book 4", ai != null);
		assertTrue("room for book 5", cod != null);
		assertTrue("room for book 5", fdb != null);

		assertEquals("No room for new book", null, l.addNewBook("Algorithms"));


		assertEquals("book in correct place", la, l.getLocationOfBook("Linear Algebra"));
		assertEquals("book in correct place", cod, l.getLocationOfBook("Computer Organization and Design"));

	}


	@Test
	public void testSearchingBigLibrary() {
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
		
		for (String s : bookNames) {
			assertTrue("have book we added", l.getLocationOfBook(s) != null);
		}
		
		assertTrue("don't have book we didn't add", l.getLocationOfBook("other") == null);
	}

}
