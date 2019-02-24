package edu.brandeis.cs12b.pa04.provided;

public class Point {
	public int x, y;
	
	/*
	 * NOTE: THE POINT CLASS IS FULLY IMPLEMENTED FOR YOU. YOU SHOULD NOT CHANGE IT!
	 */
	
	/**
	 * Create a new Point with a given X and Y coordinates
	 * @param x to put the point at
	 * @param y to put the point at
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Create a new point from a string of the form "(1,2)". Use this to parse
	 * points from user input.
	 * @param coordinates a string with coordinates for the new point
	 */
	public Point(String coordinates) {
		String[] splitCoordintes = coordinates.split("[(,)]");
		this.x = Integer.parseInt(splitCoordintes[1]);
		this.y = Integer.parseInt(splitCoordintes[2]);
	}
	
	/**
	 * Create a new point that is moved one tile in a given direction
	 * @param direction to move the tile
	 * @return returns a new point with the new coordinates
	 */
	public Point translate(String direction) {
		int newX = this.x;
		int newY = this.y;
		switch (direction){
			case "NORTH": newY--; break;
			case "SOUTH": newY++; break;
			case "EAST":  newX++; break;
			case "WEST":  newX--; break;
		}
		return new Point(newX,newY);
	}
	
	
	public String toString(){
		return "(" + this.x + "," + this.y + ")";
	}
}
