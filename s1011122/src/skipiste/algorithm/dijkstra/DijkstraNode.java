package skipiste.algorithm.dijkstra;

import skipiste.algorithm.NodeDecorator;
import skipiste.graph.elements.Node;

/**
 * Specific implementation of a GraphNode used by the Dijkstra Algorithm
 * @author s1011122
 *
 */
public class DijkstraNode extends NodeDecorator{

	/**
	 * Constructor.
	 * @param n - node that this object wraps.
	 */
	public DijkstraNode(Node n)
	{
		super();
		this.setNode(n);
	}	
}
