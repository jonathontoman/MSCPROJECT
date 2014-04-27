package skipiste.graph;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedList;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import skipiste.geometry.Point;
import skipiste.graph.elements.Edge;
import skipiste.graph.elements.Node;
import skipiste.graph.elements.Piste;
import skipiste.utils.distance.BasicDistance;
import skipiste.utils.distance.DistanceCalculator;

/**
 * The test class for the NewGraphBuilder class
 * 
 * @author s1011122
 * 
 */
public class TestNewGraphBuilder extends EasyMockSupport {

	/**
	 * The class under test
	 */
	private NewGraphBuilder classUnderTest;

	private GraphBuilderTestHelper helper;

	@Before
	/**
	 *  Initialise the test class
	 */
	public void setUp() {
		classUnderTest = new NewGraphBuilder();
		helper = new GraphBuilderTestHelper();
	}

	@Test
	/**
	 * Tests the method GraphBuilder.createEdges(LinkedList<Node> nodes) creates the correct edges between each node in the linked list of nodes
	 */
	public void testCreateEdges() {

		Node node1 = new Node(100, 150, false, false);
		Node node2 = new Node(101, 150, false, false);
		// create a basic linked list of nodes.
		LinkedList<Node> nodes = new LinkedList<Node>();
		nodes.add(node1);
		nodes.add(node2);

		// we expect this to create 1 edge, that starts at node 1 and finishes
		// at node2
		HashSet<Edge> edges = classUnderTest.createEdges(nodes);
		// we expect set containing only 1 edge
		assertEquals(1, edges.size());

		// Get the edge from the set
		Edge e = (Edge) edges.toArray()[0];
		// This edge should go from node1
		assertEquals(node1, e.getFrom());
		// This edge should go to node2
		assertEquals(node2, e.getTo());
	}

	@Test
	/**
	 * Test the method GraphBuilder.buildNodeAtIntersection(Point intersection, Piste piste1, Piste piste2) create the Node object from the given arguments.
	 */
	public void testBuildNodeAtIntersection() {
		// The intersection
		int expectedX = 100;
		int expectedY = 200;
		Point intersection = new Point(expectedX, expectedY);

		// The basic pistes
		Piste p1 = new Piste();
		p1.setName("Piste1");
		Piste p2 = new Piste();
		p2.setName("Piste2");

		Node n = classUnderTest.buildNodeAtIntersection(intersection, p1, p2);
		assertEquals(expectedX, n.getLongitude(), 0);
		assertEquals(expectedX, n.getLongitude(), 0);
		assertTrue(n.isPredicted());
	}

