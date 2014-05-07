package skipiste.utils.distance;

import skipiste.graph.elements.Node;

public interface DistanceCalculator {

	/**
	 * Calculates the straight line distance between two sets of longitude and
	 * latitude coordinates
	 * 
	 * @return double - the distance in between the two points.
	 * 
	 */
	public abstract double calculateDistanceBetweenNodes(Node node1, Node node2);

	/**
	 * Calculates the straight line distance between two sets of longitude and
	 * latitude coordinates based on the Haversine formula
	 * 
	 * @return double - the distance between the two points.
	 * 
	 */
	public abstract double calculateDistanceBetweenCoordinates(Double x1, Double y1,
			Double x2, Double y2);

}