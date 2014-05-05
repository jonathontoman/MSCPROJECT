package skipiste.algorithm;

import java.util.Set;

import skipiste.graph.elements.Edge;
import skipiste.graph.elements.GraphNode;

/**
 * An abstract decorator class that the algorithm specific classes will
 * implement in order to add their extra functionality.
 * 
 * @author s1011122
 * 
 */
public abstract class NodeDecorator implements GraphNode, Comparable<NodeDecorator> {
	protected GraphNode n;

	@Override
	public double getCost() {
		return this.n.getCost();
	}

	@Override
	public void setCost(double cost) {
		this.n.setCost(cost);

	}

	@Override
	public Set<Edge> getOutboundEdges() {
		return this.n.getOutboundEdges();
	}

	@Override
	public GraphNode getPrevious() {
		return this.n.getPrevious();
	}

	@Override
	public void setPrevious(GraphNode n) {
		this.n.setPrevious(n);

	}

	@Override
	public double getLongitude() {
		return this.n.getLongitude();
	}

	@Override
	public double getLatitude() {
		return this.n.getLatitude();
	}
	
	
	@Override
	public int compareTo(NodeDecorator o) {
		if (this.getCost() < o.getCost())
			return -1;
		if (this.getCost() > o.getCost())
			return +1;
		return 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((n == null) ? 0 : n.hashCode());
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
		if (!(obj instanceof NodeDecorator))
			return false;
		NodeDecorator other = (NodeDecorator) obj;
		if (n == null) {
			if (other.n != null)
				return false;
		} else if (!n.equals(other.n))
			return false;
		return true;
	}

}
