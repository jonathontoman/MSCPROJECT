package skipiste.algorithm.astar;

import skipiste.algorithm.NodeDecorator;
import skipiste.graph.elements.Node;

/**
 * Specific implementation of a GraphNode used by the AnytimeRepairingAStarAlgorithm
 * @author s1011122
 *
 */
public class AStarNode extends NodeDecorator
{
	
	/**
	 * Constructor.
	 * @param n - node that this object wraps.
	 */
	public AStarNode(Node n)
	{
		super();
		this.setNode(n);
	}
	
	
	@Override
	public int compareTo(NodeDecorator o) {
		if (this.getCost() + this.getHeuristic() < o.getCost()
				+ o.getHeuristic())
			return -1;
		if (this.getCost() + this.getHeuristic() > o.getCost()
				+ o.getHeuristic())
			return +1;
		return 0;
	}
}
