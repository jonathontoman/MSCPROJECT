package skipiste.importer.kml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;
import skipiste.graph.elements.Section;

/**
 * Tests the KMLtoGraph class builds a graph as we expect it. We use a known xml
 * set we have created to check that the data produced by the handler is
 * correct. The handler is responsible for producing three things, a List of
 * Edges, a List of nodes, and a List of pistes so we will check all three
 * pieces of data are correct.
 * 
 * @author s1011122
 * 
 */
public class TestKMLHandler {

	/**
	 * The location of our test data. The file name here is treated as XML ,
	 * this is just convenience for the eclipse editor and editing XML.
	 */
	private static final String KML = "TestSkiMap.xml";

	/**
	 * The name of the first piste.
	 */
	private static final String RUN1 = "Test Run 1";
	/**
	 * The name of the first piste.
	 */
	private static final String RUN2 = "Test Ski Run 2";

	/**
	 * the pistes built by the handler.
	 */
	private List<Piste> pistes;
	/**
	 * the nodes built by the handler.
	 */
	private List<Node> nodes;
	/**
	 * The edges build by the handler.
	 */
	private List<Edge> edges;

	/**
	 * The SAX handler for the KML file
	 */
	private KMLHandler handler;
	private SAXParserFactory spf;
	private SAXParser saxParser;
	private XMLReader xmlReader;

	@Before
	public void setup() {
		// Configure the SAX parsing.
		spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		try {
			saxParser = spf.newSAXParser();
			handler = new SkiMapHandler();
			xmlReader = saxParser.getXMLReader();
			xmlReader.setContentHandler(handler);

		} catch (ParserConfigurationException | SAXException e) {
			// Do Nothing other than print stack trace
			e.printStackTrace();
		}
		try {
			xmlReader.parse(this.getClass().getResource(KML).getFile());
			pistes = handler.getPistes();
			nodes = handler.getNodes();
			edges = handler.getEdges();
		} catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test that the handler builds all three of our collections
	 */
	@Test
	public void testCollectionsHaveBeenBuilt() {
		assertNotNull(pistes);
		assertNotNull(nodes);
		assertNotNull(edges);
	}

	/**
	 * Test that the handler has built the correct number of pistes(2)
	 */
	@Test
	public void testNumberOfPistesCorrect() {
		// Number of pistes expected
		int expected = 2;
		int actual = pistes.size();
		assertEquals(expected, actual);
	}

	/**
	 * Test that the pistes have been named correctly ("Test Run 1" and
	 * "Test Run 2")
	 */
	@Test
	public void testPisteNamesCorrect() {
		String expected1 = RUN1;
		String expected2 = RUN2;

		boolean expected1match = false;
		boolean expected2match = false;

		ArrayList<String> actualNames = new ArrayList<String>();

		// get all the names of the pistes
		for (Piste p : pistes) {
			if (p.getName().equals(expected1)) {
				expected1match = true;
			}
			if (p.getName().equals(expected2)) {
				expected2match = true;
			}
		}

		assertTrue("No match for Name : Test Run 1", expected1match);
		assertTrue("No match for Name : Test Ski Run 2", expected2match);
	}

	/**
	 * Test both ski pistes have the correct number of nodes Test Run 1 has 10
	 * and Test Ski Run 2 has 11.
	 */
	@Test
	public void testPisteNodeCountIsCorrect() {
		int expected1 = 10;
		int expected2 = 11;

		for (Piste p : pistes) {
			if (p.getName().equals(RUN1)) {
				assertEquals(p.getNodes().size(), expected1);
			} else if (p.getName().equals(RUN2)) {
				assertEquals(p.getNodes().size(), expected2);
			} else {
				fail();
			}
		}
	}

	/**
	 * Test we have the correct number of nodes;
	 */
	@Test
	public void testNodeCountCorrect() {
		// expect 21 nodes
		int expected = 21;
		assertEquals(expected, nodes.size());
	}

	/**
	 * Test all nodes unique
	 */
	@Test
	public void testNodesUnique() {
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = i + 1; j < nodes.size(); j++) {
				if (i != j) {

					Node nodeA = nodes.get(i);
					Node nodeB = nodes.get(j);
					assertNotEquals(nodeA, nodeB);
				}
			}
		}
	}

	/**
	 * Test all nodes have one outbound edge and one inbound edge and that these
	 * are different, unless they are a start node in which they only have one
	 * outbound edge or a end not in which case they only have one inbound edge.
	 */
	@Test
	public void testNodeEgdesAreCorrect() {
		for (Node n : nodes) {
			if (n.getSection() == Section.START) {
				// expect 1 outboud node
				assertEquals("Node " + n + "is incorrect", 1, n
						.getOutboudEdges().size());
				// expect 0 inboud node
				assertEquals("Node " + n + "is incorrect", 0, n
						.getInboundEdges().size());

			} else if (n.getSection() == Section.END) {
				// expect 1 inboud node
				assertEquals("Node " + n + "is incorrect", 1, n
						.getInboundEdges().size());
				// expect 0 outboud node
				assertEquals("Node " + n + "is incorrect", 0, n
						.getOutboudEdges().size());

			} else {
				// expect 1 outboud node
				assertEquals("Node " + n + "is incorrect", 1, n
						.getOutboudEdges().size());
				// expect 1 outboud node
				assertEquals("Node " + n + "is incorrect", 1, n
						.getInboundEdges().size());
				assertNotEquals("Node " + n + "is incorrect",
						n.getOutboudEdges(), n.getInboundEdges());
			}
		}
	}

	/**
	 * Test we have the correct number of edges; We expected 9 edges for piste 1
	 * and 10 for piste 2
	 */
	@Test
	public void testEdgeCountCorrect() {
		// expect 19 edges
		int expected = 19;
		assertEquals(expected, edges.size());
	}

	/**
	 * Test all edges unique
	 */
	@Test
	public void testEdgesUnique() {
		for (int i = 0; i < edges.size(); i++) {
			for (int j = i + 1; j < edges.size(); j++) {
				if (i != j) {

					Edge edgeA = edges.get(i);
					Edge edgeB = edges.get(j);
					assertNotEquals(edgeA, edgeB);
				}
			}
		}
	}
	
	/**
	 * Test all Edges have one origin and one termins node and that these nodes are different.
	 */
	@Test
	public void testEdgeNodesAreCorrect() {
		
		for (Edge e : edges)
		{
			assertNotNull(e.getFrom());
			assertNotNull(e.getTo());
			assertNotEquals(e.getFrom(), e.getTo());			
		}
	}


}
