package skipiste.graph;

import java.util.ArrayList;

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

	private ArrayList<Node> nodes;
	private ArrayList<Piste> pistes;

	public GraphBuilderTestHelper() {

		// init sets
		nodes = new ArrayList<Node>();
		pistes = new ArrayList<Piste>();

		// Build Piste 1
		Piste p1 = new Piste();
		p1.setName("Piste1");
		// Add piste to the set
		pistes.add(p1);

		// 1st set of nodes
		Node n1 = new Node(2, 7, true, false, p1);
		Node n2 = new Node(2.5, 6, false, false, p1);
		Node n3 = new Node(2.25, 4.5, false, false, p1);
		Node n4 = new Node(3, 3.5, false, false, p1);
		Node n5 = new Node(4, 2.5, false, false, p1);
		Node n6 = new Node(5, 1.5, false, false, p1);
		Node n7 = new Node(6, 0.5, false, false, p1);
		Node n8 = new Node(5, -1.5, false, false, p1);
		Node n9 = new Node(4, -2, false, false, p1);
		Node n10 = new Node(3, -3, false, false, p1);
		// Add the nodes to the set
		nodes.add(n1);
		nodes.add(n2);
		nodes.add(n3);
		nodes.add(n4);
		nodes.add(n5);
		nodes.add(n6);
		nodes.add(n7);
		nodes.add(n8);
		nodes.add(n9);
		nodes.add(n10);
		// Also add the nodes to the piste
		p1.getNodes().add(n1);
		p1.getNodes().add(n2);
		p1.getNodes().add(n3);
		p1.getNodes().add(n4);
		p1.getNodes().add(n5);
		p1.getNodes().add(n6);
		p1.getNodes().add(n7);
		p1.getNodes().add(n8);
		p1.getNodes().add(n9);
		p1.getNodes().add(n10);

	
		// Build Piste 2
		Piste p2 = new Piste();
		p2.setName("Piste2");
		// Add piste to the set
		pistes.add(p2);
		// 2ns set of nodes
		Node n11 = new Node(4, 8, true, false, p2);
		Node n12 = new Node(6, 7, false, false, p2);
		Node n13 = new Node(6, 6, false, false, p2);
		Node n14 = new Node(5, 4.5, false, false, p2);
		Node n15 = new Node(5, 3, false, false, p2);
		Node n16 = new Node(4, 1.5, false, false, p2);
		Node n17 = new Node(3, 0.5, false, false, p2);
		Node n18 = new Node(2, 0, false, false, p2);
		Node n19 = new Node(1, 0, false, false, p2);
		Node n20 = new Node(1, -1, false, false, p2);
		Node n21 = new Node(1, -3, false, true, p2);
		// Add the nodes to the set
		nodes.add(n11);
		nodes.add(n12);
		nodes.add(n13);
		nodes.add(n14);
		nodes.add(n15);
		nodes.add(n16);
		nodes.add(n17);
		nodes.add(n18);
		nodes.add(n19);
		nodes.add(n20);
		nodes.add(n21);
		// Also add the nodes to the piste
		p2.getNodes().add(n11);
		p2.getNodes().add(n12);
		p2.getNodes().add(n13);
		p2.getNodes().add(n14);
		p2.getNodes().add(n15);
		p2.getNodes().add(n16);
		p2.getNodes().add(n17);
		p2.getNodes().add(n18);
		p2.getNodes().add(n19);
		p2.getNodes().add(n20);
		p2.getNodes().add(n21);
	}

	/**
	 * Build a HashSet<Piste> containing two pistes, one with 11 nodes one with
	 * 10, one of the nodes is found in both pistes.
	 * 
	 * @return HashSet<Piste>
	 */
	public ArrayList<Piste> getPistes() {
		return pistes;
	}

	/**
	 * Build a HashSet<Node> containing 9 nodes with a reference to piste 1, 10
	 * nodes with a reference to piste 2 and one node with a reference to piste
	 * 1 and piste 2
	 * 
	 * @return HashSet<Nodes>
	 */
	public ArrayList<Node> getNodes() {
		return nodes;
	}

}
