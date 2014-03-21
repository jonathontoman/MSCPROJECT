package skipiste.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import skipiste.geometry.LineSegment;
import skipiste.geometry.Point;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;
import skipiste.importer.kml.KMLHandler;
import skipiste.importer.kml.KMLImporter;
import skipiste.importer.kml.PlanDePisteHandler;
import skipiste.utils.HaversineDistance;

/**
 * Builds the graph from the basic data set.
 * 
 * @author s101122
 * 
 */
public class GraphBuilder {

	/**
	 * The graph we are building
	 */
	private Graph graph;

	/**
	 * The nodes in the graph;
	 */
	private List<Node> nodes;

	/**
	 * The edges in the graph;
	 */
	private List<Edge> edges;

	/**
	 * The pistes in the graph;
	 */
	private List<Piste> pistes;

	/**
	 * Constructor
	 */
	public GraphBuilder(String kmlFile) {

		KMLHandler handler = new PlanDePisteHandler();
		KMLImporter importer = new KMLImporter(handler, kmlFile);

		nodes = importer.getNodes();
		pistes = importer.getPistes();
		edges = new ArrayList<Edge>();
		// build the edges
		createEdges(pistes);
		// predict new nodes that will join up our existing pistes.
		predictNewNodes();
		// merge nodes
		mergeSimilarNodes(10, 20);
		// add the weights of the edges
		for (Edge e : edges) {
			e.setWeight(HaversineDistance.calculateDistance(e.getFrom(),
					e.getTo()));
		}

		// create the graph object
		graph = new Graph(pistes, nodes, edges);
	}

	/**
	 * Constructor for unit testing only, if time allows we will use easymock to
	 * mock out calls to the kml handler in the constructor.
	 * 
	 * @param graph
	 * @param nodes
	 * @param edges
	 * @param pistes
	 */
	public GraphBuilder() {
		super();
		this.nodes = new ArrayList<Node>();
		this.edges = new ArrayList<Edge>();
		this.pistes = new ArrayList<Piste>();
	}

	/**
	 * Create the edges between the nodes in each piste.
	 * 
	 * @param pistes
	 */
	protected void createEdges(List<Piste> pistes) {

		for (Piste p : pistes) {
			ListIterator<Node> it = p.getNodes().listIterator();
			while (it.hasNext()) {

				Node origin = it.next();
				if (it.hasNext()) {
					Node terminus = it.next();
					// Make an edge
					Edge e = new Edge(origin, terminus, p);
					edges.add(e);

					// Set these edges on the node
					origin.getOutboudEdges().add(e);
					terminus.getInboundEdges().add(e);

					it.previous();
				} else {
					break;
				}
			}
			System.out.println("Added Edges for Piste : " + p);
		}
	}

