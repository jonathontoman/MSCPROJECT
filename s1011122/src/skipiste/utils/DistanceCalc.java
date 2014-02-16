package skipiste.utils;

import skipiste.graph.Node;
/**
 * Interface that classes calculating the distance between two nodes must implement.
 * @author s1011122
 */
public interface DistanceCalc 
{
	/**
	 * Calculate the distance between two Nodes.
	 * @return double the distance in kilometers between two nodes.
	 */
	public double calculateDistance(Node node1, Node node2);	
}
