package skipiste.graph;

import java.util.HashSet;
import java.util.Set;

import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;

/**
 * This class represents a graph in the form of an incidence list of Nodes and
 * Edges.
 */
public class Graph {

	/**
	 * Set of nodes that are held within this graph
	 */
	private Set<Node> nodes;
	/**
	 * Set of edges that are held within this graph.
	 */
	private Set<Edge> edges;
	/**
	 * Set of Pistes
	 */
	private Set<Piste> pistes;

	public Graph(HashSet<Piste> pistes, HashSet<Node> nodes, HashSet<Edge> edges) {
		// Use sets so we dont have duplicates.

		this.nodes = nodes;		
		this.edges = edges;
		this.pistes = pistes;
	}

	/**
	 * Get the nodes of this graph.
	 * 
	 * @return
	 */
	public Set<Node> getNodes() {
		return nodes;
	}

	/**
	 * Set the nodes of this graph
	 * 
	 * @param nodes
	 */
	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * get the edges of this graph.
	 * 
	 * @return
	 */
	public Set<Edge> getEdges() {
		return edges;
	}

	/**
	 * Set the edges of this graph.
	 * 
	 * @param edges
	 */
	public void setEdges(Set<Edge> edges) {
		this.edges = edges;
	}

	/**
	 * @return the pistes
	 */
	public Set<Piste> getPistes() {
		return pistes;
	}

	/**
	 * @param pistes
	 *            the pistes to set
	 */
	public void setPistes(Set<Piste> pistes) {
		this.pistes = pistes;
	}

}
