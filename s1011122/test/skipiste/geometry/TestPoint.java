package skipiste.geometry;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test case for the Point Class
 * @author s1011122
 */
public class TestPoint {
	@Test
	public void testEquals() {
		assertEquals(new Point(0, 0), new Point(0, 0));
		assertEquals(new Point(5, 8), new Point(5, 8));
		assertEquals(new Point(-4, 6), new Point(-4, 6));
		assertFalse(new Point(0, 0).equals(new Point(1, 0)));
		assertFalse(new Point(0, 0).equals(new Point(0, 1)));
		assertFalse(new Point(4, 4).equals(new Point(-4, 4)));
		assertFalse(new Point(4, 4).equals(new Point(4, -4)));
		assertFalse(new Point(4, 4).equals(new Point(-4, -4)));
		assertFalse(new Point(-4, 4).equals(new Point(-4, -4)));
		}
	
		@Test
		public void testDistance() 
		{
		assertEquals(13d, new Point(0, 0).distance(new Point(0, 13)), 0);
		assertEquals(13d, new Point(0, 0).distance(new Point(13, 0)), 0);
		assertEquals(13d, new Point(0, 0).distance(new Point(0, -13)), 0);
		assertEquals(13d, new Point(0, 0).distance(new Point(-13, 0)), 0);
		assertEquals(5d, new Point(1, 1).distance(new Point(4, 5)), 0);
		assertEquals(5d, new Point(1, 1).distance(new Point(-2, -3)), 0);
		}
}
