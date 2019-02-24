package edu.brandeis.cs12b.pa04.provided;

import java.util.Arrays;

import edu.brandeis.cs12b.pa04.provided.Point;

public class City {

	public int maxX;
	public int maxY;
	private int[][] layout;
	private final int SNOWEDROAD = 1;
	private final int CLEAREDROAD = 2;
	private final int OFFROAD = 0;
	
	/*
	 * NOTE: THE CITY CLASS IS FULLY IMPLEMENTED FOR YOU. YOU SHOULD NOT CHANGE IT!
	 */
	
	
	/**
	 * Create a new city with a given layout
	 * @param layout the layout for the new city
	 */
	public City(int[][] layout){
		this.layout = layout;
		this.maxY = this.layout.length;
		this.maxX = this.layout[0].length;
	}
	
	/**
	 * Checks to see if a given location is a wall
	 * @param location to check
	 * @return true if the location is a wall, false if it isn't
	 */
	public boolean isOffRoad(Point location){
		return (this.layout[location.y][location.x] == OFFROAD);
	}
	
	/**
	 * Checks to see if a given location is covered in snow
	 * @param location to check
	 * @return true if the location is snowed, false if it isn't
	 */
	public boolean isSnowed(Point location){
		return (this.layout[location.y][location.x] == SNOWEDROAD);
	}
	
	/**
	 * Clears a road of snow
	 * @param location to clear
	 * @return true if the location was successfully cleared of snow, 
	 * 		   false if it failed for some reason
	 */
	public boolean clearSnow(Point location){
		if(this.layout[location.y][location.x] == SNOWEDROAD){
			this.layout[location.y][location.x] = CLEAREDROAD;
			return true;
		} else return false;
	}
	
	/**
	 * Checks to see if all the city's streets are cleared of snow
	 * @return true if all roads are clear, false if not
	 */
	public boolean isClear(){
		for(int[] arr2: this.layout)
		{
		    for(int value: arr2)
		        if (value == SNOWEDROAD)
		        	return false;
		}
		return true;	
	}
	
	/**
	 * returns a string representation of the city
	 */
	public String toString(){
		return Arrays.deepToString(this.layout);
	}

}
