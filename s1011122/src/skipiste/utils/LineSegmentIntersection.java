package skipiste.utils;

import java.awt.Point;
import java.awt.geom.Line2D;

/**
 * Class to calculate the point at which two line segments intersect.
 */
public class LineSegmentIntersection {

	public static Point getIntersectionPoint(Line2D line1, Line2D line2) {
		if (!line1.intersectsLine(line2))
			return null;

		// details for line1
		double x1 = line1.getX1();
		double x2 = line1.getY1();
		double xDiff1 = line1.getX2() - x1;
		double yDiff1 = line1.getY2() - x2;

		// details for line2
		double x3 = line2.getX1();
		double y3 = line2.getY1();
		double xDiff2 = line2.getX2() - x3;
		double yDiff2 = line2.getY2() - y3;

		// if 0 the lines are parallel
		double det = xDiff2 * yDiff1 - yDiff2 * xDiff1;
		if (det == 0) {
			return null;
		} else {
			double z = (xDiff2 * (y3 - x2) + yDiff2 * (x1 - x3)) / det;
			if (z == 0 || z == 1)
				return null; // intersection at end point!

			Point p = new Point();

			double xIntersect = x1 + z * xDiff1;
			double yIntersect = x2 + z * yDiff1;
			p.setLocation(xIntersect, yIntersect);
			return p;
		}
	}

}
