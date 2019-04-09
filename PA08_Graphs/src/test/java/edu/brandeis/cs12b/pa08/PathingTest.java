//Cameron Braunstein 2019
package edu.brandeis.cs12b.pa08;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import edu.brandeis.cs12b.pa08.Graph;

public class PathingTest {
	
	@Test
	public void testPathing(){
		Graph cycle = new Graph("graphs/cycle.json");
		Iterator<String> route = cycle.getRoute("d", "b").iterator();
		assertEquals("d",route.next());
		assertEquals("b",route.next());
		assertFalse(route.hasNext());

		Graph tree = new Graph("graphs/tree.json");
		route = tree.getRoute("a", "f").iterator();
		assertEquals("a",route.next());
		assertEquals("c", route.next());
		assertEquals("f", route.next());
		assertFalse(route.hasNext());

		Graph disconnected = new Graph("graphs/disconnected.json");
		assertEquals(null, disconnected.getRoute("a", "f"));
	}

}
