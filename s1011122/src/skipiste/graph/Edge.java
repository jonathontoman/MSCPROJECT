package skipiste.graph;

/**
 * Class to represent a section of a ski piste run, as an Edge within an object
 * graph.
 * 
 * @author s1011122
 * 
 */
public class Edge 
{	
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
	 * Minimal constructor.
	 */
	public Edge() {

	}


}
