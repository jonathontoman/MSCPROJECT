package skipiste.algorithm.dijkstra;

import skipiste.algorithm.AbstractAlgorithm;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;

public class Dijkstra extends AbstractAlgorithm {

	/**
	 * Run AStar's algorithm against our graph.
	 */
	public void run() {

		
		start.setDistanceFromOrigin(0);
		openList.add(start);
		
		while (!openList.isEmpty()) {
			// The Node we are currently search from
			Node currentNode = openList.poll();
			// If this is our target we can give up searching as we know the
			// addition of a heuristic value of an underestimate of the distance
			// of a node to the destination node guarantees an optimal route
			// when we find our node, we don't need to be greedy and cycle
			// through all routes to our destination.

			for (Edge e : currentNode.getOutboudEdges()) {
				// now we need to relax the PisteSections, examine each
				// destination Node of these PisteSections.
				Node prospectiveNode = e.getTo();
				// get cost of getting to n from current Node u
				double cost = e.getWeight();
				// find the distance to the Node being examined via the current
				// Node.
				double costViaCurrentNode = cost
						+ currentNode.getDistanceFromOrigin();
				// if new distance is less than current distance to Node n then
				// update the information in the priority queue, thanks to
				// java.util.PriorityQueue we have to remove it and re add it
				if (costViaCurrentNode < prospectiveNode
						.getDistanceFromOrigin()) {
					// No side effect if n is not already in queue.
					openList.remove(prospectiveNode);
					prospectiveNode.setDistanceFromOrigin(costViaCurrentNode);
					// set the previous Node so we can later rebuild the path.
					prospectiveNode.setPreviousNodeInPath(currentNode);
					openList.add(prospectiveNode);
				}
			}
		}
	}
}