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
		Point i = new Point(4.5, 2);
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
		
		Point intersection1 = l.intersectionPoint(m);
		assertFalse(l.contains(intersection1));
		
		Point intersection2 = l.intersectionPoint(l);
		assertFalse(l.contains(intersection2));

	}

//	// The below test cases cover disjointed line segments that we know should
//	// intersect in order to properly join up our graph, the line segments
//	// coorindates are taken from the data in the file
//	// PlanMontalbertPistesPlanDePisteNI.kml
//
//	/**
//	 * Test the starting line segments of Les Adrets and Les Grenouilles will intersect
//	 */
//	@Test
//	public void testLesAdretsStartAndLesGrenouillesStartIntersect() {
//
//		// The first two coordinates of the Les Adrets piece make up a line that
//		// should intersect a line made of the first two
//		// coordinates of Les Grenouilles
//		// Les Ardets coords
//		Point p = new Point(6.649970099999999, 45.5167293);
//		Point q = new Point(6.6503774, 45.5168467);
//		// Les Grenouilles coords
//		Point r = new Point(6.6501311, 45.5164903);
//		Point s = new Point(6.6500538, 45.516605);
//
//		LineSegment l = new LineSegment(p, q, 0.0001);
//		LineSegment m = new LineSegment(r, s, 0.0001);
//		assertNotNull(l.intersectionPoint(m));
//		assertNotNull(m.intersectionPoint(l));
//	}
//
//	/**
//	 * Test the starting line segments of Prajourdan and Les Grenouilles will intersect
//	 */
//	@Test
//	public void testPrajourdanStartAndLesGrenouillesStartIntersect() {
//
//		// The first two coordinates of the Prajourdan piece make up a line that
//		// should intersect a line made of the first two
//		// coordinates of Les Grenouilles
//		// Prajourdan coords
//
//		Point p = new Point(6.650081386298162, 45.51683097569678);
//		Point q = new Point(6.650902279454771, 45.51710886439447);
//		// Les Grenouilles coords
//		Point r = new Point(6.650528310930344, 45.51694549135746);
//		Point s = new Point(6.651116793259111, 45.51676334937961);
//
//		LineSegment l = new LineSegment(p, q, 0.0001);
//		LineSegment m = new LineSegment(r, s, 0.0001);
//		assertNotNull(l.intersectionPoint(m));
//		assertNotNull(m.intersectionPoint(l));
//	}
//
//	/**
//	 * Test the ending line segments of Prajourdan and Les Grenouilles will intersect
//	 */
//	@Test
//	public void testLesAdretsEndAndLesGrenouillesEndIntersect() {
//
//		// The first end coordinates of the Prajourdan piece make up a line that
//		// should intersect a line made of the first end coordinates of Les Grenouilles
//		// Les Adrets coords 6.659566384837829,45.51755193074926,0 6.660065558765503,45.51735193590109,0
//
//		Point p = new Point(6.659566384837829,45.51755193074926);
//		Point q = new Point(6.660065558765503,45.51735193590109);
//		// Les Grenouilles coords
//		// 6.658999647647992,45.51740631904666,0 6.659755094291193,45.5174196070418,0
//		Point r = new Point(6.658999647647992,45.51740631904666);
//		Point s = new Point(6.659755094291193,45.5174196070418);
//
//		LineSegment l = new LineSegment(p, q, 0.0001);
//		LineSegment m = new LineSegment(r, s, 0.0001);
//		assertNotNull(l.intersectionPoint(m));
//		assertNotNull(m.intersectionPoint(l));
//	}
//	
//	/**
//	 * Test the ending line segments of LaGragnette andPravendue will intersect
//	 */
//	@Test
//	public void testLaGragnetteEndAndPravendueIntersect() {
//
//		// The first end coordinates of the Pravendue piece make up a line that
//		// should intersect a line made of the first end coordinates of La Gragnette
//		// Pravendue coords 6.637940870077024,45.53458827265512,0 6.636843828642389,45.53458055582091,0
//
//		Point p = new Point(6.637940870077024,45.53458827265512);
//		Point q = new Point(6.636843828642389,45.53458055582091);
//		// La Gragnette coords 6.636957882848369,45.53400626003545,0 6.636881451805334,45.53453808425287,0
//		Point r = new Point(6.636957882848369,45.53400626003545);
//		Point s = new Point(6.636881451805334,45.53453808425287);
//
//		LineSegment l = new LineSegment(p, q, 0.0001);
//		LineSegment m = new LineSegment(r, s, 0.0001);
//		assertNotNull(l.intersectionPoint(m));
//		assertNotNull(m.intersectionPoint(l));
//	}
//	
//	/**
//	 * Test the ending line segments of Montalbert and Pravendue will intersect
//	 */
//	@Test
//	public void testMontablertEndAndPravendueIntersect() {
//
//		// The first end coordinates of the Pravendue piece make up a line that
//		// should intersect a line made of the first end coordinates of La Gragnette
//		// Pravendue coords 6.637940870077024,45.53458827265512,0 6.636843828642389,45.53458055582091,0
//
//		Point p = new Point(6.637940870077024,45.53458827265512);
//		Point q = new Point(6.636843828642389,45.53458055582091);
//		// Montalbert coords 6.637522802026465,45.53414724174873,0 6.637350581122647,45.53455928289565,0 
//		Point r = new Point(6.637522802026465,45.53414724174873);
//		Point s = new Point(6.637350581122647,45.53455928289565);
//
//		LineSegment l = new LineSegment(p, q, 0.0001);
//		LineSegment m = new LineSegment(r, s, 0.0001);
//		assertNotNull(l.intersectionPoint(m));
//		assertNotNull(m.intersectionPoint(l));
//	}

}
