package skipiste.graph;

import java.util.Set;

import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.utils.OutputKML;

/**
 * Performs basic graph validation.
 * 
 * @author s1011122
 * 
 */
public class GraphValidator {

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
	public static void validateGraph(Graph g) {

		// validate edges
		validateEdges(g.getEdges());
		// validate nodes
		validateNodes(g.getNodes());
	}

	/**
	 * Validates edges All edges must have one origin and one destination, and
	 * these must be different Edges must belong to a piste Must not have null
	 * weight
	 * 
	 * @param edges
	 */
	public static void validateEdges(Set<Edge> edges) {
		// check the edges
		for (Edge e : edges) {
			if (e.getFrom() == null) {
				System.err.println("Found edge with no origin node in piste "
						+ e.getPiste());
			}
			if (e.getTo() == null) {
				System.err
						.println("Found edge with no destination node in piste "
								+ e.getPiste());
			}
			if (e.getFrom().equals(e.getTo())) {
				System.err
						.println("Found edge with same origin node and desitination node in piste "
								+ e.getPiste());
			}

			if (e.getPiste() == null) {
				System.err.println("Found edge not beloning to a piste");
			}

			if (e.getWeight() == null) {
				System.err.println("Found edge with null weight");
			}
		}
	}

	/**
	 * 1) All Start nodes must have at least one outbound node 2) All End nodes
	 * must have at least one inbound node 3) All Interections must have (a)
	 * More than 1 outbout node and 1 inbound node OR (b) more than 1 inbound
	 * node and 1 outbound node OR (c) more than 1 outbound node and more than 1
	 * inbound node. 4) All other nodes must have 1 outbound node and 1 inbound
	 * node. If any of these conditions are not met then the graph has not built
	 * correctly
	 */
	public static void validateNodes(Set<Node> nodes) {
		for (Node n : nodes) {
			int outboundEdges = n.getOutbound().size();
			int inboundEdges = n.getInbound().size();

			if (n.isStart()) {
				if (outboundEdges < 1) {
					System.err
							.println("Found Start Node with no outbound edges in piste "
									+ n.getPistes());
					System.err.println(OutputKML.outputPlaceMark(
							n.getLongitude(), n.getLatitude()));
				}
			}

			if (n.isEnd()) {
				if (inboundEdges < 1) {
					System.err
							.println("Found End Node with no inbound edges in piste"
									+ n.getPistes());
					System.err.println(OutputKML.outputPlaceMark(
							n.getLongitude(), n.getLatitude()));
				}
			}

			if (n.isIntersection() && !n.isStart() && !n.isEnd()) {
				if (inboundEdges <= 1 && outboundEdges <= 1) {
					System.err
							.println("Found Intersection that does not join two pistes in piste"
									+ n.getPistes());

					System.err.println(OutputKML.outputPlaceMark(
							n.getLongitude(), n.getLatitude()));
				}
			}

			if (!n.isIntersection() && !n.isStart() && !n.isEnd()) {
				if (outboundEdges < 1) {
					System.err
							.println("Found Node with no outbound edges that is not an End node in piste"
									+ n.getPistes());
					System.err.println(OutputKML.outputPlaceMark(
							n.getLongitude(), n.getLatitude()));

				}
				if (inboundEdges < 1) {
					System.err
							.println("Found Node with no inbound edges that is not a Start node in piste"
									+ n.getPistes());
					System.err.println(OutputKML.outputPlaceMark(
							n.getLongitude(), n.getLatitude()));
				}
			}

			if (n.getPistes() == null) {
				System.err.println("Found Node that is not part of a Piste");
			}

			validateEdges(n.getOutbound());
			validateEdges(n.getInbound());

		}
	}
}
