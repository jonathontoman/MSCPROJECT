package skipiste.utils.distance;

import skipiste.graph.elements.GraphNode;

/**
 * Calculates the distance between two nodes using the Haversine formula.
 * 
 * @author s1011122
 * 
 */
public class HaversineDistance implements DistanceCalculator {

	/* (non-Javadoc)
	 * @see skipiste.utils.distance.DistanceCalculator#calculateDistance(skipiste.graph.elements.Node, skipiste.graph.elements.Node)
	 */
	@Override
	public double calculateDistanceBetweenNodes(GraphNode node1, GraphNode node2) {
		return calculateDistanceBetweenCoordinates(node1.getLongitude(), node1.getLatitude(),
				node2.getLongitude(), node2.getLatitude());
	}

	/* (non-Javadoc)
	 * @see skipiste.utils.distance.DistanceCalculator#calculateLength(java.lang.Double, java.lang.Double, java.lang.Double, java.lang.Double)
	 */
	@Override
	public double calculateDistanceBetweenCoordinates(Double x1, Double y1,
			Double x2, Double y2) {

		final double R = 6371.009; // Radius of the earth
		Double lat1 = y1;
		Double lon1 = x1;
		Double lat2 = y2;
		Double lon2 = x2;
		Double latDistance = deg2rad(lat2 - lat1);
		Double lonDistance = deg2rad(lon2 - lon1);
		Double a = Math.sin(latDistance / 2)
				* Math.sin(latDistance / 2)
				+ (Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
						* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2));
		// look at atan2 is this the same as arcsin???
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double distance = R * c;

		return distance * 1000;

	}

	/**
	 * Convert decimal degrees to radians
	 * 
	 * @param deg
	 *            value to convert
	 * @return radian value
	 */
	private static double deg2rad(double deg) {

		return (deg * Math.PI / 180.0);

	}

}
