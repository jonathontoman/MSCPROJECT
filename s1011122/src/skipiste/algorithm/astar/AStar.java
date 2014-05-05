package skipiste.algorithm.astar;

import java.util.HashSet;

import skipiste.algorithm.AbstractSearchAlgorithm;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.GraphNode;
import skipiste.utils.distance.DistanceCalculator;
import skipiste.utils.distance.HaversineDistance;

public class AStar extends AbstractSearchAlgorithm {

	/**
	 * Calculator to calculate distnace between nodes.
	 */
	DistanceCalculator calc;
	/**
	 * Nodes we have already visited
	 */
	HashSet<GraphNode> closedList;

	/**
	 * Run AStar's algorithm against our graph.
	 * 
	 * @param s
	 *            - the start Node
	 * @param t
	 *            - the target Node.
	 */
	public void execute() {

		calc = new HaversineDistance();

		// Cost to the start node is zero as we are already at the start node.
		this.start.setCost(0);
		openList.add(start);
		closedList = new HashSet<GraphNode>();
		GraphNode gNodeEnd = new AStarNode(end);

		while (!openList.isEmpty()) {
			// The Node we are currently search from
			GraphNode currentNode = openList.poll();

			if (currentNode.equals(gNodeEnd)) {
				return;
			}
			closedList.add(currentNode);
			// If this is our target we can give up searching as we know the
			// addition of a heuristic value of an underestimate of the distance
			// of a node to the destination node guarantees an optimal route
			// when we find our node, we don't need to be greedy and cycle
			// through all routes to our destination.1

			for (Edge e : currentNode.getOutboundEdges()) {
				// now we need to relax the PisteSections, examine each
				// destination Node of these PisteSections.
				GraphNode prospectiveNode = e.getTo();

				// get cost of getting to n from current Node u
				double cost = e.getWeight()
						+ calc.calculateDistanceBetweenNodes(currentNode,
								prospectiveNode) + currentNode.getCost();
				if (!openList.contains(prospectiveNode)
						&& !closedList.contains(prospectiveNode)) {
					// No side effect if n is not already in queue.
					prospectiveNode.setCost(cost);
					// set the previous Node so we can later rebuild the path.
					prospectiveNode.setPrevious(currentNode);
					openList.remove(prospectiveNode);
					openList.add(prospectiveNode);
				}
			}
		}
	}

}
