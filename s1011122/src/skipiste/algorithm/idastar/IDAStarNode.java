package skipiste.algorithm.idastar;

import skipiste.algorithm.NodeDecorator;
import skipiste.graph.elements.Node;
/**
 * Specific implementation of a GraphNode used by the IterativeDeepeningAStar.java
 * @author s1011122
 *
 */
public class IDAStarNode extends NodeDecorator
{
	/**
	 * Constructor.
	 * @param n - node that this object wraps.
	 */
	public IDAStarNode(Node n)
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
	
	/**
	 * Returns the fValue of this node. the fValue is the cost to get to this node summed with the heuristic cost.
	 * @return
	 */
	public double fValue()
	{
		return this.cost + this.heuristic;
	}
}
