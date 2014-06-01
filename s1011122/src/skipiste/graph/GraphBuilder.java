package skipiste.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import skipiste.geometry.LineSegment;
import skipiste.geometry.Point;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;
import skipiste.importer.kml.KMLHandler;
import skipiste.importer.kml.KMLImporter;
import skipiste.importer.kml.SkiMapHandler;
import skipiste.utils.distance.DistanceCalculator;
import skipiste.utils.distance.HaversineDistance;

/**
 * Builds the network graph from input from a Set<Piste> objects.
 * @author s1011122
 * 
 */
public class GraphBuilder implements GraphBuilderInterface {

	/**
	 * The pistes in this graph
	 */
	private HashSet<Piste> pistes;

	/**
	 * Calculate the distance between two points
	 */
	private DistanceCalculator calc;

	/**
	 * Start or End nodes less to or equal to this distance apart from each
	 * other will be clustered together into a single node.
	 */
	private static final int clusterDistance = 20;

	/**
	 * The tolerance we allow for line intersections, if the intersection of
	 * line a and line b is within this distance of both lines then the
	 * intersection node will be created.
	 */
	private static final int lineInterSectionTolerance = 15;

	/**
	 * The no argument no operation constructor.
	 */
	public GraphBuilder() {
		// Do Nothing
	}

	@Override
	public Graph buildGraph(String KMLFile) {

		// initialise 
		calc = new HaversineDistance();

		
		// Import the KML.
		System.out.println("Importing KML");
		KMLHandler handler = new SkiMapHandler();
		KMLImporter importer = new KMLImporter(handler, KMLFile);
		// Get the pistes, at this stage they are just nodes in the correct order.
		pistes = new HashSet<Piste>(importer.getPistes());

		// Step 1: build the Edges in the graph, we will do this again later
		System.out.println("Building edges for first time");
		createAllEdges();
		// Step 2: predict where new nodes will be and build them by calculating
		// where edges intersect
		System.out
				.println("Finding intersections and building new nodes at those intersections");
		findIntersections();
		// Step3: remove the edges
		System.out
				.println("Removing the edges; the edges built before are now out of date");
		removeAllEdges();

		// Step 4: cluster the start and end nodes so pistes will join up
		System.out
				.println("Clustering together start, end and intersection nodes within "
						+ clusterDistance + " meters of each other");
		clusterNodes();
		// remove the edges and rebuild them
		removeAllEdges();
		System.out.println("Rebuilding all edges");
		createAllEdges();

		addEdgeWeights();

		// because the hashcode and equals values mean that some edges are
		// considered equal despite not being the same object in memory we have
		// edges with null weights on the graph, remove these.
		// TODO - FIX HASHCODE AND EQUALS OF EDGE!!!
		for (Piste p : pistes) {
			for (Node n : p.getNodes()) {
				HashSet<Edge> tmp = new HashSet<Edge>();
				for (Edge e : n.getOutbound()) {
					if (e.getWeight() == null) {
						tmp.add(e);
					}
				}

				n.getOutbound().removeAll(tmp);
				tmp = new HashSet<Edge>();
				for (Edge e : n.getInbound()) {
					if (e.getWeight() == null) {
						tmp.add(e);
					}
				}
				n.getInbound().removeAll(tmp);
			}
		}

		// create the extra data structures
		HashSet<Node> nodes = new HashSet<Node>();
		HashSet<Edge> edges = new HashSet<Edge>();

		for (Piste p : pistes) {

			nodes.addAll(p.getNodes());
			for (Node n : p.getNodes()) {
				edges.addAll(n.getOutbound());
				edges.addAll(n.getInbound());
			}

		}

		return new Graph(pistes, nodes, edges);
	}

	/**
	 * Add the edge weights using the distance calculator.
	 */
	protected void addEdgeWeights() {

		HashSet<Edge> edges = new HashSet<Edge>();
		for (Piste p : pistes) {
			for (Node n : p.getNodes()) {
				edges.addAll(n.getOutbound());
				edges.addAll(n.getInbound());
			}
		}
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
		for (Piste p : pistes) {
			for (Node n : p.getNodes()) {
				n.setInbound(new HashSet<Edge>());
				n.setOutbound(new HashSet<Edge>());
				;
			}
		}
	}

