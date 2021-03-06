package skipiste.algorithm;

import java.util.LinkedList;

import skipiste.algorithm.GraphNode;
import skipiste.utils.OutputKML;

/**
 * Represents a the path calculated by the route planning algorithms. Holds the
 * nodes visited and other data.
 * 
 * @author s1011122
 * 
 */
public class Path {

	/**
	 * The algorithm nodes that make up this path in order from start to finish.
	 */
	private LinkedList<GraphNode> nodesInPath;

	/**
	 * The total distance of this route
	 */
	private double distance;
	/**
	 * The name of the algorithm that built this path.
	 */
	private String algorithmName;

	/**
	 * Builds a string of kml representing the path
	 * 
	 * @return
	 */
	public String printPath() {
		return OutputKML.outputRoute(nodesInPath);
	}

	/**
	 * No argument constructor, initialises the list of nodes to an empty linked
	 * list.
	 */
	public Path() {
		nodesInPath = new LinkedList<GraphNode>();
	}

	/**
	 * Constructor, creates the path by recursivly going through the previous
	 * node references in the AlgorithmNode
	 * 
	 * @param n
	 *            - the desintation node
	 */
	public Path(GraphNode n) {
		this();
		distance = n.getCost();
		while (true) {
			nodesInPath.addFirst(n);

			if (n.getPrevious() != null) {
				n = n.getPrevious();
			} else {
				break;
			}
		}

	}

	/**
	 * Builds path from end node n with name.
	 */
	public Path(GraphNode n, String name) {
		this(n);
		this.algorithmName = name;
	}

	/**
	 * @return the nodesInPath
	 */
	public LinkedList<GraphNode> getNodesInPath() {
		return nodesInPath;
	}

	/**
	 * @param nodesInPath
	 *            the nodesInPath to set
	 */
	public void setNodesInPath(LinkedList<GraphNode> nodesInPath) {
		this.nodesInPath = nodesInPath;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance
	 *            the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return algorithmName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.algorithmName = name;
	}

}
