package skipiste.algorithm.idastar;

import skipiste.algorithm.NodeDecorator;
import skipiste.graph.elements.Node;

public class IDAStarNode extends NodeDecorator
{
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
