package skipiste.importer.kml;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import skipiste.geometry.LineSegment;
import skipiste.geometry.Point;
import skipiste.graph.Graph;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;
import skipiste.graph.elements.Section;
import skipiste.utils.HaversineDistance;
import skipiste.utils.OutputKML;

/**
 * 
 * Builds a graph from a KML source file.
 * 
 * @author s1011122
 * 
 */
public class KMLToGraph {
	/**
	 * The SAX handler for the KML file
	 */
	private KMLHandler handler;
	private SAXParserFactory spf;
	private SAXParser saxParser;
	private XMLReader xmlReader;
	/**
	 * Nodes that we build up from the KML file and modify in this class.
	 */
	private List<Node> nodes;
	/**
	 * Edges that we build up from the KML file and modify in this class.
	 */
	private List<Edge> edges;
	/**
	 * Pistes that we build up from the KML file and modify in this class.
	 */
	private List<Piste> pistes;

	public KMLToGraph(KMLHandler handler) {

		// initialise the handler

		this.handler = handler;

		// Configure the SAX parsing.
		spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		try {
			saxParser = spf.newSAXParser();
			xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(handler);

		} catch (ParserConfigurationException | SAXException e) {
			// Do Nothing other than print stack trace
			e.printStackTrace();
		}

	}

	public Graph importGraph(String kmlFile) {

		try {
			xmlReader.parse(kmlFile);
		} catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// the graph built by the KML handler will produce n number of
		// unconnected graphs, one for each named piste in the origin data.
		// we need to go through three processes to connected theses graphs.
		nodes = handler.getNodes();
		edges = handler.getEdges();
		pistes = handler.getPistes();
		
		
		
		// Stage 1: create new nodes where ever line segments intersect. To do
		// this we will examine all edges against each other, if those edges
		// plotted onto a plane would intersect we will create a new node that
		// represents the intersection point. For each new node created we will
		// need to also add the inbound and outbound edges, and adjust these on
		// the related nodes accordingly.
		System.out
				.println("Outputting coordinates where lines intersect in order Latitude, Longitude, this means we can just put them into google earth to check ");

		for (int i = 0; i < edges.size(); i++) {
			for (int j = i + 1; j < edges.size(); j++) {
				if (i != j) {

					Edge edgeA = edges.get(i);
					Edge edgeB = edges.get(j);
					// Check if they belong to the same piste, if not see if we
					// should merge them based on being within 10m of each
					// other.
					if (edgeA.getPiste() != edgeB.getPiste()) {
						intersectAndMerge(edges.get(i), edges.get(j));
					}
				}
			}
		}

//		// Stage 1: wherever we have START or END nodes that are within 10 m of
//		// each other merge these nodes into one
//		for (int i = 0; i < nodes.size(); i++) {
//			for (int j = i + 1; j < nodes.size(); j++) {
//				if (i != j) {
//
//					Node nodeA = nodes.get(i);
//					Node nodeB = nodes.get(j);
//					// Check if they belong to the same piste, if not see if we
//					// should merge them based on being within 10m of each
//					// other.
//
//					if (!nodeA.getPistes().containsAll(nodeB.getPistes())) {
//						compareAndMergeStartOrEnd(nodeA, nodeB, 0.01);
//					}
//
//				}
//			}
//		}



		// Stage 3, Stage 1 produces duplicates, merge all nodes with 1 m of
		// each other

		for (int i = 0; i < nodes.size(); i++) {
			for (int j = i + 1; j < nodes.size(); j++) {
				if (i != j) {

					Node nodeA = nodes.get(i);
					Node nodeB = nodes.get(j);
					// Check if they belong to the same piste, if not see if we
					// should merge them based on being within 10m of each
					// other.

					if (!nodeA.getPistes().containsAll(nodeB.getPistes())) {
						compareAndMerge(nodeA, nodeB, 0.001);
					}

				}
			}
		}

		// remove any null values from our lists
		nodes.removeAll(Collections.singleton(null));
		edges.removeAll(Collections.singleton(null));
		pistes.removeAll(Collections.singleton(null));
		
		
		// Stage 4 build the weight for each edge on the graph
		for (Edge e : edges)
		{
			e.setWeight(HaversineDistance.calculateDistance(e.getFrom(), e.getTo()));
		}

		return new Graph(pistes, nodes, edges);
	}

