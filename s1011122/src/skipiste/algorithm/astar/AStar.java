package skipiste.algorithm.astar;

import java.util.HashSet;

import skipiste.algorithm.AbstractSearchAlgorithm;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.GraphNode;
import skipiste.graph.elements.Node;
import skipiste.utils.OutputKML;
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

		while (!openList.isEmpty()) {
			// The Node we are currently search from
			AStarNode currentNode = (AStarNode) openList.poll();
			// If this is our target we can give up searching as we know the
			// addition of a heuristic value of an underestimate of the distnace
			// of a node to the destination node guarantees an optimal route
			// when we find our node, we don't need to be greedy and cycle
			// through all routes to our destination.
			if (currentNode.equals(end)) {
				end = currentNode;
				break;
			}

			for (Edge e : currentNode.getOutboudEdges()) {
				// now we need to relax the PisteSections, examine each
				// destination Node of these PisteSections.
				AlgorithmNode n  = new AlgorithmNode(e.getTo());
				// get cost of getting to n from current Node u
				// We need to add our heuristic value with A* and adjust the
				// weight of this edge to n
				e.setWeight(e.getWeight()
						+ calc.calculateDistanceBetweenNodes(currentNode,n));

				double weight = e.getWeight();
				// find the distance to the Node being examined via the current
				// Node.
				double distanceViaU = weight + currentNode.getCost();
				// if new distance is less than current distance to Node n then
				// update the information in the priority queue, thanks to
				// java.util.PriorityQueue we have to remove it and re add it
				if (distanceViaU < n.getCost()) {
					// No side effect if n is not already in queue.
					openList.remove(n);
					n.setCost(distanceViaU);
					// set the previous Node so we can later rebuild the path.
					n.setPreviousNodeInPath(currentNode);
					openList.add(n);
				}
			}
		}
	}

	public void printShortestPath(Node destination) {
		while (destination.getPreviousNodeInPath() != null) {
			System.out.println(OutputKML.outputPlaceMark(destination
					.getPreviousNodeInPath().getLongitude(), destination
					.getPreviousNodeInPath().getLattitude()));
			destination = destination.getPreviousNodeInPath();
		}
	}

}
