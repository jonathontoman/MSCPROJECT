package skipiste.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import skipiste.geometry.Point;

/**
 * Test class for the LineSegmentIntersection class
 * 
 * @author s1011122
 * 
 */
public class TestLineSegmentIntersection {

	// test data consists of two line segements. 1st line segment is built of
	// Point A to Point B , the second is Point C to Point D.
	Point a;
	Point b;
	Point c;
	Point d;

	/**
	 * Test case one, calculate the intersection of two lines. Line 1 = 0,0 -
	 * 4,4 and Line 2 = 4,0 - 0,4. The expected intersection Point is (2,2)
	 */
	public void setUpTestCase1() {
		// a = new Point(0, 0);

		a = new Point(0, 0);
		b = new Point(4, 4);
		c = new Point(4, 0);
		d = new Point(0, 4);
	}

	/**
	 * Test case two, calculate the intersection of two lines. Line 1 = 5,5 -
	 * 25,20 and Line 2 = 0,10 - 10,20. The expected intersection Point is
	 * (12,10)
	 */
	public void setUpTestCase2() {
		a = new Point(5, 5);
		b = new Point(25, 20);
		c = new Point(0, 10);
		d = new Point(20, 10);
	}

	/**
	 * Test case three, calculate the intersection of two lines. Line 1 =
	 * 6.1,45.1 - 6.2,45.3 and Line 2 = 6.2, 45.3 - 6.2,45.1. The expected
	 * intersection Point is (6.143,45.186)
	 */
	public void setUpTestCase3() {
		a = new Point(6.1, 45.1);
		b = new Point(6.2, 45.3);
		c = new Point(6.2, 45.1);
		d = new Point(6.0, 45.4);
	}

	/**
	 * Test case two, calculate the intersection of two lines. Line 1 = 1,1 -
	 * 1,4 and Line 2 = 3,1 - 3,4. There is no intersection as these lines are
	 * parallel
	 */
	public void setUpTestCase4() {
		a = new Point(1, 1);
		b = new Point(1, 4);
		c = new Point(3, 1);
		d = new Point(3, 4);
	}

	/**
	 * Test case two, calculate the intersection of two lines. Line 1 = 5,5 -
	 * 25,20 and Line 2 = 0,10 - 10,20. The expected intersection Point is
	 * (25,35) this is outside of the line segments that the points represent.
	 */
	public void setUpTestCase5() {
		a = new Point(5, 5);
		b = new Point(15, 20);
		c = new Point(10, 5);
		d = new Point(15, 15);
	}

	/**
	 * Tests the LineSegementIntersection calculates the correct X coordinate
	 * for the intersection of test case 1.
	 */
	@Test
	public void testCalculateXIntersectionTestCase1() {

		// Setup testcase1
		setUpTestCase1();
		// Expected X intersection = 2;
		double expected = 2;
		Point actual = LineSegmentIntersection.getIntersectionPoint(a, b, c, d);
		assertEquals(expected, actual.getX(), 0);
	}

	/**
	 * Tests the LineSegementIntersection calculates the correct Y coordinate
	 * for the intersection of test case 1.
	 */
	@Test
	public void testCalculateYIntersectionTestCase1() {

		// Setup testcase1
		setUpTestCase1();
		// Expected Y intersection = 2;
		double expected = 2;
		Point actual = LineSegmentIntersection.getIntersectionPoint(a, b, c, d);
		assertEquals(expected, actual.getY(), 0);
	}

	/**
	 * Tests the LineSegementIntersection calculates the correct X coordinate
	 * for the intersection of test case 1.
	 */
	@Test
	public void testCalculateXIntersectionTestCase2() {

		// Setup testcase2
		setUpTestCase2();
		// Expected X intersection = 11.6666;
		double expected = 11.6666;
		Point actual = LineSegmentIntersection.getIntersectionPoint(a, b, c, d);
		assertEquals(expected, actual.getX(), 0.0001);
	}

	/**
	 * Tests the LineSegementIntersection calculates the correct Y coordinate
	 * for the intersection of test case 1.
	 */
	@Test
	public void testCalculateYIntersectionTestCase2() {

		// Setup testcase2
		setUpTestCase2();
		// Expected Y intersection = 10;
		double expected = 10;
		Point actual = LineSegmentIntersection.getIntersectionPoint(a, b, c, d);
		assertEquals(expected, actual.getY(), 0);
	}

	/**
	 * Tests the LineSegementIntersection calculates the correct X coordinate
	 * for the intersection of test case 1.
	 */
	@Test
	public void testCalculateXIntersectionTestCase3() {

		// Setup testcase3
		setUpTestCase3();
		// Expected X intersection = 6.143;
		double expected = 6.143;
		Point actual = LineSegmentIntersection.getIntersectionPoint(a, b, c, d);
		assertEquals(expected, actual.getX(), 0.001);
	}

	/**
	 * Tests the LineSegementIntersection calculates the correct Y coordinate
	 * for the intersection of test case 1.
	 */
	@Test
	public void testCalculateYIntersectionTestCase3() {

		// Setup testcase3
		setUpTestCase3();
		// Expected Y intersection = 45.186;
		double expected = 45.186;
		Point actual = LineSegmentIntersection.getIntersectionPoint(a, b, c, d);
		assertEquals(expected, actual.getY(), 0.001);
	}

	/**
	 * Tests the LineSegementIntersection calculates the correct X coordinate
	 * for the intersection of test case 4.
	 */
	@Test
	public void testCalculateXIntersectionTestCase4() {

		// Setup testcase3
		setUpTestCase4();
		// expect this to return null as the test case is parallel lines.
		assertNull(LineSegmentIntersection.getIntersectionPoint(a, b, c, d));
	}

	/**
	 * Tests the LineSegementIntersection calculates the correct Y coordinate
	 * for the intersection of test case 4.
	 */
	@Test
	public void testCalculateYIntersectionTestCase4() {

		// Setup testcase4
		setUpTestCase4();
		// expect this to return null as the test case is parallel lines.
		assertNull(LineSegmentIntersection.getIntersectionPoint(a, b, c, d));
	}

	/**
	 * Tests the LineSegementIntersection calculates the correct X coordinate
	 * for the intersection of test case 5.
	 */
	@Test
	public void testCalculateXIntersectionTestCase5() {

		// Setup testcase5
		setUpTestCase5();
		// Expected X intersection = 25
		double expected = 25;
		Point actual = LineSegmentIntersection.getIntersectionPoint(a, b, c, d);
		assertEquals(expected, actual.getX(), 0.001);
	}

	/**
	 * Tests the LineSegementIntersection calculates the correct Y coordinate
	 * for the intersection of test case 5.
	 */
	@Test
	public void testCalculateYIntersectionTestCase5() {

		// Setup testcase5
		setUpTestCase5();
		// Expected Y intersection = 35
		double expected = 35;
		Point actual = LineSegmentIntersection.getIntersectionPoint(a, b, c, d);
		assertEquals(expected, actual.getY(), 0.001);

	}



}