	private void compareAndMergeStartOrEnd(Node n1, Node n2, double delta) {
		if (n1.getSection().equals(Section.START)
				|| n2.getSection().equals(Section.START)
				|| n1.getSection().equals(Section.END)
				|| n2.getSection().equals(Section.END)) {
			compareAndMerge(n1, n2, delta);
		}
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
	 * @param deltaKm
	 *            the difference in Kilometers , less than this and the nodes
	 *            will be merged.
	 * 
	 * @return boolean - true - the nodes are similar enough to merge and have
	 *         been merged, else false.
	 */
	private void compareAndMerge(Node n1, Node n2, double deltaKm) {

		double distance = HaversineDistance.calculateDistance(n1, n2);
		if (distance < deltaKm) {

			System.out.println("Merging Nodes " + n1 + " and " + n2);
			// 1. change the inbound edges
			Set<Edge> inbound = n2.getInboundEdges();
			// All inbound edges now have a new destination which is node 1.
			for (Edge e : inbound) {
				e.setTo(n1);
			}
			// Node 1 now has the list of edges from n2 added to its List of
			// inbound edges
			n1.getInboundEdges().addAll(inbound);

			// 2. change the outbound edges
			Set<Edge> outbound = n2.getOutboudEdges();
			n1.getPistes().addAll(n2.getPistes());

			// All inbound edges now have a new destination which is node 1.
			for (Edge e : outbound) {
				e.setFrom(n1);
			}
			// Node 1 now has the list of edges from n2 added to its List of
			// inbound edges
			n1.getOutboudEdges().addAll(outbound);

			if (n1.getPistes().size() > 1) {
				n1.setSection(Section.JUNCTION);
			}

			// set the node n2 to null, we no longer need it
			n2 = null;
		}
	}

	private void intersectAndMerge(Edge e1, Edge e2) {

		Point p1 = new Point(e1.getFrom().getLongitude(), e1.getFrom()
				.getLattitude());
		Point p2 = new Point(e1.getTo().getLongitude(), e1.getTo()
				.getLattitude());
		Point p3 = new Point(e2.getFrom().getLongitude(), e2.getFrom()
				.getLattitude());
		;
		Point p4 = new Point(e2.getTo().getLongitude(), e2.getTo()
				.getLattitude());

		LineSegment line1 = new LineSegment(p1, p2);
		// Check for the intersection a delta value here of 0.00001 works nicely
		// with the test data so it will take more trial and error.
		Point intersection = line1.intersectionPoint(new LineSegment(p3, p4),
				0.00001);

		// if the intersection is not null we will create a new node to
		// represent the intersection
		// and adjust the edges to join this node.
		if (intersection != null) {
			System.out.println(OutputKML.outputPlaceMark(intersection.getX(),
					intersection.getY()));

			// create a new node
			createNewNode(e1, e2, intersection);
			;

		}
	}

	/**
	 * Create new node at at the intersection of two edges.
	 * 
	 * @param edge1
	 *            - edge 1
	 * @param edge2
	 *            - edge 2
	 * @param nodes
	 *            the arraylist that this node will be added to
	 * @param intersection
	 *            - the point which at which the edges intersect and where we
	 *            will build the new node.
	 */
	private void createNewNode(Edge edge1, Edge edge2, Point intersection) {

		// Step 1: build the new node with the information from the Edges
		// we will need to add the piste information later.

		Node n = new Node();
		n.setLattitude(intersection.getY());
		n.setLongitude(intersection.getX());
		n.getPistes().add(edge1.getPiste());
		n.getPistes().add(edge2.getPiste());
		// n of type JUNCTION as we know it will have multiple pises
		n.setSection(Section.JUNCTION);

		// Step 3: edge1 now terminates at the new node, we need to make an new
		// edge, edge3, that originates at the new node and terminates and node
		// that edge1 originally terminated.
		// Keep hold of edge1's original destination
		Node destination = edge1.getTo();
		edge1.setTo(n);

		Edge edge3 = new Edge();
		edge3.setTo(destination);
		edge3.setFrom(n);
		// it is still part of the same piste as edge1
		edge3.setPiste(edge1.getPiste());

		// Step 3: edge2 now terminates at the new node, we need to make an new
		// edge, edge4, that originates at the new node and terminates and node
		// that edge2 originally terminated.

		// Keep hold of edge2's original destination
		destination = edge2.getTo();
		edge2.setTo(n);

		Edge edge4 = new Edge();
		edge4.setTo(destination);
		edge4.setFrom(n);
		// it is still part of the same piste as edge1
		edge3.setPiste(edge2.getPiste());

		// Step 4:, add the piste information to the new node
		Piste p1 = edge1.getPiste();
		Piste p2 = edge2.getPiste();
		// Add these to the node
		n.getPistes().add(p1);
		n.getPistes().add(p2);

		// Step 5:update the piste array, we need to add the new nodes to the
		// list of nodes that makes up a piste.
		// As nodes are stored in a linked list we can add them in the correct
		// place in the piste.
		// Piste stores the nodes as a linked list so we know the order is
		// preseverd so we need to stick them in the correct place
		int pisteIndex = pistes.indexOf(p1);
		List<Node> nodeList = pistes.get(pisteIndex).getNodes();
		// add the new node after the origin node of edge 1;
		int nodeIndex = nodeList.indexOf(edge1.getFrom());
		nodeList.add(nodeIndex + 1, n);
		// do the same for the second piste;
		pisteIndex = pistes.indexOf(p2);
		nodeList = pistes.get(pisteIndex).getNodes();
		// add the new node after the origin node of edge 1;
		nodeIndex = nodeList.indexOf(edge2.getFrom());
		nodeList.add(nodeIndex + 1, n);
	}

	/**
	 * This method reduces the number of nodes and edges within a graph. It
	 * examines every node in a piste,
	 */
	private void rationaliseGraph() {

	}
}
