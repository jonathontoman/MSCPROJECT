package skipiste.algorithm;

import java.util.HashSet;
import java.util.PriorityQueue;

import skipiste.algorithm.GraphNode;
import skipiste.graph.elements.Node;

public abstract class AbstractSearchAlgorithm<T extends GraphNode> implements
		SearchAlgorithm {

	/**
	 * The starting node for our search
	 */
	protected T start;
	/**
	 * The end node for our search;
	 */
	protected T end;
	/**
	 * A queue of the nodes that we are going to consider for this algorithm.
	 */
	protected PriorityQueue<T> openList;
	/**
	 * A list of the nodes that we have already visited for this algorithm
	 */
	protected HashSet<T> closedList;

	/**
	 * The duration of the algorithm.
	 */
	protected long duration;
	
	protected long startTime;

	/**
	 * The number of nodes that are expanded by the algorithm.
	 */
	protected int nodeCount;
	
	protected String algorithmName;

	@Override
	public Path findPath(Node start, Node end) {
		
		setAlgorithmName();
		nodeCount =0;
		openList = new PriorityQueue<T>();
		closedList = new HashSet<T>();
		setStartNode(start);
		setEndNode(end);
		startTime = System.currentTimeMillis();
		execute();
		duration = System.currentTimeMillis() - startTime;
		return new Path(this.end,  algorithmName);
	}

	/**
	 * Searches the openList to see if an object passed to this method already
	 * exists on the openList. This calls .equals on each object on the
	 * openList, if we find an object that matches we return the object that was
	 * on the openlist ,if not we return the object that was passed as an
	 * argument to this method.
	 * 
	 * @param graphNode
	 *            - the node we want to see if we already have it on the
	 *            openList;
	 * @return - if there is an equal object on the openList return that, if not
	 *         reutrn this object passed to this method.
	 */
	protected T findInQueue(T node) {
		for (T node2 : openList) {
			if (node.equals(node2)) {

				return node2;
			}
		}
		return node;
	}

	/**
	 * Build an node for the algorithm specific implementation.
	 * 
	 * @return
	 */
	protected abstract T buildSpecificNode(Node n);
	
	protected abstract void setAlgorithmName();

	/**
	 * Build the algorithm specific end node
	 * 
	 * @param start
	 *            - the algorithm specific start node.
	 */
	protected void setStartNode(Node start) {
		this.start = buildSpecificNode(start);
	}

	/**
	 * Build the algorithm specific end node.
	 * 
	 * @param end
	 *            - the algorithm specific end node.
	 */
	protected void setEndNode(Node end) {
		this.end = buildSpecificNode(end);
	}

	/**
	 * Execute the algorithm
	 */
	protected abstract void execute();

	/**
	 * @return the start
	 */
	public GraphNode getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(T start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public GraphNode getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(T end) {
		this.end = end;
	}

	/**
	 * @return the openList
	 */
	public PriorityQueue<T> getOpenList() {
		return openList;
	}

	/**
	 * @param openList
	 *            the openList to set
	 */
	public void setOpenList(PriorityQueue<T> openList) {
		this.openList = openList;
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * @return the nodeCount
	 */
	public int getNodeCount() {
		return nodeCount;
	}

	/**
	 * @param nodeCount the nodeCount to set
	 */
	public void setNodeCount(int nodeCount) {
		this.nodeCount = nodeCount;
	}
}
