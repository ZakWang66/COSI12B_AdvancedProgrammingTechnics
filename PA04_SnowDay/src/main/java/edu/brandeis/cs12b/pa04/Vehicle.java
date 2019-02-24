package edu.brandeis.cs12b.pa04;

import edu.brandeis.cs12b.pa04.provided.City;
import edu.brandeis.cs12b.pa04.provided.Point;

public abstract class Vehicle {

	protected Point location;
	protected City city;
	protected String facing;
	
	/**
	 * This places your vehicle into a city. If invalid, ensure that 
	 * somewhere in your code the proper VehicleError is printed.
	 * 
	 * @param city the city to be placed into
	 * @param location the location in the city to be placed
	 * @param facing this direction
	 * @return true if this happens successfully, false if not
	 */
	public boolean place(City city, Point location, String facing){
		//TODO: Implement Baseline place method
		if (city.isOffRoad(location)) {
			this.reportPlaceError();
			return false;
		}
		else {
			this.city = city;
			this.facing = new String(facing);
			this.location = new Point(location.toString());
			return true;
		}
	}

	/**
	 * This method should move your vehicle one cell in the direction it was facing.
	 * If your vehicle can't move because there's a wall in the way, it should stay in
	 * same place and call the reportError(), then return false so it can be taken
	 * off the list of active vehicles
	 * @return true if the move was successfully made, false if not
	 */
	public boolean move(){
		//TODO: Implement baseline move method
		Point target = location.translate(facing);
		if (city.isOffRoad(target)) {
			return false;
		}
		else {
			this.location = target;
			return true;
		}
	}

	/**
	 * This method reports if a vehicle can't move. This should be different for each vehicle.
	 * Use the static fields in the VehicleError class to get the text to print.
	 */
	public abstract void reportMoveError();
	
	/**
	 * Likewise for placing a vehicle.
	 */
	public abstract void reportPlaceError();
	
	/**
	 * This method turns the vehicle in question to the right. Not all vehicles
	 * have this capability, however. So make sure only certain of your vehicles
	 * can turn right.
	 */
	protected void turnRight() {
		//TODO: Implement me!
		if (this.getName().equals("RightSnowPlow")) {
			switch (facing) {
			case "NORTH": facing = "EAST"; break;
			case "EAST": facing = "SOUTH"; break;
			case "SOUTH": facing = "WEST"; break;
			case "WEST": facing = "NORTH"; break;
			}
		}
	}
	
	/**
	 * This method turns the vehicle in question to the left. Not all vehicles
	 * have this capability, however. So make sure only certain of your vehicles
	 * can turn left.
	 */
	protected void turnLeft(){
		//TODO: Implement me!
		if (this.getName().equals("LeftSnowPlow")) {
			switch (facing) {
			case "NORTH": facing = "WEST"; break;
			case "WEST": facing = "SOUTH"; break;
			case "SOUTH": facing = "EAST"; break;
			case "EAST": facing = "NORTH"; break;
			}
		}
	}

	/**
	 * Methods for getting the name of the vehicle and converting to a string have been implemented
	 * for you. However, you should study how this mechanism works to get a sense of how you might
	 * implement inheritance among the various vehicles.
	 */
	public abstract String getName();

	public String toString(){
		return this.getName() + " " + this.location.toString();
	}
}
