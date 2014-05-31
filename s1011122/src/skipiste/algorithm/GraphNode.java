package skipiste.algorithm;

import java.util.Set;

import skipiste.graph.elements.Edge;

/**
 * Interface implemented by the Node class that guarantees the getCost and
 * setCost methods are implemented.
 * 
 * @author s1011122
 * 
 */
public interface GraphNode {

	/**
	 * Get the associated cost to reach this node.
	 * 
	 * @return double - the cost to reach this node.
	 */
	public double getCost();

	/**
	 * Set the cost to reach this node
	 * 
	 * @return double - the cost to reach this node.
	 */
	public void setCost(double cost);

	/**
	 * Get the edges outbound from this node.
	 * 
	 * @return
	 */
	public Set<Edge> getOutboundEdges();

	/**
	 * Get the previous node in the path to reach this node.
	 * 
	 * @return GraphNode - the previousNode in the path to reach this node.
	 */
	public GraphNode getPrevious();
	
	/**
	 * Get the previous node in the path to reach this node.
	 * 
	 * @return GraphNode - the previousNode in the path to reach this node.
	 */
	public void setPrevious(GraphNode n);
	/**
	 * Get the latitude of this node.
	 * @return double - the latitude of this node
	 */
	public double getLongitude();

	/**
	 * Get the longitude of this node.
	 * @return double the longitude of this node.
	 */
	public double getLatitude();
	
	public int hashCode();
	
	public boolean equals(Object obj);

}
