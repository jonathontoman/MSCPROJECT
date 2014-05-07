package skipiste.algorithm.dijkstra;

import skipiste.algorithm.NodeDecorator;
import skipiste.graph.elements.Node;

public class DijkstraNode extends NodeDecorator{

	
	/**
	 * No argument constructor
	 */
	public DijkstraNode(Node n)
	{
		super();
		this.setNode(n);
	}	
}
