package skipiste.geometry;

/**
 * Represents a geometric line segment
 * 
 * @author s1011122
 * 
 */
public class LineSegment {
	/**
	 * The first point of the line segment.
	 */
	private final Point p1;
	/**
	 * The second point of the line segment.
	 */
	private final Point p2;
	/**
	 * The slope of the line
	 */
	private final Slope s;

	/**
	 * Line Segment constructor
	 * 
	 * @param a
	 *            - the x,y point of the start of this line
	 * @param b
	 *            - the x,y point of the end of this line
	 */
	public LineSegment(Point a, Point b) {
		this.p1 = a;
		this.p2 = b;
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
		return new Point(x, y);
	}

	/**
	 * Find the x coordinate of the line intersection
	 * 
	 * @param line
	 *            the line we are calculating against.
	 * @return the X intesection point.
	 */
	private double getIntersectionXCoordinate(LineSegment line) {
		if (s.isVertical()) {
			return p1.getX();
		}
		if (line.s.isVertical()) {
			return line.p1.getX();
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
	 * @return the Y intersection point.
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

		if (!isWithin(z.getX(), p1.getX(), p2.getX())) {
			return false;
		}
		if (!isWithin(z.getY(), p1.getY(), p2.getY())) {
			return false;
		}
		if (s.isVertical()) {
			return true;
		}

		return z.getY() == solveY(z.getX());
	}

	/**
	 * Returns the distance of p3 to the segment defined by p1,p2;
	 * 
	 * @param a
	 *            First point of the segment
	 * @param b
	 *            Second point of the segment
	 * @param p3
	 *            Point to which we want to know the distance of the segment
	 *            defined by p1,p2
	 * @return The distance of p3 to the segment defined by p1,p2
	 */
	public Point closestPointOnLine(Point p3) {

		// the x axis difference of the ends of this line segment
		double xDelta = p2.getX() - p1.getX();
		// the y axis difference of the ends of this line segmenet
		double yDelta = p2.getY() - p1.getY();

		if ((xDelta == 0) && (yDelta == 0)) {
			// p1 and p2 can not be the same point
			return null;

		}

		double u = ((p3.getX() - p1.getX()) * xDelta + (p3.getY() - p1.getY())
				* yDelta)
				/ (xDelta * xDelta + yDelta * yDelta);

		u = ((p3.getX() - p1.getX()) * xDelta + (p3.getY() - p1.getY())
				* yDelta)
				/ (xDelta * xDelta + yDelta * yDelta);

		// The closest point on this line to the point p3
		Point p;
		if (u < 0) {
			p = p1;
		} else if (u > 1) {
			p = p2;
		} else {
			p = new Point(p1.getX() + u * xDelta, p1.getY() + u * yDelta);
		}
		return p;
	}

	/**
	 * Test whether one number is within the range specified by another.
	 * 
	 * @param test
	 * @param bound1
	 * @param bound2
	 * @return
	 */
	private boolean isWithin(double test, double bound1, double bound2) {

		return test >= Math.min(bound1, bound2)
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
		return p1.getY() - s.asDouble() * p1.getX();
	}

	/**
	 * @return the a
	 */
	public Point getA() {
		return p1;
	}

	/**
	 * @return the b
	 */
	public Point getB() {
		return p2;
	}

	/**
	 * @return the s
	 */
	public Slope getSlope() {
		return s;
	}

}
