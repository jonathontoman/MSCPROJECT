package skipiste.geometry;

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

	/**
	 * Line Segment constructor
	 * 
	 * @param a
	 *            - the x,y point of the start of this line
	 * @param b
	 *            - the x,y point of the end of this line
	 */
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
		return new Point(x, y);
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

		if (!isWithin(a.getX(), a.getX(), b.getX())) {
			return false;
		}
		if (!isWithin(z.getY(), a.getY(), b.getY())) {
			return false;
		}
		if (s.isVertical()) {
			return true;
		}

		// // y value of the point z, our comparrison
		// double zy = z.getY();
		// // y value intersection on this line
		// double y = solveY(z.getX());
		// // error margin as we are dealing with doubles
		// double delta = 0.0000000001;
		// double result = y - zy;
		//
		// // problems wiht double precsision use a delta value of 0.000001
		// if (-delta <= result && result <= delta) {
		// return true;
		// }
		// return false;

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

		final double xDelta = b.getX() - a.getX();
		final double yDelta = b.getY() - a.getY();

		if ((xDelta == 0) && (yDelta == 0)) {
			// p1 and p2 can not be the same point
			return null;
					
		}

		final double u = ((p3.getX() - a.getX()) * xDelta + (p3.getY() - a
				.getY()) * yDelta)
				/ (xDelta * xDelta + yDelta * yDelta);

		final Point p;
		if (u < 0) {
			p = a;
		} else if (u > 1) {
			p = b;
		} else {
			p = new Point(a.getX() + u * xDelta, a.getY() + u * yDelta);
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
