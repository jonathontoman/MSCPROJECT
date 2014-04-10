package skipiste.algorithm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

import skipiste.graph.Graph;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.utils.HaversineDistance;
import skipiste.utils.OutputKML;

/**
 * Anytime Repairing A Start (ARA*) Algorithm as adapted from pseudocode
 * provided by in Heuristic Search
 * 
 * @author s1011122
 * 
 */
public class AnytimeRepairingAStar {
	/**
	 * The priority queue holds the neighbouring Nodes we will want to visit,
	 * stored in its 'natural order'. The Nodes implement the comparable
	 * interface and this should be configures so that the natural order is that
	 * the nearest Node is returned when we poll it.
	 */
	PriorityQueue<Node> open;

	/**
	 * The graph the algorithm runs against.
	 */
	Graph g;

	HashSet<Node> closed;
	HashSet<Node> inconsistent;
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

	public AnytimeRepairingAStar(Graph graph) {
		this.g = graph;
	}

	/**
	 * Run AnytimeAStar algorithm against our graph.
	 * 
	 * @param s
	 *            - the start Node
	 * @param t
	 *            - the target Node.
	 */
	public void execute(Node source, Node destination) {

		// Initialise data structures
		this.open = new PriorityQueue<Node>();
		this.closed = new HashSet<Node>();
		this.inconsistent = new HashSet<Node>();
		// initialise values;

		// distance to our origin value is 0 (we start here)
		distanceToOrigin = 0;
		// set our heuristic multiplier
		heuristicMultiplier = 5;
		// infinite estimates
		distnaceToDesination = Double.MAX_VALUE;
		iValue = Double.MAX_VALUE;

		// set the distance from origin for source = 0
		source.setDistanceFromOrigin(distanceToOrigin);
		// add our start node to the priority queue
		open.add(source);
		// call subroutine
		subroutine(source, destination);
		// print current shortestpath
		printShortestPath(destination);
		while (heuristicMultiplier >= 1) {
			// double to hold previous multiplier
			double prevMultiplier = heuristicMultiplier;
			// reduce heuristicMultiplier
			heuristicMultiplier--;
			// move nodes from inconsistent set to open queue
			open = new PriorityQueue<Node>();
			open.addAll(inconsistent);
			// adjust the heuristic value with the multiplier on the nodes on
			// the open queue
			Iterator<Node> nodeIt = open.iterator();
			subroutine(source, destination);
			// print solution
			while (nodeIt.hasNext()) {
				Node u = nodeIt.next();
				// Adjust the distanceTo origin of this node with the new
				// heuristic
				// We know that the current distnace = incoming edge length +
				// haversince distance * heuristic multiplier

				// new distance = old distance - (haversineDistance to
				// desination * oldmultiplier) + (haversineDistance *
				// newmultiplier)
				u.setDistanceFromOrigin(u.getDistanceFromOrigin()
						- (HaversineDistance.calculateDistance(u, destination) * prevMultiplier)
						+ ((HaversineDistance.calculateDistance(u, destination) * heuristicMultiplier)));
				subroutine(source, destination);
				// print solution;
				printShortestPath(destination);

			}

		}

	}

	public void subroutine(Node source, Node destination) {
		closed = new HashSet<Node>();
		inconsistent = new HashSet<Node>();
		while (destination.getDistanceFromOrigin() > open.peek().getDistanceFromOrigin()) {
			Node u = open.poll();
			closed.add(u);
			// set ivalue to equal the cost of the best paht found so far from
			iValue = u.getDistanceFromOrigin();

			for (Edge e : u.getOutboudEdges()) {
				// now we need to relax the PisteSections, examine each
				// destination Node of these PisteSections.
				Node n = e.getTo();
				// is the new route to node n less than the current route?
				double distance = u.getDistanceFromOrigin()
						+ e.getWeight()
						+ (HaversineDistance.calculateDistance(n, destination) * heuristicMultiplier);

				if (n.getDistanceFromOrigin() > distance) {
					n.setDistanceFromOrigin(distance);
					n.setPreviousNodeInPath(u);
					if (!closed.contains(n)) {
						// No side effect if n is not already in queue, but this
						// is how we update it
						open.remove(n);
						// set the previous Node so we can later rebuild the
						// path.
						open.add(n);
					} else {
						inconsistent.add(n);
					}
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
