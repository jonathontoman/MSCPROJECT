package skipiste.utils;

import static org.junit.Assert.*;

import java.awt.Point;
import java.awt.geom.Line2D;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the LineSegemntIntersection class
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

	@Before
	public void setUp() {
		a = new Point(0, 0);
		b = new Point(4, 4);
		c = new Point(4, 0);
		d = new Point(0, 4);
	}

	@Test
	public void testCalculateXIntersection() {

		Line2D line1;
		Line2D line2;

		line1 = new Line2D.Double();
		line1.setLine(a, b);

		line2 = new Line2D.Double();
		line2.setLine(c, d);

		// Expected X intersection = 2;
		double expected = 2;
		Point actual = LineSegmentIntersection.getIntersectionPoint(line1,
				line2);
		assertEquals(expected, actual.getX(), 0);
	}

}
