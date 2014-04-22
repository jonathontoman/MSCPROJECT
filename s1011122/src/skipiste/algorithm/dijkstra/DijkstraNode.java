package skipiste.algorithm.dijkstra;

import skipiste.graph.elements.Node;

/**
 * Node specifically adapted for the use of the Dijkstra Algorithm
 *
 */
public class DijkstraNode extends Node
{
	protected Node  node;
	protected double costToNode;
	protected DijkstraNode previousNodeInPath;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(costToNode);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		result = prime
				* result
				+ ((previousNodeInPath == null) ? 0 : previousNodeInPath
						.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DijkstraNode))
			return false;
		DijkstraNode other = (DijkstraNode) obj;
		if (Double.doubleToLongBits(costToNode) != Double
				.doubleToLongBits(other.costToNode))
			return false;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		if (previousNodeInPath == null) {
			if (other.previousNodeInPath != null)
				return false;
		} else if (!previousNodeInPath.equals(other.previousNodeInPath))
			return false;
		return true;
	}
}
