package skipiste.importer.kml;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import skipiste.graph.Edge;
import skipiste.graph.Graph;
import skipiste.graph.Node;
import skipiste.importer.AbstractGraphImporter;
import skipiste.importer.GraphImporter;
import skipiste.utils.HaversineDistance;

/**
 * 
 * Builds a graph from a KML source file.
 * 
 * @author s1011122
 * 
 */
public class KMLToGraph extends AbstractGraphImporter implements GraphImporter {
	/**
	 * The SAX handler for the KML file
	 */
	private KMLHandler handler;
	private SAXParserFactory spf;
	private SAXParser saxParser;
	private XMLReader xmlReader;

	public KMLToGraph() {

		graph = new Graph();
		// Configure the SAX parsing.
		spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		try {
			saxParser = spf.newSAXParser();
			handler = new KMLHandler();
			xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(handler);

		} catch (ParserConfigurationException | SAXException e) {
			// Do Nothing other than print stack trace
			e.printStackTrace();
		}

	}

	@Override
	public Graph importGraph(String kmlFile) {

		try {
			xmlReader.parse(kmlFile);
		} catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get the data produced by the handler, this is still arranged in
		// pistes, we want to add these to the graph.
		// We also want to merge nodes that are close to each other. This is
		// becuase the KML data we have records each piste as distinct entites,
		// where pistes cross they they will have similar, or identical
		// coordinates, but will be distinct entries in our data. For the
		// purposes of building the graph any nodes in different pistes that are
		// fewer than 20m (0.02KM) apart will be "merged".
		List<LinkedList<Node>> list = handler.getGraphData();

		Iterator<LinkedList<Node>> it1 = list.iterator();

		while (it1.hasNext()) {
			LinkedList<Node> linkedList1 = it1.next();
			it1.remove();

			for (Node n1 : linkedList1) {
				int id = 0;
				Iterator<LinkedList<Node>> it2 = list.iterator();
				while (it2.hasNext()) {
					Iterator<Node> it3 = it2.next().iterator();
					while (it3.hasNext()) {
						Node n2 = it3.next();
						if (compareAndMerge(n1, n2, 0.01)) {

							System.out.println("Removing " + n2.toString()
									+ " as it is similar to " + n1.toString());
							it3.remove();

						}
					}
				}

				graph.getNodes().put(n1.getName() + id, n1);
				id++;
			}
		}

		return graph;

	}

	/**
	 * Merge nodes that are within X Km of each other to reduce the types of
	 * node. If a node is merged it returns true, else false.
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
	private boolean compareAndMerge(Node n1, Node n2, double deltaKm) {
		// Step one calculate distance between the two nodes if the distance is
		// less than 20m merge the nodes.
		double distance  = HaversineDistance.calculateDistance(n1, n2); 
		if (distance < deltaKm) {

			// 1. change the inbound edges
			List<Edge> inbound = n2.getInboundEdges();
			// All inbound edges now have a new destination which is node 1.
			for (Edge e : inbound) {
				e.setTo(n1);
			}
			// Node 1 now has the list of edges from n2 added to its List of
			// inbound edges
			n1.getInboundEdges().addAll(inbound);

			// 2. change the outbound edges
			List<Edge> outbound = n2.getInboundEdges();
			// All inbound edges now have a new destination which is node 1.
			for (Edge e : outbound) {
				e.setFrom(n1);
			}
			// Node 1 now has the list of edges from n2 added to its List of
			// inbound edges
			n1.getOutboudEdges().addAll(outbound);

			return true;
		}
		return false;
	}

}
