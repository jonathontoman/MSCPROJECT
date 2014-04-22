package skipiste.graph;

import java.util.HashSet;

import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;

/**
 * Helper class to build the required data for testing
 * skipiste.graph.GraphBuilder
 * 
 * @author s1011122
 * 
 */
public class GraphBuilderTestHelper {

	private HashSet<Node> nodes;
	private HashSet<Piste> pistes;
	private HashSet<Edge> edges;

	public GraphBuilderTestHelper() {

		// init sets
		nodes = new HashSet<Node>();
		pistes = new HashSet<Piste>();
		edges = new HashSet<Edge>();

		// Build Piste 1
		Piste p1 = new Piste();
		p1.setName("Piste1");
		// Add piste to the set
		pistes.add(p1);

		// 1st set of nodes
		Node a0 = new Node(1.5, 10, true, false);
		Node a1 = new Node(2, 7, false, false);
		Node a2 = new Node(2.5, 6, false, false);
		Node a3 = new Node(2.25, 4.5, false, false);
		Node a4 = new Node(3, 3.5, false, false);
		Node a5 = new Node(3.5, 3, false, false);
		Node a6 = new Node(5, 1.5, false, false);
		Node a8 = new Node(5, -1.5, false, false);
		Node a9 = new Node(4, -2, false, false);
		Node a10 = new Node(3, -3, false, true);
		// Add the nodes to the set
		nodes.add(a0);
		nodes.add(a1);
		nodes.add(a2);
		nodes.add(a3);
		nodes.add(a4);
		nodes.add(a5);
		nodes.add(a6);
		nodes.add(a8);
		nodes.add(a9);
		nodes.add(a10);
		// Also add the nodes to the piste
		p1.getNodes().add(a0);
		p1.getNodes().add(a1);
		p1.getNodes().add(a2);
		p1.getNodes().add(a3);
		p1.getNodes().add(a4);
		p1.getNodes().add(a5);
		p1.getNodes().add(a6);
		p1.getNodes().add(a8);
		p1.getNodes().add(a9);
		p1.getNodes().add(a10);

		// build the edges for these nodes
		Edge z0 = new Edge(a0, a1, p1);
		Edge z1 = new Edge(a1, a2, p1);
		Edge z2 = new Edge(a2, a3, p1);
		Edge z3 = new Edge(a3, a4, p1);
		Edge z4 = new Edge(a4, a5, p1);
		Edge z5 = new Edge(a5, a6, p1);
		Edge z6 = new Edge(a6, a8, p1);
		Edge z8 = new Edge(a8, a9, p1);
		Edge z9 = new Edge(a9, a10, p1);

		edges.add(z0);
		edges.add(z1);
		edges.add(z2);
		edges.add(z3);
		edges.add(z4);
		edges.add(z5);
		edges.add(z6);
		edges.add(z8);
		edges.add(z9);

		// Build Piste 2
		Piste p2 = new Piste();
		p2.setName("Piste2");
		// Add piste to the set
		pistes.add(p2);
		// 2ns set of nodes
		Node b0 = new Node(4, 8, true, false);
		Node b1 = new Node(6, 7, false, false);
		Node b2 = new Node(6, 6, false, false);
		Node b3 = new Node(5, 4.5, false, false);
		Node b4 = new Node(5, 3, false, false);
		Node b5 = new Node(4, 1.5, false, false);
		Node b6 = new Node(3, 0.5, false, false);
		Node b7 = new Node(2, 0, false, false);
		Node b8 = new Node(1, 0, false, false);
		Node b9 = new Node(-1, -1, false, false);
		Node b10 = new Node(-1, -3, false, false);
		Node b11 = new Node(-2.5, -4, false, true);
		// Add the nodes to the set
		nodes.add(b0);
		nodes.add(b1);
		nodes.add(b2);
		nodes.add(b3);
		nodes.add(b4);
		nodes.add(b5);
		nodes.add(b6);
		nodes.add(b7);
		nodes.add(b8);
		nodes.add(b9);
		nodes.add(b10);
		nodes.add(b11);
		// Also add the nodes to the piste
		p2.getNodes().add(b0);
		p2.getNodes().add(b1);
		p2.getNodes().add(b2);
		p2.getNodes().add(b3);
		p2.getNodes().add(b4);
		p2.getNodes().add(b5);
		p2.getNodes().add(b6);
		p2.getNodes().add(b7);
		p2.getNodes().add(b8);
		p2.getNodes().add(b9);
		p2.getNodes().add(b10);
		p2.getNodes().add(b11);

		// build the edges for these nodes
		Edge y0 = new Edge(b0, b1, p2);
		Edge y1 = new Edge(b1, b2, p2);
		Edge y2 = new Edge(b2, b3, p2);
		Edge y3 = new Edge(b3, b4, p2);
		Edge y4 = new Edge(b4, b5, p2);
		Edge y5 = new Edge(b5, b6, p2);
		Edge y6 = new Edge(b6, b7, p2);
		Edge y7 = new Edge(b7, b8, p2);
		Edge y8 = new Edge(b8, b9, p2);
		Edge y9 = new Edge(b9, b10, p2);
		Edge y10 = new Edge(b10, b11, p2);

		edges.add(y0);
		edges.add(y1);
		edges.add(y2);
		edges.add(y3);
		edges.add(y4);
		edges.add(y5);
		edges.add(y6);
		edges.add(y7);
		edges.add(y8);
		edges.add(y9);
		edges.add(y10);

		// Build Piste 3
		Piste p3 = new Piste();
		p3.setName("Piste3");
		// Add piste to the set
		pistes.add(p3);
		// 3rd set of nodes
		Node c0 = new Node(1, 10, true, false);
		Node c1 = new Node(1, 6, false, false);
		Node c2 = new Node(4, 5, false, false);
		Node c3 = new Node(6, 5, false, false);
		Node c4 = new Node(6, 3, false, false);
		Node c5 = new Node(6, 1, false, false);
		Node c6 = new Node(4, 0, false, false);
		Node c7 = new Node(3, -2, false, false);
		Node c8 = new Node(1, -2, false, false);
		Node c9 = new Node(-3, -1, false, false);
		Node c10 = new Node(-3, -4, false, true);
		// Add the nodes to the set
		nodes.add(c0);
		nodes.add(c1);
		nodes.add(c2);
		nodes.add(c3);
		nodes.add(c4);
		nodes.add(c5);
		nodes.add(c6);
		nodes.add(c7);
		nodes.add(c8);
		nodes.add(c9);
		nodes.add(c10);
		// Also add the nodes to the piste
		p3.getNodes().add(c0);
		p3.getNodes().add(c1);
		p3.getNodes().add(c2);
		p3.getNodes().add(c3);
		p3.getNodes().add(c4);
		p3.getNodes().add(c5);
		p3.getNodes().add(c6);
		p3.getNodes().add(c7);
		p3.getNodes().add(c8);
		p3.getNodes().add(c9);
		p3.getNodes().add(c10);

		// build the edges for these nodes
		Edge x0 = new Edge(c0, c1, p3);
		Edge x1 = new Edge(c1, c2, p3);
		Edge x2 = new Edge(c2, c3, p3);
		Edge x3 = new Edge(c3, c4, p3);
		Edge x4 = new Edge(c4, c5, p3);
		Edge x5 = new Edge(c5, c6, p3);
		Edge x6 = new Edge(c6, c7, p3);
		Edge x7 = new Edge(c7, c8, p3);
		Edge x8 = new Edge(c8, c9, p3);
		Edge x9 = new Edge(c9, c10, p3);

		edges.add(x0);
		edges.add(x1);
		edges.add(x2);
		edges.add(x3);
		edges.add(x4);
		edges.add(x5);
		edges.add(x6);
		edges.add(x7);
		edges.add(x8);
		edges.add(x9);

	}