	/**
	 * Creates all the edges for the graph data structures
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
					origin.getOutbound().add(e);
					terminus.getInbound().add(e);
				} else {
					it.next();
				}
			}
		}
	}

	/**
	 * Cluster the start and end nodes in the graph
	 */
	protected void clusterNodes() {
		// get all the nodes marked start and end
		HashSet<Node> startEndNodes = new HashSet<Node>();

		for (Piste p : pistes) {
			for (Node n : p.getNodes()) {
				if (n.isStart() || n.isEnd() || n.isIntersection()) {
					startEndNodes.add(n);
				}
			}
		}

		// build edges for these start end nodes if the distance between them
		// does not exceed the cluster tolerance m

		for (Node n1 : startEndNodes) {
			for (Node n2 : startEndNodes) {
				if (!n1.equals(n2)
						&& calc.calculateDistanceBetweenNodes(n1, n2) <= clusterDistance) {
					Edge e = new Edge(n1, n2);
					n1.getOutbound().add(e);
					n2.getInbound().add(e);
				}
			}
		}

		ArrayList<HashSet<Node>> clusters = findClusters(startEndNodes);

		for (HashSet<Node> cluster : clusters) {
			if (cluster.size() > 1) {
				buildNodeFromCluster(cluster);

				for (Piste p : pistes) {
					p.getNodes().removeAll(cluster);
				}

			} else {
				// just remove the edges
				for (Node n : cluster) {
					n.getOutbound().clear();
					n.getInbound().clear();
				}
			}

		}
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
			origin.getOutbound().add(e);
			terminus.getInbound().add(e);
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
	protected void findIntersections() {

		// get a list of all the edges in the pistes
		HashSet<Edge> edges = new HashSet<Edge>();
		for (Piste p : pistes) {
			for (Node n : p.getNodes()) {
				edges.addAll(n.getOutbound());
				edges.addAll(n.getInbound());
			}
		}

		Iterator<Edge> edgeIt = edges.iterator();

		while (edgeIt.hasNext()) {
			Edge e1 = edgeIt.next();
			// remove the edge so it wont be compared again.
			edgeIt.remove();
			// get e1's piste
			Piste p1 = e1.getPiste();
			LineSegment l1 = new LineSegment(new Point(e1.getFrom()
					.getLongitude(), e1.getFrom().getLatitude()), new Point(e1
					.getTo().getLongitude(), e1.getTo().getLatitude()));

			// compare e against all other edges

			for (Edge e2 : edges) {
				Piste p2 = e2.getPiste();
				if (!p2.equals(p1)) {

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
						// insert this node into the correct places in the
						// pistes
						// insert this node into the piste p1, immediately
						// before
						// the end point of e1
						int x = p1.getNodes().indexOf(e1.getTo());
						p1.getNodes().add(x, n);
						// insert this node into the piste p2, immediately
						// before
						// the end point of e2
						int y = p2.getNodes().indexOf(e2.getTo());
						p2.getNodes().add(y, n);
					}

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
	protected ArrayList<HashSet<Node>> findClusters(HashSet<Node> nodes) {

		// Use a LinkedList as a queue
		LinkedList<Node> queue = new LinkedList<Node>();
		// keep track of all the nodes we have already examined
		HashSet<Node> visited = new HashSet<Node>();

		ArrayList<HashSet<Node>> clusters = new ArrayList<HashSet<Node>>();

		for (Node n : nodes) {

			if (visited.contains(n)) {
				continue;
			} else {
				HashSet<Node> cluster = new HashSet<Node>();

				queue.add(n);
				// while we still have nodes on the queue
				while (!queue.isEmpty()) {
					// take the first node off the queue
					Node next = queue.poll();
					// if we have already looked at this node don't consider it
					// again
					if (!visited.contains(next)) {
						// add this to the list nodes we are going to cluster
						cluster.add(next);
						visited.add(next);
						// add it to the list of visited nodes so we know not to
						// consider it again
						// look at the edges (just look at outbound to avoid
						// duplication)
						for (Edge e : next.getOutbound()) {
							queue.add(e.getTo());
						}
					}
				}
				clusters.add(cluster);
			}
		}

		return clusters;
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

		// the new node we are building;
		Node node = new Node();

		double longitude = 0;
		double latitude = 0;
		double altitude = 0;
		HashSet<Piste> pistes = new HashSet<Piste>();
		boolean start = false;
		boolean end = false;
		boolean intersection = false;

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
			if (n.isIntersection()) {
				intersection = true;
			}

			// We need to place the new node in the piste where the old node
			// was.
			for (Piste p : n.getPistes()) {
				// get the linked list of nodes
				LinkedList<Node> nodeList = p.getNodes();

				// the new node may have already been put in the piste as it
				// other start/end/intersections may have already been part of
				// the same piste.
				if (!nodeList.contains(node)) {
					// replace the the node n with the new node "node"
					int index = nodeList.indexOf(n);
					nodeList.set(index, node);
					// add this piste to the new node
					pistes.add(p);
				}
				// just remove the node from the piste
				else {
					p.getNodes().remove(n);
				}

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

		return node;

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
}
