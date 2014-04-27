package skipiste.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;

import skipiste.geometry.LineSegment;
import skipiste.geometry.Point;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.GraphBuilderInterface;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;
import skipiste.importer.kml.KMLHandler;
import skipiste.importer.kml.KMLImporter;
import skipiste.importer.kml.SkiMapHandler;
import skipiste.utils.distance.DistanceCalculator;
import skipiste.utils.distance.HaversineDistance;

/**
 * 
 * @author s1011122 builds the graphs for use by the search alogrithms.
 * 
 */
public class NewGraphBuilder implements GraphBuilderInterface {

	/**
	 * The edges of the graph.
	 */
	protected HashSet<Edge> edges;
	/**
	 * The pistes in this graph
	 */
	protected HashSet<Piste> pistes;
	/**
	 * The nodes in this graph.
	 */
	protected HashSet<Node> nodes;
	
	/**
	 * Calculate the distance between two points
	 */
	protected DistanceCalculator calc;

	/**
	 * Start or End nodes less to or equal to this distance apart from each
	 * other will be clustered together into a single node.
	 */
	private int startEndNode;
	/**
	 * PredictedNodes nodes less to or equal to this distance apart from each
	 * other will be clustered together into a single node.
	 */
	private int predictedNodeDistance;

	/**
	 * The tolerance we allow for line intersections, if the intersection of
	 * line a and line b is within this distance of both lines then the
	 * intersection node will be created.
	 */
	protected double lineInterSectionTolerance;

	/**
	 * The no argument no operation constructor.
	 */
	public NewGraphBuilder() {
		// Do Nothing
	}

	@Override
	public Graph buildGraph(String KMLFile) {

		System.out.println("Importing KML");
		KMLHandler handler = new SkiMapHandler();
		KMLImporter importer = new KMLImporter(handler, KMLFile);

		nodes = new HashSet<Node>(importer.getNodes());
		pistes = new HashSet<Piste>(importer.getPistes());
		edges = new HashSet<Edge>();
		calc = new HaversineDistance();
		startEndNode = 20;
		predictedNodeDistance = 10;
		lineInterSectionTolerance = 15;
		
		

		// Step 1: build the Edges in the graph, we will do this again later
		System.out.println("Building edges for first time");
		createAllEdges();
		// Step 2: predict where new nodes will be and build them by calculating
		// where edges intersect
		validateGraph();
		System.out
				.println("Finding intersections and building new nodes at those intersections");
		findIntersections(edges);
		// Step3: remove the edges
		System.out
				.println("Removing the edges; the edges built before are now out of date");
		removeAllEdges();
		// Step 4: cluster the start and end nodes so pistes will join up
		System.out.println("Clustering together start and end nodes within "
				+ startEndNode + " meters of each other");
		clusterStartAndEndNodes();
		// Step 5: cluster the nodes that we predicted exist
		System.out
				.println("Clustering together nodes predicted by calculating our piste intersection within  "
						+ predictedNodeDistance + " meters of each other");
		clusterPredictedNodes();
		// remove the edges and rebuild them
		removeAllEdges();
		System.out.println("Rebuilding all edges");
		createAllEdges();
		// validate the graph
		validateGraph();
		// finally add the edge weights using the distance calculator
		addEdgeWeights();
		return new Graph(pistes, nodes, edges);
	}

	/**
	 * Add the edge weights using the distance calculator.
	 */
	protected void addEdgeWeights() {
		for (Edge e : edges) {
			e.setWeight(calc.calculateDistanceBetweenNodes(e.getFrom(),
					e.getTo()));
		}
	}

	/**
	 * Removes all edges that have been built so far in the graph data
	 * structures.
	 */
	protected void removeAllEdges() {
		edges = new HashSet<Edge>();
		for (Node n : nodes) {
			n.setOutboudEdges(new HashSet<Edge>());
			n.setInboundEdges(new HashSet<Edge>());
		}
	}

	/**
	 * Creates all the edges for the graph data strucutres
	 */
	protected void createAllEdges() {
		for (Piste p : this.pistes) {

			ListIterator<Node> it = p.getNodes().listIterator();

			while (it.hasNext()) { 
				if (it.hasPrevious()) {
					Node origin = it.previous();
					// move the iterator on to its position before calling
					// previous
					it.next();
					Node terminus = it.next();
					Edge e = new Edge(origin, terminus);
					e.setPiste(p);
					edges.add(e);
					origin.getOutboundEdges().add(e);
					terminus.getInboundEdges().add(e);
				}
				else
				{
					it.next();					
				}
			}
		}
	}

