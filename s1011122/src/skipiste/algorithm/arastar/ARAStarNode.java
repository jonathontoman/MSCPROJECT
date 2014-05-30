package skipiste.algorithm.arastar;

import skipiste.algorithm.NodeDecorator;
import skipiste.graph.elements.Node;

public class ARAStarNode extends NodeDecorator
{
	
	/**
	 * The inconsistentCost value is the estimate of a start distance, the estimate value of a node
	 * will always be equal to the cost of the best path from the source node,
	 * to the current node under consideration at the time that we last expanded
	 * the current node. If that node has not been expanded, then the inconsistentCost value is
	 * infinity.
	 */
	double inconsistentCost;
	
	/**
	 * The multiplier that we use to inflate the heuristic.
	 */
	int heuristicMultiplier;
	
	
	public ARAStarNode(Node n)
	{
		super();
		this.inconsistentCost = Double.MAX_VALUE;
		this.setNode(n);
	}
	
	
	@Override
	public int compareTo(NodeDecorator o) {
		if (this.getCost() +  this.getHeuristic() < o.getCost()
				+ o.getHeuristic())
			return -1;
		if (this.getCost() + this.getHeuristic() > o.getCost()
				+ o.getHeuristic())
			return +1;
		return 0;
	}
	
	/**
	 * Return the f cost to get to this node, that the actual calculated cost to this node + the heuristc value of this node.
	 * @return
	 */
	public double getFCost()
	{
		return this.getCost() + (this.heuristic * heuristicMultiplier);
	}

	
	@Override
	public double getHeuristic()
	{
		return heuristic * heuristicMultiplier;
	}
	

	/**
	 * @return the inconsistentCost
	 */
	public double getInconsistentCost() {
		return inconsistentCost;
	}


	/**
	 * @param inconsistentCost the inconsistentCost to set
	 */
	public void setInconsistentCost(double inconsistentCost) {
		this.inconsistentCost = inconsistentCost;
	}


	/**
	 * @return the heuristicMultiplier
	 */
	public int getHeuristicMultiplier() {
		return heuristicMultiplier;
	}


	/**
	 * @param heuristicMultiplier the heuristicMultiplier to set
	 */
	public void setHeuristicMultiplier(int heuristicMultiplier) {
		this.heuristicMultiplier = heuristicMultiplier;
	}
}
