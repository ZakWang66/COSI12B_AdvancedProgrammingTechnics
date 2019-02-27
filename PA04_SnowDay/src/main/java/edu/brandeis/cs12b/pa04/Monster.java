package edu.brandeis.cs12b.pa04;

import edu.brandeis.cs12b.pa04.provided.City;
import edu.brandeis.cs12b.pa04.provided.ExtraCreditResponses;
import edu.brandeis.cs12b.pa04.provided.Point;

public class Monster extends Vehicle{
	
	private static String MONSTER_MOVE_ERROR = "ERROR: MONSTER FAILED TO MOVE";
	private static String MONSTER_PLACEMENT = "ERROR: MONSTER FAILED TO BE PLACED";
	private static String MONSTER_DESTROY_CAR = "MONSTER DESTROYED A CAR";
	
	protected String name = "Monster";
	private Simulator sim;
	
	public Monster(Simulator sim) {
		this.sim = sim;
	}
	
	private void destroyCars() {
		for (int i = 0; i < sim.vehicles.size();) {
			Vehicle v = sim.vehicles.get(i);
			if ((v instanceof Car) && v.location.toString().equals(this.location.toString())) {
				sim.vehicles.remove(v);
				reportCarDestroyed();
			}
			else {
				i++;
			}
		}
	}
	
	@Override
	public boolean place(City city, Point location, String facing) {
		//TODO: Implement me!
		if (!super.place(city, location, facing) || city.isSnowed(location)) {
			this.reportPlaceError();
			return false;
		}
		destroyCars();
		return true;
	}
	
	@Override
	public boolean move() {
		if (super.move() && !city.isSnowed(location)) {
			destroyCars();
			return true;
		}
		else {
			this.reportMoveError();
			return false;
		}
	}

	public void reportCarDestroyed() {
		// TODO Auto-generated method stub
		System.out.println(MONSTER_DESTROY_CAR);
	}
	
	@Override
	public void reportMoveError() {
		// TODO Auto-generated method stub
		System.out.println(MONSTER_MOVE_ERROR);
	}

	@Override
	public void reportPlaceError() {
		// TODO Auto-generated method stub
		System.out.println(MONSTER_PLACEMENT);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
}