	/**
	 * Cluster the start and end nodes in the graph
	 */
	protected void clusterStartAndEndNodes() {
		// get all the nodes marked start and end
		HashSet<Node> startEndNodes = new HashSet<Node>();

		// Because we keep linked lists of nodes which represent the pistes in
		// the order of the piste then we can just take the first and last node
		// from every linked list of nodes.
		// for (Piste p: pistes)
		// {
		// startEndNodes.add(p.getNodes().getFirst());
		// startEndNodes.add(p.getNodes().getLast());
		// }

		for (Node n : nodes) {
			if (n.isEnd() || n.isStart()) {
				startEndNodes.add(n);
			}
		}

		// build edges for these start end nodes if the distance between them
		// does not exceed 30 m

		for (Node n1 : startEndNodes) {
			for (Node n2 : startEndNodes) {
				if (!n1.equals(n2)
						&& calc.calculateDistanceBetweenNodes(n1, n2) <= startEndNode) {
					n1.getOutboundEdges().add(new Edge(n1, n2));
				}
			}
		}

		findClusters(startEndNodes);

	}

	/**
	 * Cluster the nodes that we have predicted in the graph
	 */
	protected void clusterPredictedNodes() {
		// Step 4: cluster the start and end nodes so pistes will join up
		// get all the nodes marked start and end
		HashSet<Node> predictedNodes = new HashSet<Node>();

		for (Node n : nodes) {
			if (n.isEnd() || n.isStart())
				predictedNodes.add(n);
		}

		// build edges for these start end nodes if the distance between them
		// does not exceed 30 m

		for (Node n1 : predictedNodes) {
			for (Node n2 : predictedNodes) {
				if (!n1.equals(n2)
						&& calc.calculateDistanceBetweenNodes(n1, n2) <= predictedNodeDistance) {
					n1.getOutboundEdges().add(new Edge(n1, n2));
				}
			}
		}

		findClusters(predictedNodes);

	}

	/**
	 * Creates edges between the nodes of a pistes
	 * 
	 * @return HashSet<Edge> - a HashSet of the edges we have built between the
	 *         nodes
	 */
	protected HashSet<Edge> createEdges(LinkedList<Node> nodes) {
		HashSet<Edge> edges = new HashSet<Edge>();

		for (int i = 1; i < nodes.size(); i++) {
			Node origin = (Node) nodes.get(i - 1);
			Node terminus = (Node) nodes.get(i);
			Edge e = new Edge(origin, terminus);
			edges.add(e);
			origin.getOutboundEdges().add(e);
			terminus.getInboundEdges().add(e);
		}

		return edges;
	}

	/**
	 * Create a node at the given intersection, that is part of the pistes
	 * passed to this method.
	 * 
	 * @param intersection
	 *            - the intersection of two points which will be used for the
	 *            longitude and latitude of this node.
	 * @param piste1
	 *            - one of the pistes that this node will belong to.
	 * @param piste2
	 *            - one of the pistes that this node will belong to.
	 * @return - the new node.
	 */
	protected Node buildNodeAtIntersection(Point intersection, Piste piste1,
			Piste piste2) {
		Node n = new Node(intersection.getX(), intersection.getY(), false,
				false);
		n.setIntersection(true);
		n.getPistes().add(piste1);
		n.getPistes().add(piste2);
		// this node is the result of predicting where nodes will occur at
		// intersections of edges.
		n.setPredicted(true);
		return n;
	}

