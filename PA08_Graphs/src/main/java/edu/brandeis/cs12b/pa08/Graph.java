package edu.brandeis.cs12b.pa08;

import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;;

public class Graph {
    private Set<String> vertices;
    private Map<String, Integer> edges;
    private Map<String, Set<String>> neighbors;
    
	/**
	 * Build the graph from a json file
	 * @param the json filename
	 */
    public Graph(String filename) {
    	vertices = new HashSet<String>();
    	edges = new HashMap<String, Integer>();
    	neighbors = new HashMap<String, Set<String>>();
    	try {
    		JSONParser parser = new JSONParser();
    		JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(filename));
    		JSONArray vertices = (JSONArray)jsonObject.get("vertices");
    		for (Object v : vertices) {
    			this.vertices.add((String) v);
    			neighbors.put((String)v, new HashSet<String>());
    		}
    		JSONArray edges = (JSONArray)jsonObject.get("edges");
    		for (Object e : edges) {
    			int weight = Integer.parseInt((String)((JSONObject)e).get("weight"));
    			String v1 = (String)((JSONObject)e).get("v1");
    			String v2 = (String)((JSONObject)e).get("v2");
    			this.edges.put(v1 + " " + v2, weight);
    			this.edges.put(v2 + " " + v1, weight);
    			neighbors.get(v1).add(v2);
    			neighbors.get(v2).add(v1);
    		}
    	}
    	catch (Exception exception) {}
    }
    
	/**
	 * Returns a Set of all vertices in the graph. 
	 * @return the Set of vertices.
	 */
	public Set<String> getVertices() {
		return vertices;
	}

	/**
	 * Return a Set of Vertices that are directly connected to vertex v
	 * @param v the vertex
	 * @return A Set that contains all the vertices that are connected to v
	 */
	public Set<String> getNeighbors(String v) {
		return neighbors.get(v);
	}
	
	/**
	 * Get the weight of the edge between vertex a and vertex b.
	 * 
	 * @param a one vertex
	 * @param b another vertex 
	 * @return the edge weight or -1 if the edge doesn't exist in the graph.
	 */
	public int getEdgeWeight(String a, String b) {
		Integer weight = edges.get(a + " " + b);
		return weight == null ? -1 : weight;
	}

	/**
	 * Gets the cost of the path (sum of the edge weights)
	 * represented by the passed ArrayList of vertices. 
	 * If the path is invalid, (jumps between non-connected vertices or contains a vertex that doesn't exist)
	 * return -1
	 * @param path a List of vertices
	 * @return the path length
	 */
	public int getPathCost(List<String> path) {
		if (path.size() == 1 || path.size() == 0)
			return 0;
		int cost = 0;
		for (int i = 1; i < path.size(); i++) {
			Integer weight = edges.get(path.get(i) + " " + path.get(i - 1));
			if (weight == null)
				return -1;
			cost += weight;
		}
		return cost;
	}

	/**
	 * @param start the name of the first vertex in the route
	 * @param end the name of the last vertex in the route
	 * @return Any route from start to end. If no route exists, return null
	 */
	public List<String> getRoute(String start, String end){
		return getShortestRoute(start, end);
	}
	
	/**
	 * Determines whether the graph is cyclic iteratively
	 * @param v - the vertex currently visiting
	 * @param visited - visited vertices
	 * @param parent - the parent vertex of v
	 * @return whether the graph is cyclic or not
	 */
	private boolean isCyclicUtil(String v, Set<String> visited, String parent) {
		visited.add(v);
		Set<String> neighbors = this.neighbors.get(v);
		for (String n: neighbors) {
			if (!visited.contains(n)) {
				if (isCyclicUtil(n, visited, v)) {
					return true;
				}
			}
			else if (!n.equals(parent)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines if the graph is a tree or not. 
	 * * @return true if the graph is a tree and false if not.
	 */
	public boolean isTree() {
		if (vertices.size() == 0 || vertices.size() == 1) return true;
		Set<String> visited = new HashSet<String>();
		return !isCyclicUtil(vertices.iterator().next(), visited, null) && visited.size() == vertices.size();
	}
	
	/**
	 * Wrap a vertex with its name, distance to the starting point and route to the starting point together
	 * distance == -1 represent its distance has never been calculated and is set to positive infinity.
	 * Comparable is implemented for using the PriorityQueue
	 */
	private class Vertex implements Comparable<Vertex>{
		public String name;
		public int distance;
		public List<String> route;
		
		Vertex(String name, int distance) {
			this.name = name;
			this.distance = distance;
			route = new LinkedList<String>();
		}
		
		public int compareTo(Vertex o) {
			if (o.distance == -1 && distance == -1) return 0;
			if (o.distance == -1) return -1;
			if (distance == -1) return 1;
			return distance - o.distance;
		}
	}
	
	/**
	 * Use Dijkstra's algorithm to find the shortest path from start to end after the initializations
	 * @param starting - the starting Vertex object that has been initialized
	 * @param end - the destination vertex
	 * @param verticesWithDistance - the map for quick search the Vertex object by its name string
	 * @return the route from start to end
	 */
	private List<String> searchByDijkstra(Vertex starting, String end ,Map<String, Vertex> verticesWithDistance) {
		Set<Vertex> visited = new HashSet<Vertex>();
		PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();
		pq.offer(starting);
		while (!pq.isEmpty()) {
			Vertex v = pq.poll();
			if (v.name.equals(end))
				return v.route;
			if (!visited.contains(v))
				visited.add(v);
			else
				continue;
			for (String n : neighbors.get(v.name)) {
				int newDistance = v.distance + edges.get(v.name + " " + n);
				Vertex nextVertex = verticesWithDistance.get(n);
				if (nextVertex.distance == -1 || newDistance < nextVertex.distance) {
					nextVertex.distance = newDistance;
					nextVertex.route = new LinkedList<String>(v.route);
					nextVertex.route.add(nextVertex.name);
				}
				pq.offer(nextVertex);
			}
		}
		return null;
	}
	
	/**
	 * Use Dijkstra's algorithm to find the shortest path from start to end
	 * @param start the name of the first vertex in the route
	 * @param end the name of the last vertex in the route
	 * @return the shortest route from start to end. If no route exists, return null
	 */
	public List<String> getShortestRoute(String start, String end) {
		if (!vertices.contains(start)) return null;
		Map<String, Vertex> verticesWithDistance = new HashMap<String, Vertex>();
		Vertex starting = new Vertex(start, 0);
		verticesWithDistance.put(start, starting);
		starting.route.add(starting.name);
		for (String v : vertices) {
			if (!v.equals(start))
				verticesWithDistance.put(v, new Vertex(v, -1));
		}
		return searchByDijkstra(starting, end , verticesWithDistance);
	}
	
}
