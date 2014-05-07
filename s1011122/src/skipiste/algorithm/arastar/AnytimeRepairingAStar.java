package skipiste.algorithm.arastar;

import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

import skipiste.algorithm.AbstractSearchAlgorithm;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.GraphNode;
import skipiste.utils.OutputKML;
import skipiste.utils.distance.DistanceCalculator;
import skipiste.utils.distance.HaversineDistance;

/**
 * Anytime Repairing A Start (ARA*) Algorithm as adapted from pseudocode
 * provided by in Heuristic Search
 * 
 * @author s1011122
 * 
 */
public class AnytimeRepairingAStar extends AbstractSearchAlgorithm {

	double heuristicMultiplier;
	/**
	 * Current estimate of path weight
	 */
	double fValue;
	/**
	 * cost of the best path so far from the source node to the node under
	 * consideration(u) at the time of the last expansion.
	 */
	double iValue;
	/**
	 * cost of the best path so far from the source node to the node under
	 * consideration (u);
	 */
	double gValue;
	/**
	 * cost of the best path so far from source node to the destination.
	 */
	double distnaceToDesination;
	/**
	 * Distance to our origin node
	 */
	double distanceToOrigin;

	DistanceCalculator calc;
	
	HashSet<GraphNode> inconsistentList = new HashSet<GraphNode>();
	HashSet<GraphNode> closedList = new  HashSet<GraphNode>();

	/**
	 * Run AnytimeAStar algorithm against our graph.
	 * 
	 * @param s
	 *            - the start Node
	 * @param t
	 *            - the target Node.
	 */
	public void execute(GraphNode source, GraphNode destination) {

		// Initialise the lists.
		openList = new PriorityQueue<GraphNode>();
		closedList = new HashSet<GraphNode>();
		inconsistentList = new HashSet<GraphNode>();
		
		// Cost to the start node is zero as we are already at the start node.
		this.start.setCost(0);
		// add the start node to the openList
		openList.add(this.start);
		
		// set our heuristic multiplier
		heuristicMultiplier = 10;
		// first time round our previous multiplier will be 1
		int oldHeuristicMultiplier = 1;
		while (heuristicMultiplier >= 1) 
		{
			// add the contents of the inconsistentList to the openList as these will be reviewed again
			openList.addAll(inconsistentList);			
			updatePriorities(openList, oldHeuristicMultiplier, heuristicMultiplier);
			subroutine(source, destination);
		}
	}
	

	public void subroutine(GraphNode source, GraphNode destination) {

		closedList = new HashSet<GraphNode>();
		inconsistentList = new HashSet<GraphNode>();

		while (destination.getCost() > openList.peek()
				.getCost()) {
			GraphNode u = openList.poll();
			closedList.add(u);
			// set ivalue to equal the cost of the best paht found so far from
			iValue = u.getCost();
			
			for (Edge e : u.getOutboundEdges()) {
				// now we need to relax the PisteSections, examine each
				// destination Node of these PisteSections.
				GraphNode n = e.getTo();
				// is the new route to node n less than the current route?
				double distance = u.getCost()
						+ e.getWeight()
						+ (calc.calculateDistanceBetweenNodes(n, destination) * heuristicMultiplier);

				if (n.getCost() > distance) {
					n.setCost(distance);
					n.setPrevious(u);
					if (!closedList.contains(n)) {
						// No side effect if n is not already in queue, but this
						// is how we update it
						openList.remove(n);
						// set the previous Node so we can later rebuild the
						// path.
						openList.add(n);
					} else {
						inconsistentList = new HashSet<GraphNode>();
					}
				}
			}
		}

	}
	
	private void updatePriorities (PriorityQueue<GraphNode> queue, double oldMultiplier, double newMultiplier)
	{
		for (GraphNode n : queue)
		{
			// the current cost to the node n is the heuristicMultiplier  * real cost to node, so we will divide by the old multiplier and multiply by the new
			n.setCost((n.getCost()/oldMultiplier)* newMultiplier );
		}
	}

	public void printShortestPath(GraphNode destination) {
		while (destination.getPrevious() != null) {
			System.out.println(OutputKML.outputPlaceMark(destination
					.getPrevious().getLongitude(), destination
					.getPrevious().getLatitude()));
			destination = destination.getPrevious();
		}
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub

	}
}
