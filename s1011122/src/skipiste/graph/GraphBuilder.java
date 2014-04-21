//package skipiste.graph;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//
//import skipiste.geometry.LineSegment;
//import skipiste.geometry.Point;
//import skipiste.graph.elements.Edge;
//import skipiste.graph.elements.Node;
//import skipiste.graph.elements.Piste;
//import skipiste.importer.kml.KMLHandler;
//import skipiste.importer.kml.KMLImporter;
//import skipiste.importer.kml.SkiMapHandler;
//import skipiste.utils.distance.HaversineDistance;
//
///**
// * Builds the graph from the basic data set.
// * 
// * @author s1011122
// * 
// */
//public class GraphBuilder {
//
//	/**
//	 * The graph we are building
//	 */
//	private Graph graph;
//
//	/**
//	 * The nodes in the graph;
//	 */
//	private HashSet<Node> nodes;
//
//	/**
//	 * The edges in the graph;
//	 */
//	private HashSet<Edge> edges;
//
//	/**
//	 * The pistes in the graph;
//	 */
//	private HashSet<Piste> pistes;
//
//	/**
//	 * Constructor
//	 */
//	public GraphBuilder(String kmlFile) {
//
//		System.out.println("Importing KML");
//		KMLHandler handler = new SkiMapHandler();
//		KMLImporter importer = new KMLImporter(handler, kmlFile);
//
//		nodes = new HashSet<Node>(importer.getNodes());
//		pistes = new HashSet<Piste>(importer.getPistes());
//		edges = new HashSet<Edge>();
//		System.out.println("Creating the Edges for the first time");
//		// build the edges
//		createEdges(pistes);
//		// predict new nodes that will join up our existing pistes.
//		System.out.println("Predicting new nodes");
//		predictNewNodes();
//
//		System.out.println("Merging nodes that are in close proximity");
//		mergeSimilarNodes(10, 20);
//		// rebuild the edges
//		System.out.println("Destroying the old edges");
//		edges = new HashSet<Edge>();
//		// remove referneces to edges on the nodes.
//		for (Node n : nodes) {
//			n.setInboundEdges(new HashSet<Edge>());
//			n.setOutboudEdges(new HashSet<Edge>());
//
//		}
//		System.out.println("Rebuilding the edges");
//		createEdges(pistes);
//		System.out.println("Adding the edge weights");
//		// add the weights of the edges
//		for (Edge e : edges) {
//			e.setWeight(HaversineDistance.calculateDistance(e.getFrom(),
//					e.getTo()));
//		}
//
//		// remove duplicate nodes in a piste;
//		// removeDuplicateNodes(pistes);
//
//		// create the graph object
//		graph = new Graph(pistes, nodes, edges);
//
//		for (Edge e : edges) {
//			checkForOriginAndTerminusTheSame(e);
//		}
//
//	}
//
//	/**
//	 * Constructor for unit testing only, if time allows we will use easymock to
//	 * mock out calls to the kml handler in the constructor.
//	 * 
//	 * @param graph
//	 * @param nodes
//	 * @param edges
//	 * @param pistes
//	 */
//	public GraphBuilder() {
//		super();
//		this.nodes = new HashSet<Node>();
//		this.edges = new HashSet<Edge>();
//		this.pistes = new HashSet<Piste>();
//	}
//
//	/**
//	 * Create the edges between the nodes in each piste.
//	 * 
//	 * @param pistes
//	 */
//	protected void createEdges(HashSet<Piste> pistes) {
//
//		for (Piste p : pistes) {
//			for (int i = 1; i < p.getNodes().size(); i++) {
//				Node origin = (Node) p.getNodes().toArray()[i - 1];
//				Node terminus = (Node) p.getNodes().toArray()[i];
//
//				if (origin.equals(terminus)) {
//					System.out
//							.println("Creating an Edge with the same origing and terminus.");
//				}
//				Edge e = new Edge(origin, terminus, p);
//				edges.add(e);
//
//				origin.getOutboudEdges().add(e);
//				terminus.getInboundEdges().add(e);
//			}
//			// System.out.println("Added Edges for Piste : " + p);
//		}
//
//	}
//
//	/**
//	 * Merges nodes with similar geo coordinates, that are within X meters of
//	 * each other. Has two roles, merges nodes that are marked as intersections
//	 * that are within X meters of each other. Merges nodes that are marked as
//	 * either start or end that are within Y metes of each other.
//	 * 
//	 * @param X
//	 *            - merge nodes that we predicted exist if they are with X
//	 *            meters of each other
//	 * @param Y
//	 *            - merge nodes that are marked as either start or end with
//	 *            other start or end nodes if they are within Y meters of each
//	 *            other.
//	 */
//	protected void mergeSimilarNodes(double x, double y) {
//		// First merge all the start and end nodes so that we can join up pistes
//
//		// Step 1 : get an ArrayList<Node> of nodes that we are going to pass to
//		// our merging method
//		ArrayList<Node> startEndNodes = new ArrayList<Node>();
//		// add all of the start and end nodes to this arrayList
//		for (Node n : nodes) {
//			if (n.isStart() || n.isEnd()) {
//				startEndNodes.add(n);
//			}
//		}
//
//		// remove these merge candidates from our parent collection
//		nodes.removeAll(startEndNodes);
//		// pass to our merging method
//		mergeNodes(startEndNodes, y);
//
////		// Step 2 do the same for the predicted nodes
////		ArrayList<Node> predictedNodes = new ArrayList<Node>();
////		// add all of the start and end nodes to this arrayList
////		for (Node n : nodes) {
////			if (n.isStart() || n.isEnd()) {
////				predictedNodes.add(n);
////			}
////		}
////
////		// remove these merge candidates from our parent collection
////		nodes.removeAll(predictedNodes);
////		// pass to our merging method
////		mergeNodes(predictedNodes, y);
//
//	}
//
//	// protected void mergeSimilarNodes(double x, double y) {
//	//
//	// // We need to compare every element in the list of nodes against every
//	// // other element, and merge it if neccessary
//	// // each time we merge a node add it to a collection to be removed from
//	// // the list of nodes and mark it so we do not compare it again
//	//
//	// // keep track of the nodes we need to remove
//	// ArrayList<Node> removal = new ArrayList<Node>();
//	//
//	// //
//	// ArrayList<Node> nodeList = new ArrayList<Node>(nodes);
//	//
//	// for (int i = 0; i < nodeList.size(); i++) {
//	// Node n1 = nodeList.get(i);
//	// for (int j = i + 1; j < nodeList.size(); j++) {
//	// Node n2 = nodeList.get(j);
//	// // if n1 is a start or end node AND n2 is either a start or an
//	// // end node AND neither n1 or n2 has been marked as already
//	// // merged then we will see if these nodes can be merged, if so
//	// // then add n2 to the list that needs to be removed.
//	// if ((n1.isStart() || n1.isEnd())
//	// && (n2.isStart() || n2.isEnd())
//	// && (!n2.isMerged() && !n1.isMerged())) {
//	// if (HaversineDistance.calculateDistance(n1, n2) <= y) {
//	// n2.setMerged(true);
//	// // add n2 to the list of nodes to remove
//	// removal.add(n2);
//	// // merge n2 into n1
//	// mergeNode(n1, n2);
//	// }
//	// }
//	// // If both nodes are predicted then attempt to merge them
//	// else if (n1.isPredicted() && n2.isPredicted() && !n1.isMerged()
//	// && !n2.isMerged()) {
//	// if (HaversineDistance.calculateDistance(n1, n2) <= x) {
//	// n2.setMerged(true);
//	// // add n2 to the list of nodes to remove
//	// removal.add(n2);
//	// // merge n2 into n1
//	// mergeNode(n1, n2);
//	// }
//	// }
//	// }
//	// }
//	// // finally remove all the nodes that are no longer needed.
//	// nodeList.removeAll(removal);
//	// nodes = new HashSet<Node>(nodeList);
//	// }
//
//	protected void mergeNodes(List<Node> mergeCandidates, double distance) {
//
//		// create an edge between every node in this new array
//		for (int i = 0; i < mergeCandidates.size(); i++) {
//			Node n1 = mergeCandidates.get(i);
//			for (int j = i + 1; j < mergeCandidates.size(); j++) {
//
//				Node n2 = mergeCandidates.get(j);
//
//				// check this distance between these nodes, if it is less than
//				// or equal to distance then create the edge
//				// Create an edge
//
//				double haversineDistance = HaversineDistance.calculateDistance(
//						n1, n2);
//				if (haversineDistance <= distance) {
//					Edge e = new Edge();
//					e.setFrom(n1);
//					e.setTo(n2);
//					e.setWeight(haversineDistance);
//					n1.getOutboudEdges().add(e);
//					n2.getInboundEdges().add(e);
//				}
//			}
//		}
//
//		// we now have an array of nodes that are joined by edges in their
//		// clusters
//		// create individual nodes for these clusters.
//
//		// A hashset to keep track of nodes that we have already dealt with;
//		HashSet<Node> completedNodes = new HashSet<Node>();
//		Iterator<Node> nodeIterator = mergeCandidates.iterator();
//		while (nodeIterator.hasNext()) {
//
//			// Get the node from the iterator
//			Node node = nodeIterator.next();
//			// check to see if this has node has already been clustered, if not
//			// start creating the cluster
//			if (!completedNodes.contains(node)) {
//				// use a linked list for a queue to add our nodes to.
//				LinkedList<Node> nodeQ = new LinkedList<Node>();
//				HashSet<Node> nodesToMerge = new HashSet<Node>();
//				nodeQ.add(node);
//				while (!nodeQ.isEmpty()) {
//					// get an item off the queue, add it to a closed list
//					Node n = nodeQ.poll();
//					// add this node to the set of nodes we are going to merge
//					nodesToMerge.add(n);
//					// for each node that is connected either via an outbound or
//					// inbound edge add them to the nodeQ, check that they have
//					// not
//					// already been added.
//					for (Edge e : n.getOutboudEdges()) {
//						// carry out two checks, if the node is not in the
//						// closedList AND node in the queue, add it to the queue
//						Node node1 = e.getTo();
//						if (!nodesToMerge.contains(node1)
//								&& !nodeQ.contains(node1)) {
//							nodeQ.add(node1);
//						}
//					}
//					for (Edge e : n.getInboundEdges()) {
//						// carry out two checks, if the node is not in the
//						// closedList AND node in the queue, add it to the queue
//						Node node1 = e.getFrom();
//						if (!nodesToMerge.contains(node1)
//								&& !nodeQ.contains(node1)) {
//							nodeQ.add(node1);
//						}
//					}
//				}
//
//				// create the new node that replaces all the nodes in our
//				// nodesToMergeSet.
//				nodes.add(buildNewNodeFromSetOfNodes(nodesToMerge));
//				// add the set of merged nodes to the set of "completed" nodes
//				completedNodes.addAll(nodesToMerge);
//			}
//		}
//	}
//
//	/**
//	 * Builds a Node object that represents the average of all the nodes in the
//	 * set that is passed to this method. This method also removes the nodes
//	 * from their respective pistes and replaces them with the newly created
//	 * node. If an empty set is passed to this method this will return null. If
//	 * the set only contains 1 node then that node will be returned.
//	 * 
//	 * @param nodes
//	 *            - the Set<Node> of nodes that we want to build an average node
//	 *            from
//	 * @return the newly created node
//	 */
//	protected Node buildNewNodeFromSetOfNodes(Set<Node> nodes) {
//
//		// Some checks first
//		// if there are no nodes in the set return null.
//		if (nodes.isEmpty()) {
//			return null;
//		}
//		// if there is only one node in the set return that node
//		if (nodes.size() == 1) {
//			return (Node) nodes.toArray()[0];
//		}
//
//		// the new node we are building;
//		Node node = new Node();
//
//		double longitude = 0;
//		double latitude = 0;
//		double altitude = 0;
//		HashSet<Piste> pistes = new HashSet<Piste>();
//		boolean start = false;
//		boolean end = false;
//		boolean merged = false;
//		boolean predicted = false;
//
//		double longTotal = 0;
//		double latTotal = 0;
//		// TODO - altitude implementation
//		// double altitudeTotal;
//
//		int nodeCounter = 0;
//		for (Node n : nodes) {
//			nodeCounter++;
//			longTotal = longTotal + n.getLongitude();
//			latTotal = latTotal + n.getLattitude();
//			if (n.isStart())
//				start = true;
//			if (n.isEnd())
//				end = true;
//			if (n.isPredicted())
//				predicted = true;
//			pistes.addAll(n.getPistes());
//
//			// in every piste that this node appears replace it with the new
//			// one.
//			for (Piste p : pistes) {
//				// get the linked list of nodes
//				LinkedList<Node> nodeList = p.getNodes();
//				nodeList.set(nodeList.indexOf(n), node);
//			}
//		}
//
//		longitude = longTotal / nodeCounter;
//		latitude = latTotal / nodeCounter;
//
//		return new Node(longitude, latitude, altitude, new HashSet<Edge>(),
//				new HashSet<Edge>(), pistes, start, end, merged, predicted);
//	}
//
//	/**
//	 * Based on line geometry calculate where edges intersect and create a new
//	 * node to join the intersecting pistes. Updates and creates the node, and
//	 * the related nodes and edges.
//	 */
//	protected void predictNewNodes() {
//
//		ArrayList<Edge> edgeList = new ArrayList<Edge>(edges);
//
//		// compare all these edges against each other.
//		for (int i = 0; i < edgeList.size(); i++) {
//
//			for (int j = i + 1; j < edgeList.size(); j++) {
//				Edge e1 = edgeList.get(i);
//				Edge e2 = edgeList.get(j);
//
//				// make sure we arn't comparing the same edges
//				if (e1.equals(e2)) {
//					continue;
//				}
//				// make sure edges are not in the same pistes
//				if (e1.getPiste().equals(e2.getPiste())) {
//					continue;
//				}
//				Point p1 = new Point(e1.getFrom().getLongitude(), e1.getFrom()
//						.getLattitude());
//				Point p2 = new Point(e1.getTo().getLongitude(), e1.getTo()
//						.getLattitude());
//				Point p3 = new Point(e2.getFrom().getLongitude(), e2.getFrom()
//						.getLattitude());
//				Point p4 = new Point(e2.getTo().getLongitude(), e2.getTo()
//						.getLattitude());
//
//				LineSegment line1 = new LineSegment(p1, p2);
//				LineSegment line2 = new LineSegment(p3, p4);
//				Point intersection = line1.intersectionPoint(line2);
//
//				if (intersection != null) {
//
//					// our data isnt perfect so if the intersection
//					// falls
//					// within
//					// 15m of the points that make up the line
//					// segments
//					// create
//					// the node
//					if (pointWithinRange(intersection, line1, line2, 15)) {
//						createNode(intersection, e1.getTo(), e2.getTo(),
//								e1.getPiste(), e2.getPiste());
//
//					}
//				}
//			}
//
//		}
//
//	}
//
//	/**
//	 * Creates a new node at the intersection that has been passed to this
//	 * method.
//	 */
//	protected void createNode(Point intersection, Node n1, Node n2,
//			Piste piste1, Piste piste2) {
//
//		if (intersection != null) {
//
//			Node n = new Node();
//			n.setLattitude(intersection.getY());
//			n.setLongitude(intersection.getX());
//			n.getPistes().add(piste1);
//			n.getPistes().add(piste2);
//			// We have only predicted that this intersection exists.
//			n.setPredicted(true);
//			// add this new node to our existing list of nodes
//			nodes.add(n);
//
//			// Insert this node into the correct pistes
//
//			// is the node we are adding the same as the node previous to it in
//			// the list?
//			if (n1.equals(n)) {
//				System.out.println("Adding duplicate node");
//			}
//			if (n2.equals(n)) {
//				System.out.println("Adding duplicate node");
//			}
//
//			piste1.getNodes().add(piste1.getNodes().indexOf(n1), n);
//			piste2.getNodes().add(piste2.getNodes().indexOf(n2), n);
//		}
//	}
//
//	/**
//	 * Tests if the intersection point is within range of any of the other
//	 * points. The haversine formula will be used to calculate if the
//	 * intersection point is within range.
//	 * 
//	 * @param intersection
//	 *            - the point we are testing to find out if it is in range
//	 * @param range
//	 *            the range in meters.
//	 * @return
//	 */
//	protected boolean pointWithinRange(Point intersection, LineSegment line1,
//			LineSegment line2, double range) {
//
//		Point x = line1.closestPointOnLine(intersection);
//		Point y = line2.closestPointOnLine(intersection);
//
//		if (y != null && x != null) {
//			double distX = HaversineDistance.calculateLength(x.getX(),
//					x.getY(), intersection.getX(), intersection.getY());
//
//			double disty = HaversineDistance.calculateLength(y.getX(),
//					y.getY(), intersection.getX(), intersection.getY());
//
//			if (disty <= range && distX <= range) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	// /**
//	// * Merge the two nodes, take all the data from node 2 and add it to node1,
//	// * the calling method will remove node2 from the list of nodes. We don't
//	// * care about the edges as we will rebuild all of these later.
//	// *
//	// * @param n1
//	// * the node we are merging to.
//	// * @param n2
//	// * the node we are merging from.
//	// */
//	// protected void mergeNode(Node n1, Node n2) {
//	//
//	// // System.out.println("Merging Nodes " + n1 + " and " + n2);
//	// // Put n1 into the piste that n2 belongs to
//	// // check that n1 is not already in that piste
//	//
//	// n1.getPistes().addAll(n2.getPistes());
//	// // update the piste, everywhere n2 appears in a piste replace it with
//	// // n1.
//	// for (Piste p : n2.getPistes()) {
//	// // Node n2 may already have been added to this piste as a result of
//	// // a previous comparrison, dont add it again.
//	// if (!p.getNodes().contains(n1)) {
//	// p.getNodes().set(p.getNodes().indexOf(n2), n1);
//	// }
//	//
//	// }
//	// }
//
//	private void checkForOriginAndTerminusTheSame(Edge e) {
//		if (e.getTo().equals(e.getFrom())) {
//			System.out.println("Origin and Terminus are the same");
//		}
//	}
//
//	/**
//	 * @return the graph
//	 */
//	public Graph getGraph() {
//		return graph;
//	}
//
//	/**
//	 * @param graph
//	 *            the graph to set
//	 */
//	public void setGraph(Graph graph) {
//		this.graph = graph;
//	}
//
//	/**
//	 * @return the nodes
//	 */
//	public HashSet<Node> getNodes() {
//		return nodes;
//	}
//
//	/**
//	 * @param nodes
//	 *            the nodes to set
//	 */
//	public void setNodes(HashSet<Node> nodes) {
//		this.nodes = nodes;
//	}
//
//	/**
//	 * @return the edges
//	 */
//	public HashSet<Edge> getEdges() {
//		return edges;
//	}
//
//	/**
//	 * @param edges
//	 *            the edges to set
//	 */
//	public void setEdges(HashSet<Edge> edges) {
//		this.edges = edges;
//	}
//
//	/**
//	 * @return the pistes
//	 */
//	public HashSet<Piste> getPistes() {
//		return pistes;
//	}
//
//	/**
//	 * @param pistes
//	 *            the pistes to set
//	 */
//	public void setPistes(HashSet<Piste> pistes) {
//		this.pistes = pistes;
//	}
//}
