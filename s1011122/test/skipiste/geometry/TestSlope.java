package skipiste.geometry;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test cases for the Slope class
 * 
 * @author s1011122
 * 
 */
public class TestSlope {

	/**
	 * Test that a slope is vertical
	 */
	@Test
	public void testIsVertical() {
		// the following lines are vertical (travel == 0)
		assertTrue(new Slope(4, 0).isVertical());
		assertTrue(new Slope(0, 0).isVertical());
		assertTrue(new Slope(-5, 0).isVertical());
		// the following lines are not vertical (travel > 1)
		assertFalse(new Slope(0, 5).isVertical());
		assertFalse(new Slope(0, -5).isVertical());
	}

	/**
	 * Test slopes are equal
	 */
	@Test
	public void testEquals() {
		// The following slops should all be equal
		assertTrue(new Slope(0, -5).equals(new Slope(0, 10)));
		assertTrue(new Slope(1, 3).equals(new Slope(2, 6)));
		assertTrue(new Slope(5, 0).equals(new Slope(9, 0)));
		// The following slopes should all be unequal
		assertFalse(new Slope(1, 3).equals(new Slope(-1, 3)));
		assertFalse(new Slope(1, 3).equals(new Slope(1, -3)));

	}

	/**
	 * Test an exception is thrown if we try to get the double value of a vertical slope
	 */
	@Test
	public void testExceptionThrownWhenAskingForDoubleValueOfVerticalSlope() {

		try {
			new Slope(4, 0).asDouble();
			fail();
		} catch (IllegalStateException e) {
			assertTrue(true);
		}

	}

}
