package skipiste.utils;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.BeforeClass;
import org.junit.Test;

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

	/**
	 * The class we are testing.
	 */
	private HaversineDistance classUnderTest;

	private double longitude1 = -42.215347;
	private double longitude2 = -41.62988;
	private double longitude3;
	private double longitude4;
	private double longitude5;
	private double longitude6;

	private double latitude1 = 49.292031;
	private double latitude2 = 49.283168;
	private double latitude3;
	private double latitude4;
	private double latitude5;
	private double latitude6;

	/**
	 * The distance in meters Google Maps reports between longitude
	 * -42.215347,latitude 49.292031 and longitude -41.62988, and latitude
	 * 49.283168.
	 */
	private double googleMapsDistance1 = 42604;
	/**
	 * The distance Google Maps reports between longitude1,latitude1 and
	 * longitude2, latitude2.
	 */
	private double googleMapsDistance2;
	/**
	 * The distance Google Maps reports between longitude1,latitude1 and
	 * longitude2, latitude2.
	 */
	private double googleMapsDistance3;

	/**
	 * The difference tolerance is set at 1% of the expected value
	 */
	private double tolerance = 0.01;

	@Test
	/**
	 * Test the difference between points longitude1,latitude1 and longitude2,latitude2, the difference should be approximately googleMapsDistance1
	 */
	public void testCase1() {
		// 1000 x calculateLength as we want the value in meters.
		double calculatedDistance = 1000 * HaversineDistance.calculateLength(
				longitude1, latitude1, longitude2, latitude2);
		assertEquals(googleMapsDistance1, calculatedDistance,
				(googleMapsDistance1 * tolerance));
	}

	@Test
	/**
	 * Test the difference between points longitude3,latitude3 and longitude4,latitude4, the difference should be approximately googleMapsDistance2
	 */
	public void testCase2() {
		// 1000 x calculateLength as we want the value in meters.
		double calculatedDistance = 1000 * HaversineDistance.calculateLength(
				longitude1, latitude1, longitude2, latitude2);
		assertEquals(googleMapsDistance1, calculatedDistance,
				(googleMapsDistance1 * tolerance));
	}

	@Test
	/**
	 * Test the difference between points longitude5,latitude5 and longitude6,latitude6, the difference should be approximately googleMapsDistance3
	 */
	public void testCase3() {
		// 1000 x calculateLength as we want the value in meters.
		double calculatedDistance = 1000 * HaversineDistance.calculateLength(
				longitude1, latitude1, longitude2, latitude2);
		assertEquals(googleMapsDistance1, calculatedDistance,
				(googleMapsDistance1 * tolerance));
	}

}
