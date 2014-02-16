package skipiste.graph;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * This class represents a single point on a ski piste map as a Node within a
 * graph.
 * 
 * @author s1011122
 * 
 */
public class Node  implements Comparable<Node>{
	/**
	 * The longitude of this node.
	 */
	private double longitude;
	/**
	 * The lattitude of this node;
	 */
	private double lattitude;
	/**
	 * The altidue of this node;
	 */
	private double altitude;
	/**
	 * Description of this node;
	 */
	private String description;
	/**
	 * Edges that are in-bound/terminate at this node
	 */
	private List<Edge> inboundEdges;
	/**
	 * Edges that are out-bound/originate from this node
	 */
	private List<Edge> outboudEdges;

	// These are used for the path finding and for keeping track  of the route used for pathfinding.
	/**
	 * Calucluated distance to this node from origin.
	 */
	private double distanceFromOrigin;
	/**
	 * The previous node in the path from the origin.
	 */
	private Node previousNodeInPath;

	public Node() {
		// set distance to largest possible double
		distanceFromOrigin = Double.MAX_VALUE;
		// For convenience initialise these with the constructor, dont worry about space.
		outboudEdges = new ArrayList<Edge>();
		inboundEdges = new ArrayList<Edge> ();
	}


	@Override
	public int compareTo(Node arg0) {
		if (this.getDistanceFromOrigin() < arg0.getDistanceFromOrigin())
			return -1;
		if (this.getDistanceFromOrigin() > arg0.getDistanceFromOrigin())
			return +1;
		return 0;
	}

	@Override
	public String toString() {
		return "PisteNode [description=" + description + "]";
	}


	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}


	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	/**
	 * @return the lattitude
	 */
	public double getLattitude() {
		return lattitude;
	}


	/**
	 * @param lattitude the lattitude to set
	 */
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}


	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}


	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the inboundEdges
	 */
	public List<Edge> getInboundEdges() {
		return inboundEdges;
	}


	/**
	 * @param inboundEdges the inboundEdges to set
	 */
	public void setInboundEdges(List<Edge> inboundEdges) {
		this.inboundEdges = inboundEdges;
	}


	/**
	 * @return the outboudEdges
	 */
	public List<Edge> getOutboudEdges() {
		return outboudEdges;
	}


	/**
	 * @param outboudEdges the outboudEdges to set
	 */
	public void setOutboudEdges(List<Edge> outboudEdges) {
		this.outboudEdges = outboudEdges;
	}


	/**
	 * @return the distanceFromOrigin
	 */
	public double getDistanceFromOrigin() {
		return distanceFromOrigin;
	}


	/**
	 * @param distanceFromOrigin the distanceFromOrigin to set
	 */
	public void setDistanceFromOrigin(double distanceFromOrigin) {
		this.distanceFromOrigin = distanceFromOrigin;
	}


	/**
	 * @return the previousNodeInPath
	 */
	public Node getPreviousNodeInPath() {
		return previousNodeInPath;
	}


	/**
	 * @param previousNodeInPath the previousNodeInPath to set
	 */
	public void setPreviousNodeInPath(Node previousNodeInPath) {
		this.previousNodeInPath = previousNodeInPath;
	}

}