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
public class Node implements Comparable<Node> {
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
	 * The section of a piste that this node belongs to.
	 */
	private Section section;
	/**
	 * Name of the piste this belongs to.
	 */
	private String name;
	/**
	 * Edges that are in-bound/terminate at this node
	 */
	private Set<Edge> inboundEdges;
	/**
	 * Edges that are out-bound/originate from this node
	 */
	private Set<Edge> outboudEdges;
	/**
	 * The pistes this node belongs to.
	 */
	private Set<Piste> pistes;

	// These are used for the path finding and for keeping track of the route
	// used for pathfinding.
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
		// For convenience initialise these with the constructor, dont worry
		// about space.
		outboudEdges = new HashSet<Edge>();
		inboundEdges = new HashSet<Edge>();
		pistes = new HashSet<Piste>();
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
		
		String pistename;
		StringBuilder sb = new StringBuilder();		
		for (Piste p : this.pistes)			
		{
			sb.append(p.getName());
			sb.append(":");
		}
		pistename = sb.toString();
		
		
		
		return pistename + " " + section;
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
	public double getLattitude() {
		return lattitude;
	}

	/**
	 * @param lattitude
	 *            the lattitude to set
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
	public Set<Edge> getOutboudEdges() {
		return outboudEdges;
	}

	/**
	 * @param outboudEdges
	 *            the outboudEdges to set
	 */
	public void setOutboudEdges(Set<Edge> outboudEdges) {
		this.outboudEdges = outboudEdges;
	}

	/**
	 * @return the distanceFromOrigin
	 */
	public double getDistanceFromOrigin() {
		return distanceFromOrigin;
	}

	/**
	 * @param distanceFromOrigin
	 *            the distanceFromOrigin to set
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
	 * @param previousNodeInPath
	 *            the previousNodeInPath to set
	 */
	public void setPreviousNodeInPath(Node previousNodeInPath) {
		this.previousNodeInPath = previousNodeInPath;
	}

	/**
	 * @return the section
	 */
	public Section getSection() {
		return section;
	}

	/**
	 * @param section
	 *            the section to set
	 */
	public void setSection(Section section) {
		this.section = section;
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

// TODO  -invocation exception thrown when calling hashcode() due to null pointers.
//	/* (non-Javadoc)
//	 * @see java.lang.Object#hashCode()
//	 */
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		long temp;
//		temp = Double.doubleToLongBits(altitude);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
//		temp = Double.doubleToLongBits(distanceFromOrigin);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
//		result = prime * result
//				+ ((inboundEdges == null) ? 0 : inboundEdges.hashCode());
//		temp = Double.doubleToLongBits(lattitude);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
//		temp = Double.doubleToLongBits(longitude);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		result = prime * result
//				+ ((outboudEdges == null) ? 0 : outboudEdges.hashCode());
//		result = prime * result + ((pistes == null) ? 0 : pistes.hashCode());
//		result = prime
//				* result
//				+ ((previousNodeInPath == null) ? 0 : previousNodeInPath
//						.hashCode());
//		result = prime * result + ((section == null) ? 0 : section.hashCode());
//		return result;
//	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (Double.doubleToLongBits(altitude) != Double
				.doubleToLongBits(other.altitude))
			return false;
		if (Double.doubleToLongBits(distanceFromOrigin) != Double
				.doubleToLongBits(other.distanceFromOrigin))
			return false;
		if (inboundEdges == null) {
			if (other.inboundEdges != null)
				return false;
		} else if (!inboundEdges.equals(other.inboundEdges))
			return false;
		if (Double.doubleToLongBits(lattitude) != Double
				.doubleToLongBits(other.lattitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (outboudEdges == null) {
			if (other.outboudEdges != null)
				return false;
		} else if (!outboudEdges.equals(other.outboudEdges))
			return false;
		if (pistes == null) {
			if (other.pistes != null)
				return false;
		} else if (!pistes.equals(other.pistes))
			return false;
		if (previousNodeInPath == null) {
			if (other.previousNodeInPath != null)
				return false;
		} else if (!previousNodeInPath.equals(other.previousNodeInPath))
			return false;
		if (section != other.section)
			return false;
		return true;
	}

}