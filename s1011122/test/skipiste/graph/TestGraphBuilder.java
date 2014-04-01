package skipiste.graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import skipiste.graph.elements.Node;
import skipiste.utils.OutputKML;

/**
 * Test cases for the class skipiste.graph.GraphBuilder
 * 
 * @author s1011122
 * 
 */
public class TestGraphBuilder {

	private GraphBuilderTestHelper helper;
	private GraphBuilder graphBuilder;

	@Before
	public void setUp() {
		helper = new GraphBuilderTestHelper();
		graphBuilder = new GraphBuilder();
	}

	@Test
	public void test() {
		assertNotNull(helper.getNodes());
		assertNotNull(helper.getPistes());
	}

	/**
	 * Test the method GraphBuilder.createEdges(), this should create in total
	 * 19 new edges, one outbound edge for every start not, one inbound edge for
	 * every end node and one inbound and one outbound edge for every other
	 * node.
	 */
	@Test
	public void testCreateEdges() 
	{
		graphBuilder.setNodes(helper.getNodes());
		graphBuilder.setPistes(helper.getPistes());
		graphBuilder.createEdges(graphBuilder.getPistes());
		
		// We expect 19 edges in total;
		assertEquals(19, graphBuilder.getEdges().size());
	}
	
	@Test
	public void testPredictNewNodes()
	{
		graphBuilder.setNodes(helper.getNodes());
		graphBuilder.setPistes(helper.getPistes());
		graphBuilder.createEdges(graphBuilder.getPistes());

		// Test data should predict one new node
		graphBuilder.predictNewNodes();
		assertEquals(22, graphBuilder.getNodes().size());
		
		// this one new node should also create 2 new edges
		// we now should have 21 edges in total.
		assertEquals(21, graphBuilder.getEdges().size());		
	}
	
	@Test
	public void testCreateNode()
	{
		fail();
	}
	
	@Test
	public void testPointWithinRange()
	{
		fail();
	}
	
	@Test
	public void testMergeNodes()
	{
		fail();
	}
	
	public void testMergeSimilarNodes()
	{
		fail();
	}

	@Test
	public void test1() {
		String file = this.getClass()
				.getResource("PlanMontalbertPistesPlanDePisteNl.kml").getFile();
		GraphBuilder builder = new GraphBuilder(file);

		for (Node n : builder.getGraph().getNodes()) {
			System.out.println(OutputKML.outputPlaceMark(n.getLongitude(),
					n.getLattitude()));
		}
	}

}
