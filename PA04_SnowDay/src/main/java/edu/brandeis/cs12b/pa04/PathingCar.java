package edu.brandeis.cs12b.pa04;

import edu.brandeis.cs12b.pa04.provided.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.brandeis.cs12b.pa04.provided.ExtraCreditResponses;

public class PathingCar extends Car {

	protected String name = "PathingCar";
	private Point destination;
	private boolean canReachTarget;
	private Simulator sim;
	private ArrayList<Point> snowedRoute = new ArrayList<Point>();
	private ArrayList<SnowPlow> phantomSnowPlows = new ArrayList<SnowPlow>();
	private boolean predictResult = false;
	
	private PathingCar(Point destination) {
		this.destination = destination;
	}
	
	public PathingCar(Point destination, Simulator sim) {
		this.destination = destination;
		this.sim = sim; 
	}
	
	// This method will only be called by a simulateCar
	private boolean checkRoute() {
		while (!location.toString().equals(destination.toString())) {
			// Use the Vehicle move method to test the road status
			if (super.move()) {
				if (this.city.isSnowed(location)) {
					snowedRoute.add(location);
				}
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	private void putPhantomSnowPlow() {
		for (Vehicle v : sim.vehicles) {
			String name = v.getName();
			if (name.equals("SnowPlow") || name.equals("LeftSnowPlow") || name.equals("RightSnowPlow")) {
				SnowPlow phantomSnowPlow;
				if (name.equals("LeftSnowPlow")) {
					phantomSnowPlow = new LeftSnowPlow();
					phantomSnowPlow.name = "phantomLeftSnowPlow";
				}
				else if (name.equals("RightSnowPlow")) {
					phantomSnowPlow = new RightSnowPlow();
					phantomSnowPlow.name = "phantomRightSnowPlow";
				}
				else {
					phantomSnowPlow = new SnowPlow();
					phantomSnowPlow.name = "phantomSnowPlow";
				}
				phantomSnowPlow.place(sim.city, new Point(v.location.toString()), new String(v.facing));
				phantomSnowPlows.add(phantomSnowPlow);
			}
		}
	}
	
	private void movePhantomSnowPlow(Set<String> prediction) {
		// Use a map to record the points the SnowPlows' starting point
		Map<Vehicle, String> passedPoint = new HashMap<Vehicle, String>();
		for (Vehicle v : phantomSnowPlows) {
			passedPoint.put(v, v.location.toString());
		}
		
		while (!phantomSnowPlows.isEmpty()) {
			for (int i = 0; i < phantomSnowPlows.size();) {
				Vehicle v = phantomSnowPlows.get(i);
				// SnowPlow unable to move 
				//  || passedPoint.get(v).equals(v.location.toString()) // get to the original starting point 
				if (!v.move()) {
					phantomSnowPlows.remove(i);
				}
				else {
					prediction.add(v.location.toString());
					i++;
				}
			}
		}
	}
	
	private boolean testRoute(Set<String> prediction) {
		for (Point p : snowedRoute) {
			if (!prediction.contains(p.toString())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean predictor() {
		Set<String> prediction = new HashSet<String>();
		putPhantomSnowPlow();
		movePhantomSnowPlow(prediction);
		return testRoute(prediction);
	}
	
//	private boolean snowedRouteIsCleaned() {
//		for (Point p : snowedRoute) {
//			if (sim.city.isSnowed(p)) {
//				return false;
//			}
//		}
//		return true;
//	}
	
	@Override
	public boolean move() {
		if (location.toString().equals(destination.toString())) {
			return true;
		}
		PathingCar simulateCar = new PathingCar(destination);
		simulateCar.name = "simulateCar";
		simulateCar.city = this.city;
		simulateCar.location = new Point(this.location.toString());
		simulateCar.facing = new String(this.facing);
		snowedRoute = new ArrayList<Point>();
		canReachTarget = simulateCar.checkRoute();
		this.snowedRoute = simulateCar.snowedRoute;
		if (!canReachTarget) {
			reportMoveError();
			return false;
		}
		if (!predictResult) {
			predictResult = predictor();	
		}
		if (predictResult) {
			return super.move();
		}
		else {
			reportWaiting();
			return true;
		}
	}
	
	public void reportWaiting() {
		// TODO Auto-generated method stub
		System.out.println(ExtraCreditResponses.PATHING_CAR_WAITING);
	}
	
	@Override
	public void reportMoveError() {
		// TODO Auto-generated method stub
		System.out.println(ExtraCreditResponses.PATHING_CAR_FAIL);
	}

	@Override
	public void reportPlaceError() {
		// TODO Auto-generated method stub
		System.out.println(ExtraCreditResponses.PATHING_CAR_PLACEMENT);
	}

}
