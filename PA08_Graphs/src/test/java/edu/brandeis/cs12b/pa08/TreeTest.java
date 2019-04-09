//Cameron Braunstein 2019
package edu.brandeis.cs12b.pa08;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.brandeis.cs12b.pa08.Graph;

public class TreeTest {

	@Test
	public void testTree(){
		Graph cycle = new Graph("graphs/cycle.json");
		assertFalse(cycle.isTree());

		Graph tree = new Graph("graphs/tree.json");
		assertTrue(tree.isTree());

		Graph disconnected = new Graph("graphs/disconnected.json");
		assertFalse(disconnected.isTree());
		
		
	}

}
