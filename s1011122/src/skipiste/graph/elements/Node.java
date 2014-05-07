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
public class Node  {

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
	protected Set<Edge> inbound;
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


	/**
	 * No argmument constructor.
	 */
	public Node() {
		// For convenience initialise these with the constructor, don't worry
		// about space.
		outbound = new HashSet<Edge>();
		inbound = new HashSet<Edge>();
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
		this.inbound = new HashSet<Edge>(inboundEdges);
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
		return inbound;
	}

	/**
	 * @param inboundEdges
	 *            the inboundEdges to set
	 */
	public void setInboundEdges(Set<Edge> inboundEdges) {
		this.inbound = inboundEdges;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(altitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (end ? 1231 : 1237);
		result = prime * result + (intersection ? 1231 : 1237);
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (predicted ? 1231 : 1237);
		result = prime * result + (start ? 1231 : 1237);
		return result;
	}

	/* (non-Javadoc)
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
		if (predicted != other.predicted)
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

	/**
	 * @return the inbound
	 */
	public Set<Edge> getInbound() {
		return inbound;
	}

	/**
	 * @param inbound the inbound to set
	 */
	public void setInbound(Set<Edge> inbound) {
		this.inbound = inbound;
	}

	/**
	 * @return the outbound
	 */
	public Set<Edge> getOutbound() {
		return outbound;
	}

	/**
	 * @param outbound the outbound to set
	 */
	public void setOutbound(Set<Edge> outbound) {
		this.outbound = outbound;
	}
}