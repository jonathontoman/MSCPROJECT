package skipiste.algorithm;

import java.util.HashSet;
import java.util.PriorityQueue;

import skipiste.graph.elements.Node;

public abstract class AbstractAlgorithm 
{
	
	
	/**
	 * A queue of the nodes that we are going to consider for this algorithm.
	 */
	protected PriorityQueue<Node> openList;
	/**
	 * A List of all the nodes that have been visted and "completed" by the algorithm.
	 */
	protected HashSet<Node> closedList;
	/**
	 * The time it took in milliseconds to run the algorithm.
	 */
	protected double duration;
	
	protected Node start;
	protected Node end;
	
	
	/**
	 * Execute the algorithm, search from the start node for paths to the end node.
	 */
	public void execute(Node start, Node end)
	{
		double startTime = System.currentTimeMillis();
		this.start = start;
		this.end = end;
		// run algorithm
		// do some basic initialisation of data structures
		openList = new PriorityQueue<Node>();
		closedList = new HashSet<Node>();
		start.setDistanceFromOrigin(0);		
		run();
		duration = System.currentTimeMillis() - startTime;
	}
	
	public abstract void run(	);

	
	/**
	 * @return the openList
	 */
	public PriorityQueue<Node> getOpenList() {
		return openList;
	}
	/**
	 * @param openList the openList to set
	 */
	public void setOpenList(PriorityQueue<Node> openList) {
		this.openList = openList;
	}
	/**
	 * @return the closedList
	 */
	public HashSet<Node> getClosedList() {
		return closedList;
	}
	/**
	 * @param closedList the closedList to set
	 */
	public void setClosedList(HashSet<Node> closedList) {
		this.closedList = closedList;
	}
}
