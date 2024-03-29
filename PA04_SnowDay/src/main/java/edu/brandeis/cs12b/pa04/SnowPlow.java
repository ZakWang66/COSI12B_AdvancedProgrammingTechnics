package edu.brandeis.cs12b.pa04;

import edu.brandeis.cs12b.pa04.provided.City;
import edu.brandeis.cs12b.pa04.provided.Point;
import edu.brandeis.cs12b.pa04.provided.VehicleError;

public class SnowPlow extends Vehicle {
	
	protected String name = "SnowPlow";

	@Override
	public boolean place(City city, Point location, String facing) {
		//TODO: Implement me!
		if (super.place(city, location, facing)) {
			super.city.clearSnow(location);
			return true;
		}
		else {
			reportPlaceError();
			return false;
		}
	}
	
	@Override
	public boolean move() {
		//TODO: Implement me!
		if (super.move()) {
			// A "phantomSnowPlow" is used for PathingCar simulation, which won't really clear snow
			if (name.equals("SnowPlow") || name.equals("LeftSnowPlow") || name.equals("RightSnowPlow")) {
				this.city.clearSnow(location);
			}
			return true;
		}
		else {
			if (name.equals("SnowPlow")) {
				this.reportMoveError();
			}
			return false;
		}
	}

	@Override
	public void reportMoveError() {
		//TODO: Implement me!
		System.out.println(VehicleError.SNOWPLOW_MOVE_ERROR);
	}

	@Override
	public void reportPlaceError() {
		//TODO: Implement me!
		System.out.println(VehicleError.SNOWPLOW_PLACEMENT_ERROR);
	}

	/**
	 * This has been implemented for you.
	 */
	@Override
	public String getName() {
		return this.name;
	}
}
