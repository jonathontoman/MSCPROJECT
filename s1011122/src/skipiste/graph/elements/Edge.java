package skipiste.graph.elements;

/**
 * Class to represent a section of a ski piste run, as an Edge within an object
 * graph.
 * 
 * @author s1011122
 * 
 */
public class Edge {
	/**
	 * String descriptor of this edge.
	 */
	private Difficulty diff;
	/**
	 * The Weight of this edge. Set to to a double as the edge weight should be null by default not zero.
	 */
	private Double weight;
	/**
	 * Id of Vertex/Node that comes to this edge.
	 */
	private Node from;
	/**
	 * Id of Vertex/Node we can reach from this edge.
	 */
	private Node to;

	/**
	 * The piste this edge is part of.
	 */
	private Piste piste;

	/**
	 * Minimal constructor.
	 */
	public Edge() {

	}

	/**
	 * Simple constructor
	 * @param from - the node this edge originates from. 
	 * @param to - the node the edge terminates at
	 * @param piste - the piste which this edge is part of.
	 */
	public Edge(Node from, Node to, Piste piste) {
		super();
		this.from = from;
		this.to = to;
		this.piste = piste;
	}
	
	/**
	 * Simple constructor
	 * @param from - the node this edge originates from. 
	 * @param to - the node the edge terminates at
	 * @param piste - the piste which this edge is part of.
	 */
	public Edge(Node from, Node to) {
		super();
		this.from = from;
		this.to = to;
	}

	/**
	 * @param piste
	 *            the piste to set
	 */
	public void setPiste(Piste piste) {
		this.piste = piste;
	}

	/**
	 * @return the diff
	 */
	public Difficulty getDiff() {
		return diff;
	}

	/**
	 * @param diff
	 *            the diff to set
	 */
	public void setDiff(Difficulty diff) {
		this.diff = diff;
	}

	/**
	 * @return the piste
	 */
	public Piste getPiste() {
		return piste;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * @return the from
	 */
	public Node getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(Node from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public Node getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(Node to) {
		this.to = to;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		if (!(obj instanceof Edge))
			return false;
		Edge other = (Edge) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}
}
