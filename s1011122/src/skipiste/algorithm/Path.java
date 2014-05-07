package skipiste.algorithm;

import java.util.LinkedList;

import skipiste.graph.elements.GraphNode;
import skipiste.utils.OutputKML;

/**
 * Represents a the path calculated by the route planning algorithms. Holds the
 * nodes visted and other data.
 * 
 * @author s1011122
 * 
 */
public class Path {

	/**
	 * The algorithm nodes that make up this path in order from start to finish.
	 */
	LinkedList<GraphNode> nodesInPath;

	/**
	 * The total distance of this route
	 */
	double distance;

	/**
	 * Builds a string of kml representing the path
	 * 
	 * @return
	 */
	public String printPath() {
		StringBuilder sb = new StringBuilder();
		for (GraphNode n : nodesInPath) {
			sb.append(OutputKML.outputPlaceMark(n.getLongitude(),
					n.getLatitude()));
			sb.append("\r\n");
		}
		return sb.toString();
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
		while (n.getPrevious() != null) {
			nodesInPath.addFirst(n);
			n = n.getPrevious();
		}
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

}
