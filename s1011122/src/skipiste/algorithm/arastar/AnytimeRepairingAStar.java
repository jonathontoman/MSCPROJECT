package skipiste.algorithm.arastar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import skipiste.algorithm.AbstractSearchAlgorithm;
import skipiste.algorithm.Path;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;

/**
 * Anytime Repairing A Start (ARA*) Algorithm as adapted from pseudocode
 * provided by in Heuristic Search
 * 
 * @author s1011122
 * 
 */
public class AnytimeRepairingAStar extends AbstractSearchAlgorithm<ARAStarNode> {

	int heuristicMultiplier;
	HashSet<ARAStarNode> inconsistent = new HashSet<ARAStarNode>();

	/**
	 * @{inheritDoc
	 */
	public void execute() {

		// start heuristic mutliplier at 10.
		heuristicMultiplier = 10;
		// cost to reach start node is 0
		start.setCost(0);
		start.setHeuristic(heuristic(start));
		// add our start node to the open list to begin with.
		openList.add(start);
		start.setHeuristicMultiplier(heuristicMultiplier);

		while (heuristicMultiplier >= 1) {

			nodeCount = 0;
			openList.addAll(inconsistent);

			// remove all elements to queue and read them after the heuristic is
			// changed to change the order based on new heurisitc

			List<ARAStarNode> nodes = new ArrayList<ARAStarNode>();
			for (ARAStarNode n : openList) {
				n.setHeuristicMultiplier(heuristicMultiplier);
				nodes.add(n);
			}

			openList.removeAll(nodes);
			openList.addAll(nodes);

			ara();
			// Print out the route so far and some detail.
			System.out.println("ARA* output for inflation factor "
					+ heuristicMultiplier);
			System.out.println("Number of Nodes expanded: " + nodeCount);
			System.out.println("Time taken = "
					+ (System.currentTimeMillis() - startTime));
			System.out.println("Path Length (meters) = " + end.getCost());
			System.out.println("Path Route KML: ");
			System.out.println(new Path(end,
					"Anytime Repairing A Star with inflation factor "
							+ heuristicMultiplier).printPath());

			// decrease the multiplier
			heuristicMultiplier--;
		}
	}

	/**
	 * This method carries out the search
	 */
	private void ara() {

		// reset the closed list
		closedList = new HashSet<ARAStarNode>();
		// reset the inconsistent list
		inconsistent = new HashSet<ARAStarNode>();

		// repeat while there are nodes on the openList AND the cost to reach
		// that node + (heuristic * heuristic multiplier) is less than the
		// currently calculate cost to get to the end node
		while (openList.peek() != null
				&& openList.peek().getFCost() < end.getFCost()) {
			// The Node we are currently search from
			ARAStarNode currentNode = openList.poll();
			closedList.add(currentNode);

			// termination criteria
			if (currentNode.equals(end)) {
				end = currentNode;
				return;
			}
			// set the inconsistency value of this node.
			currentNode.setInconsistentCost(currentNode.getCost());
			nodeCount++;
			for (Edge e : currentNode.getOutboundEdges()) {
				// now we need to relax the PisteSections, examine each
				// destination Node of these PisteSections.

				ARAStarNode prospectiveNode = new ARAStarNode(e.getTo());
				// see if this is already on the priorityQueue, if so make sure
				// we get that a reference to that object
				prospectiveNode = findInQueue(prospectiveNode);
				prospectiveNode.setHeuristicMultiplier(heuristicMultiplier);

				// set the heuristic cost if it hasnt already been set
				if (prospectiveNode.getHeuristic() == 0) {
					prospectiveNode.setHeuristic(heuristic(prospectiveNode));
				}
				// get cost of getting to prospective node from current node.
				double cost = e.getWeight() + currentNode.getCost();

				// if the new cost is less than the old cost update/
				if (cost < prospectiveNode.getCost()) {
					prospectiveNode.setCost(cost);

					// if this node has already been expanded add it to the
					// inconsistent list
					if (closedList.contains(prospectiveNode)) {
						inconsistent.add(prospectiveNode);
					} else {

						// set the previous Node so we can later rebuild the
						// path.
						prospectiveNode.setPrevious(currentNode);
						openList.remove(prospectiveNode);
						openList.add(prospectiveNode);
					}
				}
			}
			// we have now dealt with the current node, add it to the closed list.
			closedList.add(currentNode);
		}
	}

	@Override
	protected ARAStarNode buildSpecificNode(Node n) {
		return new ARAStarNode(n);
	}

	@Override
	protected void setAlgorithmName() {
		this.algorithmName = "AnytimeRepairingAStar";
	}

}