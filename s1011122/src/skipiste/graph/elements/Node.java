package skipiste.graph.elements;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a single point on a ski piste map as a Node within a
 * graph.
 * 
 * @author s1011122
 * 
 */
public class Node implements GraphNode, Comparable<Node> {

	/**
	 * The longitude of this node.
	 */
	protected double longitude;
	/**
	 * The lattitude of this node;
	 */
	protected double latitude;
	/**
	 * The altidue of this node;
	 */
	protected double altitude;
	/**
	 * Name of the piste this belongs to.
	 */
	protected String name;
	/**
	 * Edges that are in-bound/terminate at this node
	 */
	protected Set<Edge> inboundEdges;
	/**
	 * Edges that are out-bound/originate from this node
	 */
	protected Set<Edge> outbound;
	/**
	 * The pistes this node belongs to.
	 */
	protected Set<Piste> pistes;

	/**
	 * Flag to show whether or not this start of a node.
	 */
	protected boolean start;
	/**
	 * Flag to show whether or not this start of a node.
	 */
	protected boolean end;

	/**
	 * Flag to indicate if this node has already been merged with another
	 */
	protected boolean intersection;

	/**
	 * Flag that shows us whether or not this node exists in the data source or
	 * whether this nodes existence at the intersection of two or more edges has
	 * been predicted by our algorithm.
	 */
	protected boolean predicted;

	// These are used for the path finding and for keeping track of the route
	// used for pathfinding.
	/**
	 * The assoicated cost to reach this node.
	 */
	protected double cost;
	/**
	 * The previous node in the path from the origin.
	 */
	protected GraphNode previous;

	/**
	 * No argmument constructor.
	 */
	public Node() {
		// set distance to largest possible double
		cost = Double.MAX_VALUE;
		// For convenience initialise these with the constructor, don't worry
		// about space.
		outbound = new HashSet<Edge>();
		inboundEdges = new HashSet<Edge>();
		pistes = new HashSet<Piste>();
		// by default assume this is not a predicted node.
		predicted = false;
		start = false;
		end = false;
		intersection = false;
	}

	/**
	 * Basic constructor
	 * 
	 * @param longitude
	 *            - longitude of this node
	 * @param lattitude
	 *            - latitude of this node
	 * @param - the piste that this node is part of.(we may find later it is
	 *        part of many)
	 * @param start
	 *            - is this node the start of a piste?
	 * @param end
	 *            - is this node the end of a piste?
	 */
	public Node(double longitude, double lattitude, boolean start, boolean end) {

		this();
		this.longitude = longitude;
		this.latitude = lattitude;
		this.start = start;
		this.end = end;
		this.intersection = false;

	}

