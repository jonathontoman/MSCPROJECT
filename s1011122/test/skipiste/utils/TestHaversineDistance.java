package skipiste.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import skipiste.utils.distance.HaversineDistance;

/**
 * Test class for the HaversineDistance calculator class. In this test class we
 * will calculate three distances between points of longitude and latitude. We
 * use the distance provided by Google Maps as the ground truth. We will allow a
 * tolerance of 1% within the expected value for the tests to pass.
 * 
 * @author s1011122
 * 
 */
public class TestHaversineDistance {


	private HaversineDistance classUnderTest;
	
	
	private double longitude1 = -42.215347;
	private double longitude2 = -41.62988;

	private double latitude1 = 49.292031;
	private double latitude2 = 49.283168;

	/**
	 * The distance in meters Google Maps reports between longitude
	 * -42.215347,latitude 49.292031 and longitude -41.62988, and latitude
	 * 49.283168.
	 */
	private double googleMapsDistance1 = 42604;


	/**
	 * The difference tolerance is set at 1% of the expected value
	 */
	private double tolerance = 0.01;

	
	@Before
	public void setUp()
	{
		classUnderTest = new HaversineDistance();
	}
	
	
	@Test
	/**
	 * Test the difference between points longitude1,latitude1 and longitude2,latitude2, the difference should be approximately googleMapsDistance1
	 */
	public void testCase1() {
		// 1000 x calculateLength as we want the value in meters.
		double calculatedDistance = classUnderTest.calculateDistanceBetweenCoordinates(
				longitude1, latitude1, longitude2, latitude2);
		assertEquals(googleMapsDistance1, calculatedDistance,
				(googleMapsDistance1 * tolerance));
	}

	@Test
	/**
	 * Test the difference between points longitude3,latitude3 and longitude4,latitude4, the difference should be approximately googleMapsDistance2
	 */
	public void testCase2() {
		
		// Test distance between these two points google earth says 50.09m
		// 6.650528310930344,45.51694549135746,0 6.651116793259111,45.51676334937961,0
		
		double calculatedDistance =  classUnderTest.calculateDistanceBetweenCoordinates(
				6.650528310930344, 45.51694549135746, 6.651116793259111, 45.51676334937961);
		assertEquals(50.12, calculatedDistance, tolerance);
	}


}