	public HashSet<Node> buildStartEndNodes() {
		HashSet<Node> nodes = new HashSet<Node>();

		// build start nodes
		Node s1 = new Node(1, 1, true, false);
		Node s2 = new Node(1, 2, true, false);
		Node s3 = new Node(5, 5, true, false);

		// build end nodes
		Node e1 = new Node(3, 10, false, true);
		Node e2 = new Node(3, 11, false, true);
		Node e3 = new Node(13, 14, false, true);

		// build edges between s1 -s2, s2-s1 and e1-e2 and e2-e1

		s1.getOutboudEdges().add(new Edge(s1, s2));
		s2.getOutboudEdges().add(new Edge(s2, s1));

		e1.getOutboudEdges().add(new Edge(e1, e2));
		e2.getOutboudEdges().add(new Edge(e2, e1));

		nodes.add(s1);
		nodes.add(s2);
		nodes.add(s3);
		nodes.add(e1);
		nodes.add(e2);
		nodes.add(e3);

		return nodes;
	}

	/**
	 * Build a HashSet<Piste> containing two pistes, one with 11 nodes one with
	 * 10, one of the nodes is found in both pistes.
	 * 
	 * @return HashSet<Piste>
	 */
	public HashSet<Piste> getPistes() {
		return pistes;
	}

	/**
	 * Build a HashSet<Node> containing 9 nodes with a reference to piste 1, 10
	 * nodes with a reference to piste 2 and one node with a reference to piste
	 * 1 and piste 2
	 * 
	 * @return HashSet<Nodes>
	 */
	public HashSet<Node> getNodes() {
		return nodes;
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
	 * @param nodes
	 *            the nodes to set
	 */
	public void setNodes(HashSet<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @param pistes
	 *            the pistes to set
	 */
	public void setPistes(HashSet<Piste> pistes) {
		this.pistes = pistes;
	}

}
