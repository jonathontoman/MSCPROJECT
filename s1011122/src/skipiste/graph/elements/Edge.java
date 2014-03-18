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
	 * The Weight of this edge.
	 */
	private double weight;
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
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(double weight) {
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
		result = prime * result + ((diff == null) ? 0 : diff.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((piste == null) ? 0 : piste.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (diff != other.diff)
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (piste == null) {
			if (other.piste != null)
				return false;
		} else if (!piste.equals(other.piste))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (Double.doubleToLongBits(weight) != Double
				.doubleToLongBits(other.weight))
			return false;
		return true;
	}
}