	/**
	 * Complete constructor
	 * 
	 * @param longitude
	 *            - the longitude of this node.
	 * @param latitude
	 *            - the latitude of this node.
	 * @param altitude
	 *            - the altitude of this node.
	 * @param outboundEdges
	 *            -the edges that are outbound from this node.
	 * @param inboundEdges
	 *            - the edges that are inbound to this node.
	 * @param pistes
	 *            - the pistes this node is part of
	 * @param start
	 *            - is this node the start of a piste?
	 * @param end
	 *            - is this node the end of a piste?
	 * @param merged
	 *            - has this node been merged with any others?
	 * @param predicted
	 *            - was this node predicted by our graph builder?
	 */
	public Node(double longitude, double latitude, double altitude,
			Set<Edge> outboundEdges, Set<Edge> inboundEdges, Set<Piste> pistes,
			boolean start, boolean end, boolean merged, boolean predicted) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.altitude = altitude;
		this.outbound = new HashSet<Edge>(outboundEdges);
		this.inboundEdges = new HashSet<Edge>(inboundEdges);
		this.pistes = new HashSet<Piste>(pistes);
		this.start = start;
		this.end = end;
		this.intersection = merged;
		this.predicted = predicted;

	}

	@Override
	public String toString() {

		String pistename;
		StringBuilder sb = new StringBuilder();
		for (Piste p : this.pistes) {
			sb.append(p.getName());
			sb.append(":");
		}
		pistename = sb.toString();

		return pistename;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the lattitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param lattitude
	 *            the lattitude to set
	 */
	public void setLatitude(double lattitude) {
		this.latitude = lattitude;
	}

	/**
	 * @return the altitude
	 */
	public double getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude
	 *            the altitude to set
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	/**
	 * @return the description
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setName(String description) {
		this.name = description;
	}

	/**
	 * @return the inboundEdges
	 */
	public Set<Edge> getInboundEdges() {
		return inboundEdges;
	}

	/**
	 * @param inboundEdges
	 *            the inboundEdges to set
	 */
	public void setInboundEdges(Set<Edge> inboundEdges) {
		this.inboundEdges = inboundEdges;
	}

	/**
	 * @return the outboudEdges
	 */
	public Set<Edge> getOutboundEdges() {
		return outbound;
	}

	/**
	 * @param outboudEdges
	 *            the outboudEdges to set
	 */
	public void setOutboudEdges(Set<Edge> outboudEdges) {
		this.outbound = outboudEdges;
	}

	/**
	 * @return the distanceFromOrigin
	 */
	public double getDistanceFromOrigin() {
		return cost;
	}

	/**
	 * @param distanceFromOrigin
	 *            the distanceFromOrigin to set
	 */
	public void setDistanceFromOrigin(double distanceFromOrigin) {
		this.cost = distanceFromOrigin;
	}

	/**
	 * @return the previousNodeInPath
	 */
	public GraphNode getPrevious() {
		return previous;
	}

	/**
	 * @param previousNodeInPath
	 *            the previousNodeInPath to set
	 */
	public void setPrevious(GraphNode previousNodeInPath) {
		this.previous = previousNodeInPath;
	}

	/**
	 * @return the pistes
	 */
	public Set<Piste> getPistes() {
		return pistes;
	}

	/**
	 * @param pistes
	 *            the pistes to set
	 */
	public void setPistes(Set<Piste> pistes) {
		this.pistes = pistes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(altitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (end ? 1231 : 1237);
		result = prime * result + (intersection ? 1231 : 1237);
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pistes == null) ? 0 : pistes.hashCode());
		result = prime * result + (predicted ? 1231 : 1237);
		result = prime * result
				+ ((previous == null) ? 0 : previous.hashCode());
		result = prime * result + (start ? 1231 : 1237);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		if (Double.doubleToLongBits(altitude) != Double
				.doubleToLongBits(other.altitude))
			return false;
		if (Double.doubleToLongBits(cost) != Double
				.doubleToLongBits(other.cost))
			return false;
		if (end != other.end)
			return false;
		if (intersection != other.intersection)
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pistes == null) {
			if (other.pistes != null)
				return false;
		} else if (!pistes.equals(other.pistes))
			return false;
		if (predicted != other.predicted)
			return false;
		if (previous == null) {
			if (other.previous != null)
				return false;
		} else if (!previous.equals(other.previous))
			return false;
		if (start != other.start)
			return false;
		return true;
	}

	/**
	 * @return the start
	 */
	public boolean isStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(boolean start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public boolean isEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(boolean end) {
		this.end = end;
	}

	/**
	 * @return the intersection
	 */
	public boolean isIntersection() {
		return intersection;
	}

	/**
	 * @param intersection
	 *            the intersection to set
	 */
	public void setIntersection(boolean intersection) {
		this.intersection = intersection;
	}

	/**
	 * @return the predicted
	 */
	public boolean isPredicted() {
		return predicted;
	}

	/**
	 * @param predicted
	 *            the predicted to set
	 */
	public void setPredicted(boolean predicted) {
		this.predicted = predicted;
	}

	@Override
	public double getCost() {
		return cost;
	}

	@Override
	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public int compareTo(Node o) {
		if (this.getCost() < o.getCost())
			return -1;
		if (this.getCost() > o.getCost())
			return +1;
		return 0;
	}
}