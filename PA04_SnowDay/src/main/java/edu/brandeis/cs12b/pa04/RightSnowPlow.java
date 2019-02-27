package edu.brandeis.cs12b.pa04;

import edu.brandeis.cs12b.pa04.provided.City;
import edu.brandeis.cs12b.pa04.provided.Point;
import edu.brandeis.cs12b.pa04.provided.VehicleError;

public class RightSnowPlow extends SnowPlow {

	protected String name = "RightSnowPlow";
	
	/*
	@Override
	public boolean place(City city, Point location, String facing) {
		//TODO: Implement me!
	}
	*/
	
	@Override
	public boolean move() {
		//TODO: Implement me!
		if (super.move()) {
			return true;
		}
		else {
			super.turnRight();
			if (super.move()) {
				return true;
			}
			else {
				if (name.equals("RightSnowPlow")) {
					this.reportMoveError();
				}
				return false;
			}
		}
	}
	
	@Override
	public void reportMoveError() {
		//TODO: Implement me!
		System.out.println(VehicleError.RIGHTSNOWPLOW_MOVE_ERROR);
	}
	
	@Override
	public void reportPlaceError() {
		//TODO: Implement me!
		System.out.println(VehicleError.RIGHTSNOWPLOW_PLACEMENT_ERROR);
	}
	
	/**
	 * This has been implemented for you.
	 */
	@Override
	public String getName() {
		return this.name;
	}
}