	@Test
	/**
	 * Test the method GraphBuilder.findIntersections(HashSet<Edge> edges) finds 5 intersections using the test data provided by TestGraphBuilderHelper.java, and inserts the new nodes into the correct place in the pistes.
	 */
	public void testFindIntersections() {
		// in this test we need to mock out some of the methods in
		// GraphBuilder.java, we will use easymock partial mocking to do this.
		classUnderTest = createMockBuilder(NewGraphBuilder.class)
				.addMockedMethod("buildNodeAtIntersection").createMock();

		// build the node that we expect to be returned by the mocked method
		// we dont care about its value, only that it gets inserted into the
		// correct places in the pistes
		Node n = new Node(2.5, 5, false, true);
		n.setPredicted(true);

		// set the edges of the classUnderTest
		classUnderTest.setEdges(helper.getEdges());
		classUnderTest.setPistes(helper.getPistes());
		// set the distance calculator
		DistanceCalculator calc = new BasicDistance();
		classUnderTest.setCalc(calc);

		classUnderTest.setLineInterSectionTolerance(0.001);

		// with the test data we are expecting that given a distance tolerance
		// of 1 we will have 5 intersections with the sample data.
		expect(
				classUnderTest.buildNodeAtIntersection(isA(Point.class),
						isA(Piste.class), isA(Piste.class))).andReturn(n);
		expect(
				classUnderTest.buildNodeAtIntersection(isA(Point.class),
						isA(Piste.class), isA(Piste.class))).andReturn(n);
		expect(
				classUnderTest.buildNodeAtIntersection(isA(Point.class),
						isA(Piste.class), isA(Piste.class))).andReturn(n);
		expect(
				classUnderTest.buildNodeAtIntersection(isA(Point.class),
						isA(Piste.class), isA(Piste.class))).andReturn(n);
		expect(
				classUnderTest.buildNodeAtIntersection(isA(Point.class),
						isA(Piste.class), isA(Piste.class))).andReturn(n);
		replay(classUnderTest);

		classUnderTest.findIntersections(helper.getEdges());

		// Test 1, verify we attempt to create 5 nodes at intersections
		verify(classUnderTest);

		// check size of pistes have increased to correct values
		int piste1ExpectedSize = 13;
		int piste2ExpectedSize = 15;
		int piste3ExpectedSize = 15;

		// check that the correct nodes are in the correct pistes
		// Node n should be at the indexes 3,7,9 in piste1
		// Node n should be at the indexes 3,6,12 in piste2
		// Node n should be at the indexes 2,4,8,12 in piste3

		for (Piste p : classUnderTest.getPistes()) {
			if (p.getName().equals("Piste1")) {
				LinkedList<Node> nodes = p.getNodes();
				assertEquals(piste1ExpectedSize, nodes.size());
				assertEquals(n, nodes.get(3));
				assertEquals(n, nodes.get(7));
				assertEquals(n, nodes.get(9));

				assertNotEquals(n, nodes.get(0));
				assertNotEquals(n, nodes.get(1));
				assertNotEquals(n, nodes.get(2));
				assertNotEquals(n, nodes.get(4));
				assertNotEquals(n, nodes.get(5));
				assertNotEquals(n, nodes.get(6));
				assertNotEquals(n, nodes.get(8));
				assertNotEquals(n, nodes.get(10));
				assertNotEquals(n, nodes.get(11));
				assertNotEquals(n, nodes.get(12));

			} else if (p.getName().equals("Piste2")) {
				LinkedList<Node> nodes = p.getNodes();
				assertEquals(piste2ExpectedSize, nodes.size());
				assertEquals(n, nodes.get(3));
				assertEquals(n, nodes.get(6));
				assertEquals(n, nodes.get(12));

				assertNotEquals(n, nodes.get(0));
				assertNotEquals(n, nodes.get(1));
				assertNotEquals(n, nodes.get(2));
				assertNotEquals(n, nodes.get(4));
				assertNotEquals(n, nodes.get(5));
				assertNotEquals(n, nodes.get(7));
				assertNotEquals(n, nodes.get(8));
				assertNotEquals(n, nodes.get(9));
				assertNotEquals(n, nodes.get(10));
				assertNotEquals(n, nodes.get(11));
				assertNotEquals(n, nodes.get(13));
				assertNotEquals(n, nodes.get(14));

			} else if (p.getName().equals("Piste3")) {
				LinkedList<Node> nodes = p.getNodes();
				assertEquals(piste3ExpectedSize, nodes.size());
				assertEquals(n, nodes.get(2));
				assertEquals(n, nodes.get(4));
				assertEquals(n, nodes.get(8));
				assertEquals(n, nodes.get(12));

				assertNotEquals(n, nodes.get(0));
				assertNotEquals(n, nodes.get(1));
				assertNotEquals(n, nodes.get(3));
				assertNotEquals(n, nodes.get(5));
				assertNotEquals(n, nodes.get(6));
				assertNotEquals(n, nodes.get(7));
				assertNotEquals(n, nodes.get(9));
				assertNotEquals(n, nodes.get(10));
				assertNotEquals(n, nodes.get(11));
				assertNotEquals(n, nodes.get(13));
				assertNotEquals(n, nodes.get(14));

			} else {
				fail();
			}
		}

	}

