package edu.brandeis.cs12b.pa04;

import java.util.ArrayList;

import edu.brandeis.cs12b.pa04.provided.City;
import edu.brandeis.cs12b.pa04.provided.Point;

public class Simulator {
	
	protected ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

	protected City city;
	
	/**
	 * Creates a new simulation for a city
	 * @param city to be simulated
	 */
	public Simulator(City city){
		this.city = city;
	}
	
	/** 
	 * You do not need to alter this constructor.
	 */
	protected Simulator(){}

	/**
	 * This should move each vehicle in the city the given number of steps
	 * on the city map in the appropriate String.
	 * If a vehicle makes an invalid move, ensure the correct error is
	 * printed and remove it from the state.
	 */
	public void step(int numberOfSteps){
		//TODO: Implement me!
		for (int i = 0; i < numberOfSteps; i++) {
			for (int j = 0; j < vehicles.size();) {
				Vehicle v = vehicles.get(j);
				if (!v.move()) {
					vehicles.remove(j);
				}
				else {
					j++;
				}
			}
		}
	}

	/**
	 * Places a Vehicle in the city
	 * 
	 * @param vehicle to place in the city
	 * @param location to place the vehicle in the city
	 * @return true if vehicle is successfully placed, false if not
	 */
	public boolean placeVehicle(Vehicle vehicle, Point location, String facing){
		//TODO: Implement me!
		if (vehicle.place(city, location, facing)) {
			vehicles.add(vehicle);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Check to see if the Simulation's city is clear
	 * @return true if the city is clear, false if not
	 */
	public boolean isClear(){
		return this.city.isClear();
	}
	
	public String toString(){
		return this.city.toString();
	}
}
