package skipiste.algorithm;


import java.util.Map;
import java.util.PriorityQueue;

import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;

public class Dijkstra {

	/**
	 * The priority queue holds the neighbouring Nodes we will want to visit,
	 * stored in its 'natural order'. The Nodes implement the comparable
	 * interface and this should be configures so that the natural order is that
	 * the nearest Node is returned when we poll it.
	 */
	PriorityQueue<Node> pq;
	/**
	 * Holds the distance to the Node from the source Node.
	 */
	Map<Node, Double> distances;

	// Source Node
	Node s;
	// Target Node
	Node t;

	/**
	 * Run AStar's algorithm against our graph.
	 * 
	 * @param s
	 *            - the start Node
	 * @param t
	 *            - the target Node.
	 */
	public void execute(Node source, Node destination) {

		// Log start time
		long start = System.currentTimeMillis();

		// find node that matches source in our graph

		// we know the distance to the source Node is 0, update out graph.
		source.setDistanceFromOrigin(0);

		// Start searching for our route.
		pq = new PriorityQueue<Node>();
		pq.add(source);

		while (!pq.isEmpty()) {
			// The Node we are currently search from
			Node u = pq.poll();
			// If this is our target we can give up searching as we know the
			// addition of a heuristic value of an underestimate of the distance
			// of a node to the destination node guarantees an optimal route
			// when we find our node, we don't need to be greedy and cycle through all routes to our destination.
			
			for (Edge e : u.getOutboudEdges()) {
				// now we need to relax the PisteSections, examine each
				// destination Node of these PisteSections.
				Node n = e.getTo();
				// get cost of getting to n from current Node u
				double weight = e.getWeight();
				// find the distance to the Node being examined via the current
				// Node.
				double distanceViaU = weight + u.getDistanceFromOrigin();
				// if new distance is less than current distance to Node n then
				// update the information in the priority queue, thanks to
				// java.util.PriorityQueue we have to remove it and re add it
				if (distanceViaU < n.getDistanceFromOrigin()) {
					// No side effect if n is not already in queue.
					pq.remove(n);
					n.setDistanceFromOrigin(distanceViaU);
					// set the previous Node so we can later rebuild the path.
					n.setPreviousNodeInPath(u);
					pq.add(n);
				}
			}
		}

		long duration = System.currentTimeMillis() - start;
		// print total time taken for the algorithm to run.
		System.out.println("Time taken = " + duration + " milliseconds");
	}

}
