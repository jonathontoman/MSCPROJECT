package skipiste.geometry;

import skipiste.utils.HaversineDistance;

/**
 * Represents a geometric line segment
 * 
 * @author s1011122
 * 
 */
public class LineSegment {
	private final Point a;
	private final Point b;
	private final Slope s;

	public LineSegment(Point a, Point b) {
		this.a = a;
		this.b = b;
		this.s = new Slope(a.getY() - b.getY(), a.getX() - b.getX());
	}

	/**
	 * Calculates the intersection of this line with another.
	 * 
	 * @param line
	 *            the line we are calculating against
	 * @return the point where the lines intersect, null if they do not
	 *         intersect.
	 */
	public Point intersectionPoint(LineSegment line) {
		if (isParallelTo(line)) {
			return null;
		}
		double x = getIntersectionXCoordinate(line);
		double y = getIntersectionYCoordinate(line, x);
		Point p = new Point(x, y);
		if (line.contains(p) && this.contains(p)) {
			return p;
		}
		return null;
	}

	/**
	 * Calculates the intersection of this line with another.
	 * 
	 * @param line
	 *            the line we are calculating against
	 * @param delta
	 *            - if the intersection point is within deltam (using the
	 *            haversine formula) of either points of this line then consider
	 *            them to intersect.
	 * @return the point where the lines intersect, null if they do not
	 *         intersect.
	 */
	public Point intersectionPoint(LineSegment line, double delta) {

		if (isParallelTo(line)) {
			return null;
		}
		double x = getIntersectionXCoordinate(line);
		double y = getIntersectionYCoordinate(line, x);
		Point p = new Point(x, y);
		if (line.contains(p) && this.contains(p)) {
			return p;
		}
		// if the delta distance is greater than the distance from either the
		// start or end of this to the intersection then we will consider this a
		// match

		// written this way so we can easily see whats going on

		double distanceFormPointA = HaversineDistance.calculateLength(a.getX(),
				a.getY(), p.getX(), p.getY());
		double distanceFormPointB = HaversineDistance.calculateLength(b.getX(),
				b.getY(), p.getX(), p.getY());

		if (delta >= distanceFormPointA || delta >= distanceFormPointB) {
			return p;
		}

		return null;
	}

	/**
	 * Find the x coordinate of the line intersection
	 * 
	 * @param line
	 *            the line we are calculating against.
	 * @return
	 */
	private double getIntersectionXCoordinate(LineSegment line) {
		if (s.isVertical()) {
			return a.getX();
		}
		if (line.s.isVertical()) {
			return line.a.getX();
		}
		double m = s.asDouble();
		double b = base();
		double n = line.s.asDouble();
		double c = line.base();
		return (c - b) / (m - n);
	}

	/**
	 * Calculate the Y coorindate of the line intersection
	 * 
	 * @param the
	 *            line we are calculating against.
	 * @param x
	 * @return
	 */
	private double getIntersectionYCoordinate(LineSegment line, double x) {
		if (s.isVertical()) {
			// we already have the x cooridate so we can just plug it into solve
			// for y.
			// if this line is vertical we solve y on the non vertical line, i.e
			// the parameter passed to this method
			return line.solveY(x);
		}
		return solveY(x);
	}

	/**
	 * Checks if this line segment is parallel to another line.
	 * 
	 * @param line
	 *            - the comparrison line.
	 * @return true if the lines are parallel else false.
	 */
	public boolean isParallelTo(LineSegment line) {
		return s.equals(line.getSlope());
	}

	public boolean contains(Point z) {
		// Test within X bounds
		if (!isWithin(z.getX(), a.getX(), b.getX())) {
			return false;
		}
		// Test within y bounds
		if (!isWithin(z.getY(), a.getY(), b.getY())) {
			return false;
		}
		// vertical test
		if (s.isVertical()) {
			return true;
		}
		double solveY = solveY(z.getX());
		double test = solveY - z.getY();
		// problems wiht double precsision use a delta value of 0.000001
		if (-0.000001 <= test && test <= 0.00001) {
			return true;
		}
		return false;
	}

	/**
	 * Test whether one number is within the range specified by another.
	 * 
	 * @param test
	 * @param bound1
	 * @param bound2
	 * @return
	 */
	private static boolean isWithin(double test, double bound1, double bound2) {
		// has to be greater than the min bound
		return test >= Math.min(bound1, bound2)
		// and lesser than the max bound
				&& test <= Math.max(bound1, bound2);
	}

	/**
	 * Solve the y coordinate given an x coorindate.
	 * 
	 * @param x
	 *            the known x cooridinate.
	 * @return
	 */
	private double solveY(double x) {
		return s.asDouble() * x + base();
	}

	/**
	 * Calculate the value b ( the y intercept) in the formula y = mx +b
	 * 
	 * @return
	 */
	private double base() {
		return a.getY() - s.asDouble() * a.getX();
	}

	/**
	 * @return the a
	 */
	public Point getA() {
		return a;
	}

	/**
	 * @return the b
	 */
	public Point getB() {
		return b;
	}

	/**
	 * @return the s
	 */
	public Slope getSlope() {
		return s;
	}

}
