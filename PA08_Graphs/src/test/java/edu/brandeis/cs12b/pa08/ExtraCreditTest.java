//Cameron Braunstein 2019
package edu.brandeis.cs12b.pa08;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import org.junit.Test;

import edu.brandeis.cs12b.pa08.Graph;

public class ExtraCreditTest {
	@Test
	public void testShortestRoute() {
		Graph cycle = new Graph("graphs/cycle.json");
		Iterator<String> route = cycle.getShortestRoute("a", "b").iterator();
		assertEquals("a",route.next());
		assertEquals("c",route.next());
		assertEquals("b",route.next());
		assertFalse(route.hasNext());
		
		Graph complex = new Graph("graphs/complex.json");
		route = complex.getShortestRoute("a", "g").iterator();
		assertEquals("a",route.next());
		assertEquals("b",route.next());
		assertEquals("e",route.next());
		assertEquals("f",route.next());
		assertEquals("g",route.next());
		assertFalse(route.hasNext());
	}

}