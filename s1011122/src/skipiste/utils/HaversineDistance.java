package skipiste.utils;

import skipiste.graph.Node;

/**
 * Calculates the distance between two nodes using the Haversine formula.
 * @author s1011122
 *
 */
public class HaversineDistance {
	
	
	
	public static double calculateDistance(Node node1, Node node2) {		
		return calculateLength(node1.getLongitude(), node1.getLattitude(), node2.getLongitude(), node2.getLattitude());
	}

	
	
	/**
	 * Calculates the straight line distance between two sets of longitude and
	 * latitude coordinates based on the Haversine formula
	 * 
	 * 
	 */
	public static double calculateLength(Double longitude1, Double latitude1,
			Double longitude2, Double latitude2) {

		final double R = 6371.009; // Radius of the earth
		Double lat1 = latitude1;
		Double lon1 = longitude1;
		Double lat2 = latitude2;
		Double lon2 = longitude2;
		Double latDistance = deg2rad(lat2 - lat1);
		Double lonDistance = deg2rad(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ (Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2));
		// look at atan2 is this the same as arcsin???
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double distance = R * c;

		return distance;

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