	/**
	 * Merges nodes with similar geo coordinates, that are within X meters of
	 * each other. Has two roles, merges nodes that are marked as intersections
	 * that are within X meters of each other. Merges nodes that are marked as
	 * either start or end that are within Y metes of each other.
	 * 
	 * @param X
	 *            - merge nodes that we predicted exist if they are with X
	 *            meters of each other
	 * @param Y
	 *            - merge nodes that are marked as either start or end with
	 *            other start or end nodes if they are within Y meters of each
	 *            other.
	 */
	protected void mergeSimilarNodes(double x, double y) {

		// for each node, find all other nodes with X meters of it
		for (int i = 0; i < nodes.size(); i++) {
			List<Node> toMerge = new ArrayList<Node>();
			Node n1 = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); j++) {
				Node n2 = nodes.get(j);
				// merge start/end nodes for predicted nodes.
				if (n1.isStart() || n1.isEnd()) {
					if (!n2.isMerged() && (n2.isStart() || n2.isEnd())) {
						if (HaversineDistance.calculateDistance(n1, n2) <= y)
						{
							toMerge.add(n2);
							n2.setMerged(true);
						}
					}
				}
				if (n1.isPredicted()) {
					if (HaversineDistance.calculateDistance(n1, n2) <= x) {
						toMerge.add(n2);
						n2.setMerged(true);
					}
				}
			}
			for (Node n : toMerge) {
				mergeNode(n1, n);
				nodes.remove(n);
			}
		}	
	}

	/**
	 * Based on line geometry calculate where edges intersect and create a new
	 * node to join the intersecting pistes. Updates and creates the node, and
	 * the related nodes and edges.
	 */
	protected void predictNewNodes() {

		// we want to iterate over a copy of our existing edges
		List<Edge> edgesCopy = new ArrayList<Edge>(edges);

		// compare all these edges against each other.
		for (int i = 0; i < edgesCopy.size(); i++) {

			for (int j = i + 1; j < edgesCopy.size(); j++) {
				Edge e1 = edgesCopy.get(i);
				if (i != j) {
					Edge e2 = edgesCopy.get(j);
					// Check if they belong to the same piste, if not see if we
					// should merge them based on being within 10m of each
					// other.
					if (!e1.getPiste().getName()
							.equals(e2.getPiste().getName())) {
						Point p1 = new Point(e1.getFrom().getLongitude(), e1
								.getFrom().getLattitude());
						Point p2 = new Point(e1.getTo().getLongitude(), e1
								.getTo().getLattitude());
						Point p3 = new Point(e2.getFrom().getLongitude(), e2
								.getFrom().getLattitude());
						Point p4 = new Point(e2.getTo().getLongitude(), e2
								.getTo().getLattitude());

						LineSegment line1 = new LineSegment(p1, p2);
						LineSegment line2 = new LineSegment(p3, p4);
						Point intersection = line1.intersectionPoint(line2);

						if (intersection != null) {
							// if the intersection falls on either edge create a
							// new
							// node
							if (line1.contains(intersection)
									&& line2.contains(intersection)) {
								createNode(intersection, e1, e2);
							}
							// our data isnt perfect so if the intersection
							// falls
							// within
							// 15m of the points that make up the line segments
							// create
							// the node
							else if (pointWithinRange(intersection, line1,
									line2, 15)) {
								createNode(intersection, e1, e2);
							}

						}
					}
				}
			}
		}
	}

	/**
	 * Creates a new node at the intersection that has been passed to this
	 * method.
	 */
	protected void createNode(Point intersection, Edge e1, Edge e2) {
		// if the intersection is not null we will create a new node to
		// represent the intersection
		// and adjust the edges to join this node.
		if (intersection != null) {
			Node n = new Node();

			n.setLattitude(intersection.getY());
			n.setLongitude(intersection.getX());
			n.getPistes().add(e1.getPiste());
			n.getPistes().add(e2.getPiste());

			// We have only predicted that this intersection exists.
			n.setPredicted(true);
			// add this new node to our existing list of nodes
			nodes.add(n);

			// e1 needs to be made into two new edges
			Edge edge1 = new Edge(e1.getFrom(), n, e1.getPiste());
			Edge edge2 = new Edge(n, e1.getTo(), e1.getPiste());
			// update the new node with these edges
			n.getInboundEdges().add(edge1);
			n.getOutboudEdges().add(edge2);

			// e2 needs to be made into two new edges
			Edge edge3 = new Edge(e2.getFrom(), n, e2.getPiste());
			Edge edge4 = new Edge(n, e2.getTo(), e2.getPiste());
			// update the new node with these edges
			n.getInboundEdges().add(edge3);
			n.getOutboudEdges().add(edge4);

			// our new node is now in two pistes, update these accordingly
			e1.getPiste().getNodes()
					.add(e1.getPiste().getNodes().indexOf(e1.getFrom()), n);
			e2.getPiste().getNodes()
					.add(e2.getPiste().getNodes().indexOf(e2.getFrom()), n);

			// remove the old edges
			edges.remove(e1);
			edges.remove(e2);

		}
	}

	/**
	 * Tests if the intersection point is within range of any of the other
	 * points. The haversine formula will be used to calculate if the
	 * intersection point is within range.
	 * 
	 * @param intersection
	 *            - the point we are testing to find out if it is in range
	 * @param range
	 *            the range in meters.
	 * @return
	 */
	protected boolean pointWithinRange(Point intersection, LineSegment line1,
			LineSegment line2, double range) {

		Point x = line1.closestPointOnLine(intersection);
		Point y = line2.closestPointOnLine(intersection);

		if (y != null && x != null) {
			double distX = HaversineDistance.calculateLength(x.getX(),
					x.getY(), intersection.getX(), intersection.getY());

			double disty = HaversineDistance.calculateLength(y.getX(),
					y.getY(), intersection.getX(), intersection.getY());

			if (disty <= range && distX <= range) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Merge nodes that are within X Km of each other to reduce the types of
	 * node. A node is only merged if either one of them is either a START or an
	 * END node type.If a node is merged it returns true, else false.
	 * 
	 * @param Node
	 *            n1 - the node we are comparing to. Node n2 the target node, if
	 *            this matches n2 we will copy the outbound and inbout edge
	 *            Information from this node to n1.
	 * 
	 */
	protected void mergeNode(Node n1, Node n2) {

		System.out.println("Merging Nodes " + n1 + " and " + n2);

		// All of n2's outbound edges now come from n1.
		for (Edge e : n2.getOutboudEdges()) {
			// set the from value
			e.setFrom(n1);
			// update n1.
			n1.getOutboudEdges().add(e);
		}

		// All of n2's inbound edges now finish at n1
		for (Edge e : n2.getInboundEdges()) {
			// set the from value
			e.setTo(n1);
			// update n1.
			n1.getInboundEdges().add(e);
		}

		// Everywhere n2 was in a piste we need to replace it with n1
		for (Piste p : pistes) {
			int index = p.getNodes().indexOf(n2);
			if (index > -1) {
				p.getNodes().set(index, n1);
				// add this piste to the node
				n1.getPistes().add(p);
			}
		}

		// if n2 is an end or start node then mark n1 as a start node
		if (n2.isEnd()) {
			n1.setEnd(true);
		}
		if (n2.isStart()) {
			n1.setStart(true);
		}

	}

	/**
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * @return the nodes
	 */
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes
	 *            the nodes to set
	 */
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the edges
	 */
	public List<Edge> getEdges() {
		return edges;
	}

	/**
	 * @param edges
	 *            the edges to set
	 */
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	/**
	 * @return the pistes
	 */
	public List<Piste> getPistes() {
		return pistes;
	}

	/**
	 * @param pistes
	 *            the pistes to set
	 */
	public void setPistes(List<Piste> pistes) {
		this.pistes = pistes;
	}

	/**
	 * @param graph
	 *            the graph to set
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
}
