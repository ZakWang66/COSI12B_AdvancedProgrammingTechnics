package edu.brandeis.cs12b.pa04;

import edu.brandeis.cs12b.pa04.provided.City;
import edu.brandeis.cs12b.pa04.provided.Point;
import edu.brandeis.cs12b.pa04.provided.VehicleError;

public class LeftSnowPlow extends SnowPlow {

	protected String name = "LeftSnowPlow";
	
	@Override
	public boolean place(City city, Point location, String facing) {
		//TODO: Implement me!
		if (super.place(city, location, facing)) {
			super.city.clearSnow(location);
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean move() {
		//TODO: Implement me!		
		if (super.move()) {
			super.city.clearSnow(location);
			return true;
		}
		else {
			super.turnLeft();
			if (super.move()) {
				super.city.clearSnow(location);
				return true;
			}
			else {
				this.reportMoveError();
				return false;
			}
		}
	}
	
	@Override
	public void reportMoveError() {
		//TODO: Implement me!
		System.out.println(VehicleError.LEFTSNOWPLOW_MOVE_ERROR);
	}
	
	@Override
	public void reportPlaceError() {
		//TODO: Implement me!
		System.out.println(VehicleError.LEFTSNOWPLOW_PLACEMENT_ERROR);
	}
	
	/**
	 * This has been implemented for you.
	 */
	@Override
	public String getName() {
		return this.name;
	}
}
