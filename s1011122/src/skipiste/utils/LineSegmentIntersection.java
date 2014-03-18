package skipiste.utils;

import java.awt.geom.Line2D;

import skipiste.geometry.Point;

/**
 * Class to calculate the point at which two line segments intersect.
 */
public class LineSegmentIntersection {

	/**
	 * Returns the point at which two 2d lines intersect, if they do not
	 * intersect returns null
	 * 
	 * @param p1
	 *            - the first xy coordinate of line 1
	 * @param p2
	 *            - the second xy coordinate of line 1
	 * @param p3
	 *            - the first xy coordinate of line 2
	 * @param p4
	 *            - the second xy coordinate of line
	 * @return Point - the point at which the lines intersect.
	 */
	public static Point getIntersectionPoint(Point p1, Point p2, Point p3,
			Point p4) {

		// details for line1
		double x1 = p1.getX();
		double x2 = p1.getY();
		double xDiff1 = p2.getX() - x1;
		double yDiff1 = p2.getY() - x2;

		// details for line2
		double x3 = p3.getX();
		double y3 = p3.getY();
		double xDiff2 = p4.getX() - x3;
		double yDiff2 = p4.getY() - y3;

		// if 0 the lines are parallel
		double det = xDiff2 * yDiff1 - yDiff2 * xDiff1;
		if (det == 0) {
			return null;
		} else {
			double z = (xDiff2 * (y3 - x2) + yDiff2 * (x1 - x3)) / det;
			if (z == 0 || z == 1) {
				System.out.println("intersection at end point");
				return null; // intersection at end point!
			}

			double xIntersect = x1 + z * xDiff1;
			double yIntersect = x2 + z * yDiff1;
			return new Point(xIntersect, yIntersect);
		}
	}

	/**
	 * Make use of java.awt.geom.Line2D to see if the line segments would
	 * intersect
	 */
	public static boolean linesSegementsIntersect(Point p1, Point p2, Point p3,
			Point p4) {

		Line2D.Double line1 = new Line2D.Double(p1.getX(), p1.getY(),
				p2.getX(), p2.getY());
		Line2D.Double line2 = new Line2D.Double(p3.getX(), p3.getY(),
				p4.getX(), p4.getY());
		return line1.intersectsLine(line2);
	}

}
