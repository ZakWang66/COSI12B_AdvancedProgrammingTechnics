package edu.brandeis.cs12b.pa04;

import edu.brandeis.cs12b.pa04.provided.City;
import edu.brandeis.cs12b.pa04.provided.Point;
import edu.brandeis.cs12b.pa04.provided.VehicleError;

public class LeftSnowPlow extends SnowPlow {

	protected String name = "LeftSnowPlow";
	
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
			super.turnLeft();
			if (super.move()) {
				return true;
			}
			else {
				if (name.equals("LeftSnowPlow")) {
					this.reportMoveError();
				}
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
