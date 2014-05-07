package skipiste.algorithm.dijkstra;

import java.util.HashSet;

import skipiste.algorithm.AbstractSearchAlgorithm;
import skipiste.algorithm.astar.AStarNode;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.GraphNode;
import skipiste.graph.elements.Node;

/**
 * Implementation of Djikstra's algorithm designed to run against a graph of
 * Nodes
 */
public class Dijkstra extends AbstractSearchAlgorithm<DijkstraNode> {

	/**
	 * @{inheritDoc
	 */
	public void execute() {

		// Cost to the start node is zero as we are already at the start node.
		this.start.setCost(0);
		openList.add(start);

		while (!openList.isEmpty()) {
			// The Node we are currently search from
			DijkstraNode currentNode = openList.poll();

			if (currentNode.equals(end)) {
				end = currentNode;
				return;
			}

			// If this is our target we can give up searching as we know the
			// addition of a heuristic value of an underestimate of the distance
			// of a node to the destination node guarantees an optimal route
			// when we find our node, we don't need to be greedy and cycle
			// through all routes to our destination.1

			for (Edge e : currentNode.getOutboundEdges()) {
				// now we need to relax the PisteSections, examine each
				// destination Node of these PisteSections.

				DijkstraNode prospectiveNode = new DijkstraNode(e.getTo());
				// if we have already evaluated this node then move on.
				if (closedList.contains(prospectiveNode))
					continue;
				// see if this is already on the priorityqueue, if so make sure
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
	protected void setStartNode(Node start) {
		this.start = new DijkstraNode(start);

	}

	@Override
	protected void setEndNode(Node end) {
		this.end = new DijkstraNode(end);

	}
}