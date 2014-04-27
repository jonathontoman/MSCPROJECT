package skipiste.algorithm;

import java.util.HashSet;
import java.util.PriorityQueue;

import skipiste.graph.elements.GraphNode;
import skipiste.graph.elements.Node;

public abstract class AbstractSearchAlgorithm implements SearchAlgorithm {

	/**
	 * The starting node for our search
	 */
	protected GraphNode start;
	/**
	 * The end node for our search;
	 */
	protected GraphNode end;
	/**
	 * A queue of the nodes that we are going to consider for this algorithm.
	 */
	protected PriorityQueue<GraphNode> openList;	
	protected long duration;

	@Override
	public Path findPath(GraphNode start, GraphNode end) {

		this.start = start;
		this.end = end;
		openList = new PriorityQueue<GraphNode>();
		long startTime = System.currentTimeMillis();
		execute();
		duration = System.currentTimeMillis() - startTime;
		return new Path(this.end);
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
	 * @param start the start to set
	 */
	public void setStart(GraphNode start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public GraphNode getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(GraphNode end) {
		this.end = end;
	}

	/**
	 * @return the openList
	 */
	public PriorityQueue<GraphNode> getOpenList() {
		return openList;
	}

	/**
	 * @param openList the openList to set
	 */
	public void setOpenList(PriorityQueue<GraphNode> openList) {
		this.openList = openList;
	}

	/**
	 * @return the duration
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}
}
