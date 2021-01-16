import static org.junit.Assert.*;

import org.junit.Test;

public class DrivingHistoryTest {

	@Test
	/**
	 * test to check the input file read method by returning boolean value
	 */
	public void readFile() {
		DrivingHistory dh = new DrivingHistory();
	    String testFileName = "drive_details.txt";
	    boolean expected = true;
	    boolean result = dh.readInputFile(testFileName);
	    assertEquals(expected, result);
	}
	
	@Test
	/**
	 * test to check the unique number of drivers form input text file
	 */
	public void testNumberOfDrivers() {
		DrivingHistory dh = new DrivingHistory();
		String testFileName = "drive_details.txt";
		dh.readInputFile(testFileName);
		dh.generateDriverDetails();
		assertEquals(dh.getNumberOfDrivers(), 4);
	}
	
	
	@Test 
	/**
	 * Test to check one of the driver details - total miles and the average speed
	 */
	public void testDriverDetails() {
		DrivingHistory dh = new DrivingHistory();
		String testFileName = "drive_details.txt";
		dh.readInputFile(testFileName);
		dh.generateDriverDetails();
		
		assertEquals(dh.returnDanDetails().getDistance(), 39);
		assertEquals(dh.returnDanDetails().name, "Dan");
		assertEquals(dh.returnDanDetails().getSpeed(), "47");
		
	}

}
