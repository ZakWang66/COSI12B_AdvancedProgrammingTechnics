package edu.brandeis.cs12b.pa04.tests;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.brandeis.cs12b.pa04.Car;
import edu.brandeis.cs12b.pa04.LeftSnowPlow;
import edu.brandeis.cs12b.pa04.RightSnowPlow;
import edu.brandeis.cs12b.pa04.Simulator;
import edu.brandeis.cs12b.pa04.SnowPlow;
import edu.brandeis.cs12b.pa04.provided.City;
import edu.brandeis.cs12b.pa04.provided.Point;
import edu.brandeis.cs12b.pa04.provided.VehicleError;

public class VehicleTest {
	
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
	
	@Test
	public void testSnowPlow() {
		Simulator sim = new Simulator(straightLineSnowed);
		
		SnowPlow snowPlow = new SnowPlow();
		sim.placeVehicle(snowPlow, new Point(1, 1), "SOUTH");
		sim.step(5);

		assertTrue(sim.isClear());
		String expectedCity = "[[0, 0, 0], "
							+ "[0, 2, 0], "
							+ "[0, 2, 0], "
							+ "[0, 2, 0], "
							+ "[0, 2, 0], "
							+ "[0, 2, 0], "
							+ "[0, 2, 0], "
							+ "[0, 0, 0]]";
		assertEquals(expectedCity,sim.toString());
		assertEquals("", outContent.toString());
	}
	
	@Test
	public void testCar() {

		Simulator sim = new Simulator(straightLineCleared);
		
		Car car = new Car();
		sim.placeVehicle(car, new Point(1, 1), "SOUTH");
		sim.step(5);
		assertTrue(sim.isClear());
		String expectedCity = "[[0, 0, 0], "
							+ "[0, 2, 0], "
							+ "[0, 2, 0], "
							+ "[0, 2, 0], "
							+ "[0, 2, 0], "
							+ "[0, 2, 0], "
							+ "[0, 2, 0], "
							+ "[0, 0, 0]]";
		assertEquals(expectedCity,sim.toString());
		assertEquals("", outContent.toString());
	}
	
	@Test
	public void testCarFail() {
		City straightLine = new City(new int[][]{
			{0,0,0},
			{0,2,0},
			{0,1,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,0,0},
		});
		
		Simulator sim = new Simulator(straightLine);
		
		Car car = new Car();
		sim.placeVehicle(car, new Point(1, 1), "SOUTH");
		sim.step(1);
		
		assertEquals(VehicleError.CAR_MOVE_ERROR + newLine, outContent.toString());
	}
	
	@Test
	public void testCarFail2() {
		City straightLine = new City(new int[][]{
			{0,0,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
		});
		
		Simulator sim = new Simulator(straightLine);
		
		Car car = new Car();
		sim.placeVehicle(car, new Point(1, 1), "SOUTH");
		sim.step(7);
		assertEquals(VehicleError.CAR_MOVE_ERROR + newLine, outContent.toString());
	}
	
	@Test
	public void testCarFail3() {
		City straightLine = new City(new int[][]{
			{0,0,0},
			{0,1,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
			{0,2,0},
		});
		
		Simulator sim = new Simulator(straightLine);
		
		Car car = new Car();
		sim.placeVehicle(car, new Point(3, 1), "SOUTH");
		assertEquals(VehicleError.CAR_PLACEMENT_ERROR + newLine, outContent.toString());
	}

	@Test
	public void testLeftSnowPlow() {
		Simulator sim = new Simulator(elbowSnowed);
		
		LeftSnowPlow snowPlow = new LeftSnowPlow();
		sim.placeVehicle(snowPlow, new Point(2, 1), "SOUTH");
		sim.step(6);
		assertTrue(sim.isClear());
		String expectedCity ="[[0, 0, 0, 0, 0, 0], "
							+ "[0, 0, 2, 0, 0, 0], "
							+ "[0, 0, 2, 0, 0, 0], "
							+ "[0, 0, 2, 0, 0, 0], "
							+ "[0, 0, 2, 0, 0, 0], "
							+ "[0, 0, 2, 0, 0, 0], "
							+ "[0, 0, 2, 2, 0, 0], "
							+ "[0, 0, 0, 0, 0, 0]]";
		assertEquals(expectedCity,sim.toString());
		assertEquals("", outContent.toString());
	}
	
	
	@Test
	public void testLeftSnowPlowFail() {
		Simulator sim = new Simulator(elbowSnowed);
		
		LeftSnowPlow snowPlow = new LeftSnowPlow();
		sim.placeVehicle(snowPlow, new Point(2, 1), "SOUTH");
		sim.step(8);

		assertTrue(sim.isClear());
		String expectedCity ="[[0, 0, 0, 0, 0, 0], "
							+ "[0, 0, 2, 0, 0, 0], "
							+ "[0, 0, 2, 0, 0, 0], "
							+ "[0, 0, 2, 0, 0, 0], "
							+ "[0, 0, 2, 0, 0, 0], "
							+ "[0, 0, 2, 0, 0, 0], "
							+ "[0, 0, 2, 2, 0, 0], "
							+ "[0, 0, 0, 0, 0, 0]]";
		assertEquals(expectedCity,sim.toString());
		assertEquals(VehicleError.LEFTSNOWPLOW_MOVE_ERROR + newLine, outContent.toString());
	}
	
	@Test
	public void testAllVehicles() {
		City city = new City(new int[][]{
			{0,0,0,0,0,0,0,0,0,0},
			{0,0,0,1,0,0,1,1,0,0},
			{0,0,0,1,0,0,0,1,0,0},
			{0,0,0,1,1,1,1,1,0,0},
			{0,1,1,1,0,0,0,0,0,0},
			{0,0,0,1,1,1,1,1,1,0},
			{0,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0}});
		Simulator sim = new Simulator(city);
		
		LeftSnowPlow leftSnowPlow = new LeftSnowPlow();
		sim.placeVehicle(leftSnowPlow, new Point(8, 5), "WEST");
		SnowPlow snowPlow = new SnowPlow();
		sim.placeVehicle(snowPlow, new Point(1,4), "EAST");
		RightSnowPlow rightSnowPlow = new RightSnowPlow();
		sim.placeVehicle(rightSnowPlow, new Point(6,1), "EAST");
		sim.step(9);

		assertTrue(sim.isClear());
		
		String expectedErrors = VehicleError.SNOWPLOW_MOVE_ERROR + newLine
							  + VehicleError.LEFTSNOWPLOW_MOVE_ERROR + newLine;
		
		assertEquals(expectedErrors, outContent.toString());
	}
}
