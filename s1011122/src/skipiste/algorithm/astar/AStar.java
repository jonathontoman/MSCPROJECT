package skipiste.algorithm.astar;

import skipiste.algorithm.AbstractSearchAlgorithm;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.utils.distance.DistanceCalculator;
import skipiste.utils.distance.HaversineDistance;

public class AStar extends AbstractSearchAlgorithm<AStarNode> {

	/**
	 * Calculator to calculate distance between nodes.
	 */
	DistanceCalculator calc;

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

		while (!openList.isEmpty()) {
			// The Node we are currently search from
			AStarNode currentNode = openList.poll();

			if (currentNode.equals(end)) {
				end = currentNode;
				return;
			}

			// If this is our target we can give up searching as we know the
			// addition of a heuristic value of an underestimate of the distance
			// of a node to the destination node guarantees an optimal route
			// when we find our node, we don't need to be greedy and cycle
			// through all routes to our destination.1

			// increment node expansion counter
			nodeCount++;
			for (Edge e : currentNode.getOutboundEdges()) {
				// now we need to relax the PisteSections, examine each
				// destination Node of these PisteSections.

				AStarNode prospectiveNode = new AStarNode(e.getTo());
				// Set the heuristic value;
				prospectiveNode.setHeuristic(calc
						.calculateDistanceBetweenCoordinates(
								prospectiveNode.getLongitude(),
								prospectiveNode.getLatitude(),
								end.getLongitude(), end.getLatitude()));
				// if we have already evaluated this node then move on.
				if (closedList.contains(prospectiveNode))
					continue;
				// see if this is already on the priority queue, if so make sure
				// we get that a reference to that object
				prospectiveNode = findInQueue(prospectiveNode);

				// get cost of getting to n from current Node u
				double cost = e.getWeight() + currentNode.getCost();

				if (cost < prospectiveNode.getCost()) {
					// No side effect if prospectiveNode is not already in
					// queue.
					prospectiveNode.setCost(cost);
					// set the previous Node so we can later rebuild the path.
					prospectiveNode.setPrevious(currentNode);
					openList.remove(prospectiveNode);
					openList.add(prospectiveNode);
				}
			}
			closedList.add(currentNode);
		}
	}

	@Override
	protected AStarNode buildSpecificNode(Node n) {
		return new AStarNode(n);
	}
}
