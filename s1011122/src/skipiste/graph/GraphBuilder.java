package skipiste.graph;

import java.util.ArrayList;

import skipiste.geometry.LineSegment;
import skipiste.geometry.Point;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Section;
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
	
	private int nodescreated;

	/**
	 * Constructor
	 */
	public GraphBuilder(String kmlFile) {

		// TODO - lookup a properties file to decide how to build the graph, for
		// now just do as below
		KMLHandler handler = new PlanDePisteHandler();
		KMLImporter importer = new KMLImporter(handler, kmlFile);
		nodescreated=0;
		graph = new Graph(importer.getPistes(), importer.getNodes(),
				importer.getEdges());
		predictNewNodes();
		mergeSimilarNodes();
		
		System.out.println("Nodes created = " + nodescreated);

	}

	private void mergeSimilarNodes() {
		// TODO Auto-generated method stub

	}

	/**
	 * Based on line geometry calculate where edges intersect and create a new
	 * node to join the intersecting pistes. Updates and creates the node, and
	 * the related nodes and edges.
	 */
	private void predictNewNodes() {
		// Get all the edges we know about already
		// We use a set here as we dont want duplicates
		ArrayList<Edge> edges  = new ArrayList<Edge>(graph.getEdges());

		// compare all these edges against each other.
		for (int i = 0; i < edges.size(); i++) {
			for (int j = i + 1; j < edges.size(); j++) {
				Edge e1 = edges.get(i);
				if (i != j) {
					Edge e2 = edges.get(j);
					// Check if they belong to the same piste, if not see if we
					// should merge them based on being within 10m of each
					// other.
					if (!e1.getPiste().getName().equals(e2.getPiste().getName())) {
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

//						// if the intersection falls on either edge create a new
//						// node
//						if (line1.contains(intersection)) {
//							createNode(intersection, e1, e2);
//						}
						// our data isnt perfect so if the intersection falls
						// within
						// 20m of the points that make up the line segments
						// create
						// the node
						if (pointWithinRange(intersection, line1,line2, 15)) {
							createNode(intersection, e1, e2);
						}
					}
				}
			}
		}

		// Iterator<Edge> it = edges.iterator();
		// while (it.hasNext()) {
		// Edge e1 = it.next();
		// it.remove();
		// for (Edge e2 : edges) {
		// if (e1.getPiste() == e2.getPiste()) {
		//
		// Point p1 = new Point(e1.getFrom().getLongitude(), e1
		// .getFrom().getLattitude());
		// Point p2 = new Point(e1.getTo().getLongitude(), e1.getTo()
		// .getLattitude());
		// Point p3 = new Point(e2.getFrom().getLongitude(), e2
		// .getFrom().getLattitude());
		// Point p4 = new Point(e2.getTo().getLongitude(), e2.getTo()
		// .getLattitude());
		//
		// LineSegment line1 = new LineSegment(p1, p2);
		// LineSegment line2 = new LineSegment(p3, p4);
		// Point intersection = line1.intersectionPoint(line2);
		//
		// // if the intersection falls on either edge create a new node
		// if (line1.contains(intersection)) {
		// createNode(intersection, e1, e2);
		// }
		// // our data isnt perfect so if the intersection falls within
		// // 20m of the points that make up the line segments create
		// // the node
		// else if (pointWithinRange(intersection, p1, p2, p3, p4,
		// 0.02)) {
		// createNode(intersection, e1, e2);
		// }
		// }
		// }
		// }

	}

	/**
	 * Creates a new node at the intersection that has been passed to this
	 * method.
	 */
	private void createNode(Point intersection, Edge e1, Edge e2) {
		// if the intersection is not null we will create a new node to
		// represent the intersection
		// and adjust the edges to join this node.
		if (intersection != null) {
			nodescreated++;
			Node n = new Node();
			n.setLattitude(intersection.getY());
			n.setLongitude(intersection.getX());
			n.getPistes().add(e1.getPiste());
			n.getPistes().add(e2.getPiste());
			n.setSection(Section.JUNCTION);
			// We have only predicted that this intersection exists.
			n.setPredicted(true);
			// add this new node to our existing list of nodes
			graph.getNodes().add(n);

			// new edge 1
			Edge edge1 = new Edge(e1.getFrom(), n, e1.getPiste());
			graph.getEdges().remove(e1);
			graph.getEdges().add(edge1);

			// new edge 2
			Edge edge2 = new Edge(e2.getFrom(), n, e2.getPiste());
			graph.getEdges().remove(e2);
			graph.getEdges().add(edge2);

			// we also have new edges 3 and 4 to add
			Edge edge3 = new Edge(n, e1.getTo(), e1.getPiste());
			graph.getEdges().add(edge3);
			Edge edge4 = new Edge(n, e2.getTo(), e2.getPiste());
			graph.getEdges().add(edge4);

			// our new node is now in two pistes, update these accordingly
			e1.getPiste().getNodes()
					.add(e1.getPiste().getNodes().indexOf(e1.getFrom()), n);
			e2.getPiste().getNodes()
					.add(e2.getPiste().getNodes().indexOf(e2.getFrom()), n);
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
	private boolean pointWithinRange(Point intersection,LineSegment line1, LineSegment line2, double range) 
	{
		
		Point x = line1.closestPointOnLine(intersection);
		double distX = HaversineDistance.calculateLength(x.getX(), x.getY(), intersection.getX(), intersection.getY());
		
		Point y = line2.closestPointOnLine(intersection);
		double disty = HaversineDistance.calculateLength(y.getX(), y.getY(), intersection.getX(), intersection.getY());

		if (disty <= range && distX <= range)
		{
			return true;
		}
		
		return false;

	}

	/**
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * @param graph
	 *            the graph to set
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
	}

}