	/**
	 * Test the method GraphBuilder.findCluster(HashSet<Node> nodes) finds the
	 * correct clusters in the given nodes
	 */
	@Test
	public void testFindCluster() {
		classUnderTest = createMockBuilder(NewGraphBuilder.class)
				.addMockedMethod("buildNodeFromCluster").createMock();

		Node n1 = new Node(1, 1, false, true);
		Node n2 = new Node(2, 3, false, true);
		// our test data has 33 nodes

		expect(classUnderTest.buildNodeFromCluster(isA(HashSet.class)))
				.andReturn(n1);
		expect(classUnderTest.buildNodeFromCluster(isA(HashSet.class)))
				.andReturn(n2);
		replay(classUnderTest);

		// our test data of start and end nodes should have two clusters in
		HashSet<Node> nodesToCluster = helper.buildStartEndNodes();
		// the method should also remove the clustered nodes from the graphs set
		// of nodes and add the new one
		classUnderTest.setNodes(nodesToCluster);
		classUnderTest.findClusters(nodesToCluster);

		verify(classUnderTest);
		// the graphbuilder class should now only have 4 nodes
		assertEquals(4, classUnderTest.getNodes().size());
		// n1 and n2 should be part in the new set of nodes
		assertTrue(classUnderTest.getNodes().contains(n1));
		assertTrue(classUnderTest.getNodes().contains(n2));

	}

	/**
	 * Tests the method GraphBuilder.findCluster(HashSet<Node> nodes) builds the
	 * expected node from the hashset of nodes passed to it.
	 */
	@Test
	public void testBuildNodeFromCluster() {

		// Build the nodes

		Node n1 = new Node(1, 2, true, true);
		Node n2 = new Node(1, 2, false, true);
		Node n3 = new Node(2, 1, false, true);
		Node n4 = new Node(2, 1, true, false);

		HashSet<Node> cluster = new HashSet<Node>();
		cluster.add(n1);
		cluster.add(n2);
		cluster.add(n3);
		cluster.add(n4);

		// Build the pistes
		Piste p1 = new Piste("piste1");
		Piste p2 = new Piste("piste2");
		Piste p3 = new Piste("piste3");
		Piste p4 = new Piste("piste4");

		HashSet<Piste> pistes = new HashSet<Piste>();
		pistes.add(p1);
		pistes.add(p2);
		pistes.add(p3);
		pistes.add(p4);
		
		n1.getPistes().add(p1);
		n2.getPistes().add(p2);
		n3.getPistes().add(p3);
		n4.getPistes().add(p4);

		// add nodes to the piste
		p1.getNodes().add(n1);
		p2.getNodes().add(n2);
		p3.getNodes().add(n3);
		p4.getNodes().add(n4);

		classUnderTest = new NewGraphBuilder();
		classUnderTest.setPistes(pistes);

		// Get the node
		Node n = classUnderTest.buildNodeFromCluster(cluster);
		// expected values;
		double expectedLat = 1.5;
		double expectedLong = 1.5;
		boolean expectedEnd =true;
		boolean expectedStart=true;
		
		// assertions
		assertEquals(expectedLat, n.getLatitude(),0);
		assertEquals(expectedLong, n.getLongitude(),0);
		assertEquals(expectedEnd, n.isEnd());
		assertEquals(expectedStart, n.isStart());
		
		// this new node should have replaced all the old nodes in the pistes
		for (Piste p : classUnderTest.getPistes())
		{
			// each piste should have only 1 node
			assertEquals(1, p.getNodes().size());
			assertEquals(n, p.getNodes().getFirst());
		}

	}

	/**
	 * @return the classUnderTest
	 */
	public NewGraphBuilder getClassUnderTest() {
		return classUnderTest;
	}

	/**
	 * @param classUnderTest
	 *            the classUnderTest to set
	 */
	public void setClassUnderTest(NewGraphBuilder classUnderTest) {
		this.classUnderTest = classUnderTest;
	}
}
