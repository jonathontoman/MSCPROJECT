package skipiste.algorithm;

import java.util.Set;

import skipiste.graph.elements.Edge;

import skipiste.graph.elements.Node;

/**
 * An abstract decorator class that the algorithm specific classes will
 * implement in order to add their extra functionality.
 * 
 * @author s1011122
 * 
 */
public abstract class NodeDecorator implements GraphNode,
		Comparable<NodeDecorator> {

	/**
	 * The cost to get to this node.
	 */
	protected double cost;
	/**
	 * The heuristic cost of this node. This will be used for comparrison
	 * against other graphNodes.
	 */
	protected double heuristic;
	/**
	 * The previous GraphNode in the path to this GraphNode.
	 */
	protected GraphNode previous;
	/**
	 * The detail of this node.
	 */
	protected Node node;

	/**
	 * the no argument constructor called by extending classes
	 */
	protected NodeDecorator() {
		// We don't know the cost to the current node when create it so set it
		// to
		// the maximum possible value;
		this.cost = Double.MAX_VALUE;

	}

	@Override
	public double getCost() {
		return cost;
	}

	@Override
	public void setCost(double cost) {
		this.cost = cost;

	}

	@Override
	public Set<Edge> getOutboundEdges() {
		return this.node.getOutbound();
	}

	@Override
	public GraphNode getPrevious() {
		return this.previous;
	}

	@Override
	public void setPrevious(GraphNode n) {
		this.previous = n;

	}

	@Override
	public double getLongitude() {
		return this.node.getLongitude();
	}

	@Override
	public double getLatitude() {
		return this.node.getLatitude();
	}

	@Override
	public int compareTo(NodeDecorator o) {
		if (this.getCost() < o.getCost())
			return -1;
		if (this.getCost() > o.getCost())
			return +1;
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((node == null) ? 0 : node.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof NodeDecorator))
			return false;
		NodeDecorator other = (NodeDecorator) obj;
		if (node == null) {
			if (other.node != null)
				return false;
		} else if (!node.equals(other.node))
			return false;
		return true;
	}

	/**
	 * @return the heuristic
	 */
	public double getHeuristic() {
		return heuristic;
	}

	/**
	 * @param heuristic
	 *            the heuristic to set
	 */
	public void setHeuristic(double heuristic) {
		this.heuristic = heuristic;
	}

	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @param node
	 *            the node to set
	 */
	public void setNode(Node node) {
		this.node = node;
	}

}
