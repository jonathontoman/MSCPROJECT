package skipiste.algorithm.dijkstra;

import skipiste.algorithm.AbstractSearchAlgorithm;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.GraphNode;
import skipiste.graph.elements.Node;

/**
 * Implementation of Djikstra's algorithm designed to run against a graph of
 * Nodes
 */
public class Dijkstra extends AbstractSearchAlgorithm {

	/**
	 * @{inheritDoc
	 */
	public void execute() {

		openList.add(this.start);
		start.setCost(0);

		while (!openList.isEmpty()) {
			// The Node we are currently search from
			GraphNode currentNode = openList.poll();
			// add the current node to the closed list as we have now visited it
			closedList.add(currentNode);
			// If this is our target we can give up searching as we know the
			// addition of a heuristic value of an underestimate of the distance
			// of a node to the destination node guarantees an optimal route
			// when we find our node, we don't need to be greedy and cycle
			// through all routes to our destination.1

			for (Edge e : currentNode.getOutboundEdges()) {
				// now we need to relax the PisteSections, examine each
				// destination Node of these PisteSections.
				Node prospectiveNode = e.getTo();
				
				// get cost of getting to n from current Node u
				double cost = e.getWeight();
				// find the distance to the Node being examined via the current
				// Node.
				double newCostToProspectiveNode = cost + currentNode.getCost();
				double oldCostToProspectiveNode = prospectiveNode.getCost();
				// if new distance is less than current distance to prospective
				// node then
				// update the information in the priority queue, thanks to
				// java.util.PriorityQueue we have to remove it and re add it
				if (newCostToProspectiveNode < oldCostToProspectiveNode) 
				{					
					// No side effect if n is not already in queue.
					prospectiveNode.setCost(newCostToProspectiveNode);
					// set the previous Node so we can later rebuild the path.
					prospectiveNode.setPrevious(currentNode);
					openList.remove(prospectiveNode);
					openList.add(prospectiveNode);			
				}
			}
		}
	}
}