	/**
	 * Tests if the intersection point is within range of any of the other
	 * points. The haversine formula will be used to calculate if the
	 * intersection point is within range.
	 * 
	 * @param intersection
	 *            - the point we are testing to find out if it is in range
	 * @param tolerance
	 *            the range in meters.
	 * @return
	 */
	protected boolean pointWithinTolerance(Point intersection,
			LineSegment line1, LineSegment line2, double tolerance) {

		Point x = line1.closestPointOnLine(intersection);
		Point y = line2.closestPointOnLine(intersection);

		if (y != null && x != null) {
			double distX = calc.calculateDistanceBetweenCoordinates(x.getX(),
					x.getY(), intersection.getX(), intersection.getY());

			double disty = calc.calculateDistanceBetweenCoordinates(y.getX(),
					y.getY(), intersection.getX(), intersection.getY());

			if (disty <= tolerance && distX <= tolerance) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Compares every edge in a HashSet<Edge> with every other edge in the
	 * HashSet<Edge>
	 * 
	 * @param edges
	 */
	protected void findIntersections(HashSet<Edge> edges) {

		// for ease of use we will create an ArrayList<Edge>
		ArrayList<Edge> edgeList = new ArrayList<Edge>(edges);

		// loop over every edge in the list of pistes
		for (int i = 0; i < edgeList.size(); i++) {
			Edge e1 = edgeList.get(i);
			// Find the piste it game from
			Piste p1 = e1.getPiste();
			// Turn e1 into a line segment
			LineSegment l1 = new LineSegment(new Point(e1.getFrom()
					.getLongitude(), e1.getFrom().getLatitude()), new Point(e1
					.getTo().getLongitude(), e1.getTo().getLatitude()));
			// compare it against every other edge
			for (int j = i + 1; j < edgeList.size(); j++) {
				Edge e2 = edgeList.get(j);
				Piste p2 = e2.getPiste();

				// if they are in the same piste don't compare these edges
				if (p1.equals(p2)) {
					continue;
				}
				LineSegment l2 = new LineSegment(new Point(e2.getFrom()
						.getLongitude(), e2.getFrom().getLatitude()),
						new Point(e2.getTo().getLongitude(), e2.getTo()
								.getLatitude()));

				// see if there is an intersection between l1 and l2
				Point intersection = l1.intersectionPoint(l2);
				if (intersection != null
						&& pointWithinTolerance(intersection, l1, l2,
								lineInterSectionTolerance)) {
					// create the new node
					Node n = buildNodeAtIntersection(intersection, p1, p2);
					// insert this node into the correct places in the pistes
					// insert this node into the piste p1, immediately before
					// the end point of e1
					int x = p1.getNodes().indexOf(e1.getTo());
					p1.getNodes().add(x, n);
					// insert this node into the piste p2, immediately before
					// the end point of e2
					int y = p2.getNodes().indexOf(e2.getTo());
					p2.getNodes().add(y, n);
				}
			}
		}
	}

	/**
	 * Finds a cluster in a hashSet of nodes. In this case a cluster of nodes is
	 * nodes that are joined by outbound edges
	 * 
	 * @param nodes
	 *            the HashSet<Node> of nodes that contain clusters
	 */
	protected void findClusters(HashSet<Node> nodes) {

		// Use a LinkedList as a queue
		LinkedList<Node> nodeQ = new LinkedList<Node>();
		// keep track of all the nodes we have already examined
		HashSet<Node> visited = new HashSet<Node>();

		// deal with the nodes as a list for convenience
		ArrayList<Node> nodeList = new ArrayList<Node>(nodes);
		for (int i = 0; i < nodeList.size(); i++) {

			Node node = nodeList.get(i);
			// if we have already looked at this node then move onto the next
			// loop
			if (visited.contains(node))
				continue;
			HashSet<Node> cluster = new HashSet<Node>();

			// take the first node from the set of nodes and add it to the queue
			nodeQ.add(node);
			// while we still have nodes on the queue
			while (!nodeQ.isEmpty()) {
				// take the first node off the queue
				Node n = nodeQ.poll();
				// if we have already looked at this node don't consider it
				// again
				if (!visited.contains(n)) {
					// add this to the list nodes we are going to cluster
					cluster.add(n);
					visited.add(n);
					// add it to the list of visited nodes so we know not to
					// consider it again
					// look at the edges (just look at outbound to avoid
					// duplication)
					for (Edge e : n.getOutboundEdges()) {
						nodeQ.add(e.getTo());
					}
				}
			}

			if (cluster.size() > 1) {
				Node newNode = buildNodeFromCluster(cluster);
				// add this new node to the set of existing nodes
				this.nodes.add(newNode);
				// remove the now deprecated nodes
				this.getNodes().removeAll(cluster);
			}
		}
	}

	/**
	 * Build a node that represents the mid point of a cluster of nodes
	 * 
	 * @param cluster
	 *            HashSet<Node> nodes, the cluster of nodes that want to replace
	 *            with a single node
	 * @return Node - the replacement node
	 */
	protected Node buildNodeFromCluster(HashSet<Node> cluster) {
		// Some checks first
		// if there are no nodes in the set return null.
		if (cluster.isEmpty()) {
			return null;
		}
		// if there is only one node in the set return that node
		if (cluster.size() == 1) {
			return (Node) cluster.toArray()[0];
		}

		// the new node we are building;
		Node node = new Node();

		double longitude = 0;
		double latitude = 0;
		double altitude = 0;
		HashSet<Piste> pistes = new HashSet<Piste>();
		boolean start = false;
		boolean end = false;
		boolean intersection = false;
		boolean predicted = false;

		double longTotal = 0;
		double latTotal = 0;
		// TODO - altitude implementation
		// double altitudeTotal;

		int nodeCounter = 0;
		for (Node n : cluster) {
			nodeCounter++;
			longTotal = longTotal + n.getLongitude();
			latTotal = latTotal + n.getLatitude();
			if (n.isStart())
				start = true;
			if (n.isEnd())
				end = true;
			if (n.isPredicted())
				predicted = true;

			for (Piste p : n.getPistes()) {
				// get the linked list of nodes
				LinkedList<Node> nodeList = p.getNodes();
				// replace the the node n with the new node "node"
				int index = nodeList.indexOf(n);
				nodeList.set(index, node);
				// add this piste to the new node
				pistes.add(p);
			}
		}

		longitude = longTotal / nodeCounter;
		latitude = latTotal / nodeCounter;

		node.setLatitude(latitude);
		node.setLongitude(longitude);
		node.setAltitude(altitude);
		node.setPistes(pistes);
		node.setStart(start);
		node.setEnd(end);
		node.setIntersection(intersection);
		node.setPredicted(predicted);

		return node;

	}

	/**
	 * Validates that the graph created is correct accordin to some basic rules
	 * 1) All Start nodes must have at least one outbound node 2) All End nodes
	 * must have at least one inbound node 3) All Interections must have (a)
	 * More than 1 outbout node and 1 inbound node OR (b) more than 1 inbound
	 * node and 1 outbound node OR (c) more than 1 outbound node and more than 1
	 * inbound node. 4) All other nodes must have 1 outbound node and 1 inbound
	 * node. If any of these conditions are not met then the graph has not built
	 * correctly. 5) All edges must have one origin and one destination, and
	 * these must be different
	 */
	protected void validateGraph() {
		// Check the nodes
		for (Node n : nodes) {
			int outboundEdges = n.getOutboundEdges().size();
			int inboundEdges = n.getInboundEdges().size();

			if (n.isStart()) {
				if (outboundEdges < 1) {
					System.out
							.println("Found Start Node with no outbound edges");
				}
			}

			if (n.isEnd()) {
				if (inboundEdges < 1) {
					System.out.println("Found End Node with no inbound edges");
				}
			}

			if (n.isIntersection()) {
				if (inboundEdges <= 1 || outboundEdges <= 1) {
					System.out
							.println("Found Intersection that does not join two pistes");
				}
			}

			if (!n.isIntersection() && !n.isStart() && !n.isEnd()) {
				if (outboundEdges < 1) {
					System.out
							.println("Found Node with no outbound edges that is not an End node");
				}
				if (inboundEdges < 1) {
					System.out
							.println("Found Node with no inbound edges that is not a Start node");
				}
			}
		}

		// check the edges
		for (Edge e : edges) {
			if (e.getFrom() == null) {
				System.out.println("Found edge with no origin node");
			}
			if (e.getTo() == null) {
				System.out.println("Found edge with no destination node");
			}
			if (e.getFrom().equals(e.getTo())) {
				System.out
						.println("Found edge with same origin node and desitination node");
			}

		}
	}

	/**
	 * @return the edges
	 */
	public HashSet<Edge> getEdges() {
		return edges;
	}

	/**
	 * @param edges
	 *            the edges to set
	 */
	public void setEdges(HashSet<Edge> edges) {
		this.edges = edges;
	}

	/**
	 * @return the calc
	 */
	public DistanceCalculator getCalc() {
		return calc;
	}

	/**
	 * @param calc
	 *            the calc to set
	 */
	public void setCalc(DistanceCalculator calc) {
		this.calc = calc;
	}

	/**
	 * @return the lineInterSectionTolerance
	 */
	public double getLineInterSectionTolerance() {
		return lineInterSectionTolerance;
	}

	/**
	 * @param lineInterSectionTolerance
	 *            the lineInterSectionTolerance to set
	 */
	public void setLineInterSectionTolerance(double lineInterSectionTolerance) {
		this.lineInterSectionTolerance = lineInterSectionTolerance;
	}

	/**
	 * @return the pistes
	 */
	public HashSet<Piste> getPistes() {
		return pistes;
	}

	/**
	 * @param pistes
	 *            the pistes to set
	 */
	public void setPistes(HashSet<Piste> pistes) {
		this.pistes = pistes;
	}

	/**
	 * @return the nodes
	 */
	public HashSet<Node> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes
	 *            the nodes to set
	 */
	public void setNodes(HashSet<Node> nodes) {
		this.nodes = nodes;
	}

}
