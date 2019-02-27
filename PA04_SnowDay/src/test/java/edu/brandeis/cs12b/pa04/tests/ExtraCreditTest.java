package edu.brandeis.cs12b.pa04.tests;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.brandeis.cs12b.pa04.Car;
import edu.brandeis.cs12b.pa04.LeftSnowPlow;
import edu.brandeis.cs12b.pa04.Monster;
import edu.brandeis.cs12b.pa04.PathingCar;
import edu.brandeis.cs12b.pa04.RightSnowPlow;
import edu.brandeis.cs12b.pa04.Simulator;
import edu.brandeis.cs12b.pa04.SnowPlow;
import edu.brandeis.cs12b.pa04.provided.City;
import edu.brandeis.cs12b.pa04.provided.Point;
import edu.brandeis.cs12b.pa04.provided.VehicleError;

public class ExtraCreditTest {
	
	private PrintStream sysOut;
	private ByteArrayOutputStream outContent;
	private static final String newLine = System.getProperty("line.separator");
	
	private City straightLineSnowed;
	private City straightLineCleared;
	private City elbowSnowed;
	
	@Before
	public void setUp() throws Exception {
		sysOut = System.out;
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		//HERE ARE SOME STOCK CITIES FOR YOU TO USE ON YOUR TESTS
		straightLineSnowed = new City(new int[][]{
			{0,0,0},
			{0,1,0},
			{0,1,0},
			{0,1,0},
			{0,1,0},
			{0,1,0},
			{0,1,0},
			{0,0,0},
		});
		straightLineCleared = new City(new int[][]{
			{0,0,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,0,0},
		});
		elbowSnowed = new City(new int[][]{
	        {0,0,0,0,0,0},
			{0,0,1,0,0,0},
			{0,0,1,0,0,0},
			{0,0,1,0,0,0},
			{0,0,1,0,0,0},
			{0,0,1,0,0,0},
			{0,0,1,1,0,0},
			{0,0,0,0,0,0},
		});
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(sysOut);
	}
	/*
	@Test
	public void testMonster() {
		
		//Test with Car
		City city = new City(new int[][]{
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0}});
		Simulator sim = new Simulator(city);
		
		Monster monster = new Monster(sim);
		sim.placeVehicle(monster, new Point(3, 0), "SOUTH");
		Car car = new Car();
		sim.placeVehicle(car, new Point(3,7), "NORTH");
		sim.step(9);
		assertTrue(sim.isClear());
		
		// Test with SnowPlow
		city = new City(new int[][]{
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0},
			{0,0,0,2,0,0,0,0,0,0}});
		sim = new Simulator(city);
		
		monster = new Monster(sim);
		sim.placeVehicle(monster, new Point(3, 0), "SOUTH");
		SnowPlow snowPlow = new SnowPlow();
		sim.placeVehicle(snowPlow, new Point(3,7), "NORTH");
		sim.step(9);
		assertTrue(sim.isClear());		
		String expectedErrors = "MONSTER DESTROYED A CAR" + newLine
				  				+ "ERROR: MONSTER FAILED TO MOVE" + newLine	
								+ "ERROR: MONSTER FAILED TO MOVE" + newLine
								+ "ERROR: SNOWPLOW FAILED TO MOVE" + newLine;		
		assertEquals(expectedErrors, outContent.toString());
				
	}

	// Test Place Error
	@Test
	public void testPathingCar1() {
		City city = new City(new int[][]{
			{0,0,0,0,0,0,0,0,0,0},
			{0,2,0,0,0,0,0,0,0,0},
			{0,2,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,0,0,0,0,0},
			{0,2,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0}});
		Simulator sim = new Simulator(city);
		PathingCar pathingCar = new PathingCar(new Point(1,4), sim);
		sim.placeVehicle(pathingCar, new Point(1, 3), "SOUTH");
		String expectedErrors = "ERROR: PATHING CAR FAILED TO BE PLACED" + newLine;
		assertEquals(expectedErrors, outContent.toString());
	}
	
	// Test when the prediction is start moving and there is a SnowPlow cleaned the point just in time
	@Test
	public void testPathingCar2() {
		City city = new City(new int[][]{
			{0,0,0,0,0,0,0,0,0,0},
			{0,2,0,0,0,0,0,0,0,0},
			{0,2,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,0,0,0,0,0},
			{0,2,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0}});
		Simulator sim = new Simulator(city);
		
		PathingCar pathingCar = new PathingCar(new Point(1,4), sim);
		sim.placeVehicle(pathingCar, new Point(1, 1), "SOUTH");
		
		SnowPlow snowPlow = new SnowPlow();
		sim.placeVehicle(snowPlow, new Point(2,3), "WEST");
		
		sim.step(4);
		String expectedErrors = "ERROR: SNOWPLOW FAILED TO MOVE" + newLine;
		assertEquals(expectedErrors, outContent.toString());
	}
	
	// Test the PathingCar fails while moving.
	// Reason: Although the Prediction tells that it is good to go and the PathingCar starts,
	//         the PathingCar hits the snow before the SnowPlow can reach and clean that point.
	@Test
	public void testPathingCar3() {
		City city = new City(new int[][]{
			{0,0,0,0,0,0,0,0,0,0},
			{0,2,0,0,0,0,0,0,0,0},
			{0,2,0,0,0,0,0,0,0,0},
			{0,1,1,1,1,0,0,0,0,0},
			{0,2,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0}});
		Simulator sim = new Simulator(city);
		
		PathingCar pathingCar = new PathingCar(new Point(1,4), sim);
		sim.placeVehicle(pathingCar, new Point(1, 1), "SOUTH");
		
		SnowPlow snowPlow = new SnowPlow();
		sim.placeVehicle(snowPlow, new Point(4,3), "WEST");
		
		sim.step(3);
		String expectedErrors = "PATHING CAR CANNOT POSSIBLY REACH DESTINATION" + newLine;
		assertEquals(expectedErrors, outContent.toString());
	}
	*/
	// Test the PathingCar waiting for a valid SnowPlow being placed
	@Test
	public void testPathingCar4() {
		City city = new City(new int[][]{
			{0,0,0,0,0,0,0,0,0,0},
			{0,2,0,0,0,0,0,0,0,0},
			{0,2,0,1,0,0,0,0,0,0},
			{0,1,1,1,1,0,0,0,0,0},
			{0,2,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0}});
		Simulator sim = new Simulator(city);
		
		PathingCar pathingCar = new PathingCar(new Point(1,4), sim);
		sim.placeVehicle(pathingCar, new Point(1, 1), "SOUTH");
		
		LeftSnowPlow snowPlow1 = new LeftSnowPlow();
		sim.placeVehicle(snowPlow1, new Point(3,2), "SOUTH");
		
		sim.step(2);
		
		SnowPlow snowPlow2 = new SnowPlow();
		sim.placeVehicle(snowPlow2, new Point(2,3), "WEST");
		
		sim.step(3);
		
		String expectedErrors = "PATHING CAR WAITING FOR ROADS TO BE CLEARED" + newLine
								+ "PATHING CAR WAITING FOR ROADS TO BE CLEARED" + newLine 
								+ "ERROR: SNOWPLOW FAILED TO MOVE" + newLine;
		assertEquals(expectedErrors, outContent.toString());
	}
	
}
