package skipiste.algorithm;

import java.util.LinkedList;

import skipiste.graph.elements.Node;

/**
 * AlgorithmNode adds additional functionality that is specific for graph
 * search algorithms to the Node class.
 * 
 * @author s1011122
 * 
 */
public abstract class AlgorithmNode extends Node  implements SearchAlgorithNode {

	 
	
	protected double costToNode;
	protected double distanceFromOrigin;
	protected Node previousNodeInPath;

	/**
	 * Build the AlgorithmNode class from the base of a standard Node
	 * 
	 * @param n
	 *            - the node that makes up the base of this class
	 */
	public AlgorithmNode(Node n) {
		super(n.getLongitude(), n.getLattitude(), n.getAltitude(), n
				.getOutboudEdges(), n.getInboundEdges(), n.getPistes(), n
				.isStart(), n.isEnd(), n.isMerged(), n.isPredicted());
		// cost to node is by default set to the max value;
		costToNode = Double.MAX_VALUE;

	}

	/**
	 * @return the costToNode
	 */
	public double getCostToNode() {
		return costToNode;
	}

	/**
	 * @param costToNode
	 *            the costToNode to set
	 */
	public void setCostToNode(double costToNode) {
		this.costToNode = costToNode;
	}

	/**
	 * @return the distanceFromOrigin
	 */
	public double getDistanceFromOrigin() {
		return distanceFromOrigin;
	}

	/**
	 * @param distanceFromOrigin
	 *            the distanceFromOrigin to set
	 */
	public void setDistanceFromOrigin(double distanceFromOrigin) {
		this.distanceFromOrigin = distanceFromOrigin;
	}

	/**
	 * @return the previousNodeInPath
	 */
	public Node getPreviousNodeInPath() {
		return previousNodeInPath;
	}

	/**
	 * @param previousNodeInPath
	 *            the previousNodeInPath to set
	 */
	public void setPreviousNodeInPath(Node previousNodeInPath) {
		this.previousNodeInPath = previousNodeInPath;
	}

	@Override
	public LinkedList<Node> getShortestPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double calculateCostToNode() {
		// TODO Auto-generated method stub
		return 0;
	}
}
