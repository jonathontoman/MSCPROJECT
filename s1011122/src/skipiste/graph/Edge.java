package skipiste.graph;

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
	 * The name of the piste that this node belongs to.
	 */
	private String piste;

	/**
	 * Minimal constructor.
	 */
	public Edge() {

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

	/**
	 * @return the piste
	 */
	public String getPiste() {
		return piste;
	}

	/**
	 * @param piste
	 *            the piste to set
	 */
	public void setPiste(String piste) {
		this.piste = piste;
	}

}
