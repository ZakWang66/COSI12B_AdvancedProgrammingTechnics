package edu.brandeis.cs12b.pa04;

import edu.brandeis.cs12b.pa04.PathingCar;

import edu.brandeis.cs12b.pa04.provided.City;
import edu.brandeis.cs12b.pa04.provided.Point;
import edu.brandeis.cs12b.pa04.provided.VehicleError;

public class Car extends Vehicle {
	
	protected String name = "Car";

	@Override
	public boolean place(City city, Point location, String facing) {
		//TODO: Implement me!
		if (!super.place(city, location, facing) || city.isSnowed(location)) {
			this.reportPlaceError();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean move() {
		// For PathingCar use, directly call the default move method in Vehicle class
		if ((this instanceof PathingCar) && ((PathingCar)this).name.equals("simulateCar")) {
			return super.move();
		}
		if (super.move() && !city.isSnowed(location)) {
			return true;
		}
		else {
			this.reportMoveError();
			return false;
		}
	}
	
	@Override
	public void reportMoveError() {
		//TODO: Implement me!
		System.out.println(VehicleError.CAR_MOVE_ERROR);
	}

	@Override
	public void reportPlaceError() {
		//TODO: Implement me!
		System.out.println(VehicleError.CAR_PLACEMENT_ERROR);
	}
	
	@Override
	public String getName() {
		return this.name;
	}

}
