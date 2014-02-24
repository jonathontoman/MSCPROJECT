package skipiste.algorithm;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

import skipiste.graph.Edge;
import skipiste.graph.Graph;
import skipiste.graph.Node;
import skipiste.utils.HaversineDistance;

public class AStar {

	private Graph g;
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

	public AStar(Graph graph) {

		this.g = graph;

	}

	/**
	 * Run AStar's algorithm against our graph.
	 * 
	 * @param s
	 *            - the start Node
	 * @param t
	 *            - the target Node.
	 */
	public void execute(String sourceNode, String targetNode) {

		s = g.getNodes().get(sourceNode);
		t = g.getNodes().get(targetNode);
		// we know the distance to the source Node is 0, update out graph.
		s.setDistanceFromOrigin(0);
		
		ArrayList<Node> Node = new ArrayList<Node>();
		Iterator it = g.getNodes().entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        
	        // For each node in the A* algorithm we need to add a weight + heuristic, our heuristic is the distance from the destination, to do this we will get the straight line distance of this node to the destination and add this too the weight of any incoming nodes.	        
	        Node n = (Node) pairs.getValue();
	        for (Edge e : n.getInboundEdges())
	        {
	        	double newWeight = e.getWeight() + HaversineDistance.calculateDistance(n, t); 
	        	e.setWeight(newWeight); 
	        }
	        Node.add(n);
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
		pq = new PriorityQueue<Node>();
		pq.add(s);

		// Log start time
		long start = System.currentTimeMillis();
		while (!pq.isEmpty()) {
			// The Node we are currently search from
			Node u = pq.poll();
			// If this is our target we can give up searching
			if ( u.equals(t))
			{
				t= u;
			}

			for (Edge e : u.getOutboudEdges()) {
				// now we need to relax the PisteSections, examine each destination Node of these PisteSections.
				Node n = e.getTo();
				// get cost of getting to n from current Node u
				double weight = e.getWeight();
				// find the distance to the Node being examined via the current
				// Node.
				double distanceViaU = weight + u.getDistanceFromOrigin();
				// if new distance is less than current  distance to Node n then
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

	public void printShortestPath() {
		ArrayList<Node> path = new ArrayList<Node>();
		for (Node n = t; n != null; n = n.getPreviousNodeInPath()) {
			path.add(n);
		}
		Collections.reverse(path);
		System.out.println(path);
	}
}
