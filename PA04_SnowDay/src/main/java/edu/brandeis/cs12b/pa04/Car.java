package edu.brandeis.cs12b.pa04;

import edu.brandeis.cs12b.pa04.provided.City;
import edu.brandeis.cs12b.pa04.provided.Point;
import edu.brandeis.cs12b.pa04.provided.VehicleError;

public class Car extends Vehicle {
	
	protected String name = "Car";

	@Override
	public boolean place(City city, Point location, String facing) {
		//TODO: Implement me!
		if (city.isSnowed(location)) {
			this.reportPlaceError();
			return false;
		}
		return super.place(city, location, facing);
	}
	
	@Override
	public boolean move() {
		if (!city.isSnowed(location.translate(facing)) && super.move()) {
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
