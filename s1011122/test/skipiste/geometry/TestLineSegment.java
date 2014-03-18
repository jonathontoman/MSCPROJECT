package skipiste.geometry;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test cases for the Line Segment class
 * 
 * @author s101122
 * 
 */
public class TestLineSegment {

	/**
	 * Tests LineSegment.contains(Point p) on a non vertical line
	 */
	@Test
	public void testContainsForNonVerticalLine() {
		Point p = new Point(0, 0);
		Point q = new Point(3, 3);
		LineSegment l = new LineSegment(p, q);

		// Line should contain all these points
		assertTrue(l.contains(p));
		assertTrue(l.contains(new Point(1, 1)));
		assertTrue(l.contains(new Point(2, 2)));
		assertTrue(l.contains(new Point(0.5, 0.5)));
		// Line should not contain any of these points.
		assertFalse(l.contains(new Point(3.1, 3.1)));
		assertFalse(l.contains(new Point(3, 3.1)));
		assertFalse(l.contains(new Point(0, 1)));
		assertFalse(l.contains(new Point(-1, -1)));
	};

	/**
	 * Tests LineSegment.contains(Point p) on a vertical line
	 */
	@Test
	public void testContainsForVerticalLine() {
		Point p = new Point(0, 0);
		Point q = new Point(0, 3);
		LineSegment l = new LineSegment(p, q);
		assertTrue(l.contains(p));
		assertTrue(l.contains(q));
		assertTrue(l.contains(new Point(0, 1)));
		assertTrue(l.contains(new Point(0, 2)));
		assertTrue(l.contains(new Point(0, 0.5)));
		assertFalse(l.contains(new Point(0, 3.1)));
		assertFalse(l.contains(new Point(0.1, 1)));
		assertFalse(l.contains(new Point(1, 0)));
		assertFalse(l.contains(new Point(-1, -1)));
	}

	/**
	 * test the method LineSegmenet.isParallel when both lines are parralell
	 */
	@Test
	public void testIsParallelForTwoVerticalParallelLines() {
		Point p = new Point(1, 1);
		Point q = new Point(1, 6);
		Point r = new Point(4, -2);
		Point s = new Point(4, 0);
		LineSegment l = new LineSegment(p, q);
		LineSegment m = new LineSegment(r, s);
		assertTrue(l.isParallelTo(m));
		assertTrue(m.isParallelTo(l));
	}

	/**
	 * test the method LineSegmenet.isParallel returns false when one line is
	 * parallel and one isn't.
	 */
	@Test
	public void testIsParallelForOneVerticalAndOneNonVerticalLine() {
		Point p = new Point(1, 1);
		Point q = new Point(1, 6);
		Point r = new Point(4, -2);
		Point s = new Point(6, 0);
		LineSegment l = new LineSegment(p, q);
		LineSegment m = new LineSegment(r, s);
		assertFalse(l.isParallelTo(m));
		assertFalse(m.isParallelTo(l));
	}

	/**
	 * Tests parallel but non-vertical lines do not intersect.
	 */
	@Test
	public void testParallelNonVerticalLinesDoNotIntersect() {
		Point p = new Point(0, 0);
		Point q = new Point(3, 3);
		Point r = new Point(5, 0);
		Point s = new Point(8, 3);
		LineSegment l = new LineSegment(p, q);
		LineSegment m = new LineSegment(r, s);
		assertNull(l.intersectionPoint(m));
		assertNull(m.intersectionPoint(l));
	}

	/**
	 * Tests that we do not get a result when calculating the intersection of
	 * vertical lines.
	 */
	@Test
	public void testVerticalLinesDoNotIntersect() {
		Point p = new Point(0, 0);
		Point q = new Point(0, 3);
		Point r = new Point(5, 0);
		Point s = new Point(5, 3);
		LineSegment l = new LineSegment(p, q);
		LineSegment m = new LineSegment(r, s);
		assertNull(l.intersectionPoint(m));
		assertNull(m.intersectionPoint(l));
	}

	/**
	 * Tests calculation of intersection of non vertical lines is correct.
	 */
	@Test
	public void testIntersectionOfNonParallelNonVerticalLines() {
		Point p = new Point(4, 2.5);
		Point q = new Point(5, 1.5);
		Point r = new Point(4, 1);
		Point s = new Point(5, 3);
		LineSegment l = new LineSegment(p, q);
		LineSegment m = new LineSegment(r, s);
		Point i = new Point(2, 2);
		assertEquals(i, l.intersectionPoint(m));
		assertEquals(i, m.intersectionPoint(l));
	}

	/**
	 * Tests calculation of intersection of a vertical and a non vertical line
	 * is correct.
	 */
	@Test
	public void testIntersectionOfVerticalAndNonVerticalLines() {
		Point p = new Point(0, 0);
		Point q = new Point(4, 4);
		Point r = new Point(2, 0);
		Point s = new Point(2, 4);
		LineSegment l = new LineSegment(p, q);
		LineSegment m = new LineSegment(r, s);
		Point i = new Point(2, 2);
		assertEquals(i, l.intersectionPoint(m));
		assertEquals(i, m.intersectionPoint(l));
	}

	/**
	 * This is a special case for us as in some scenarios we will want to know
	 * where the disjointed lines would intersect, but in most cases we don't .
	 */
	@Test
	public void testDisjointLinesDoNotIntersect() {
		Point p = new Point(0, 0);
		Point q = new Point(0, 3);
		Point r = new Point(5, 0);
		Point s = new Point(-1, -3);
		LineSegment l = new LineSegment(p, q);
		LineSegment m = new LineSegment(r, s);
		assertNull(l.intersectionPoint(m));
		assertNull(m.intersectionPoint(l));
	}

}
