//Cameron Braunstein 2019
package edu.brandeis.cs12b.pa08;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import edu.brandeis.cs12b.pa08.Graph;

public class BasicTest {

	@Test
	public void testVertices() {
		
		Graph cycle = new Graph("graphs/cycle.json");
		Set<String> vertices = cycle.getVertices();
		assertTrue(vertices.contains("a"));
		assertTrue(vertices.contains("b"));
		assertTrue(vertices.contains("c"));
		assertTrue(vertices.contains("d"));
		assertEquals(4, vertices.size());
		
		Graph tree = new Graph("graphs/tree.json");
		vertices = tree.getVertices();
		assertTrue(vertices.contains("a"));
		assertTrue(vertices.contains("b"));
		assertTrue(vertices.contains("c"));
		assertTrue(vertices.contains("d"));
		assertTrue(vertices.contains("e"));
		assertTrue(vertices.contains("f"));
		assertEquals(6, vertices.size());
		
		Graph complex = new Graph("graphs/complex.json");
		vertices = complex.getVertices();
		assertTrue(vertices.contains("a"));
		assertTrue(vertices.contains("b"));
		assertTrue(vertices.contains("c"));
		assertTrue(vertices.contains("d"));
		assertTrue(vertices.contains("e"));
		assertTrue(vertices.contains("f"));
		assertTrue(vertices.contains("g"));
		assertTrue(vertices.contains("h"));
		assertEquals(8, vertices.size());
	}
	
	@Test
	public void testNeighbors(){
		Graph singleton = new Graph("graphs/singleton.json");
		Set<String> neighbors = singleton.getNeighbors("a");
		assertEquals(0, neighbors.size());
		
		Graph cycle = new Graph("graphs/cycle.json");
		neighbors = cycle.getNeighbors("b");
		assertTrue(neighbors.contains("a"));
		assertTrue(neighbors.contains("c"));
		assertTrue(neighbors.contains("d"));
		assertEquals(3, neighbors.size());
		
		Graph complex = new Graph("graphs/complex.json");
		neighbors = complex.getNeighbors("c");
		assertTrue(neighbors.contains("a"));
		assertTrue(neighbors.contains("f"));
		assertEquals(2, neighbors.size());
	}
	
	@Test
	public void testEdgeWeights() {
		Graph cycle = new Graph("graphs/cycle.json");
		assertEquals(10, cycle.getEdgeWeight("a", "c"));
		assertEquals(10, cycle.getEdgeWeight("c", "a"));
		assertEquals(-1,cycle.getEdgeWeight("a", "d"));
	}
	
	
	@Test
	public void testPathCost() {
		Graph complex = new Graph("graphs/complex.json");
		List<String> path= new LinkedList<String>();
		path.add("a");
		path.add("b");
		path.add("e");
		path.add("f");
		path.add("g");
		assertEquals(14,complex.getPathCost(path));
		
		Graph disconnected = new Graph("graphs/disconnected.json");
		path= new LinkedList<String>();
		path.add("a");
		path.add("b");
		path.add("d");
		path.add("f");
		assertEquals(-1,disconnected.getPathCost(path));
	}

}
